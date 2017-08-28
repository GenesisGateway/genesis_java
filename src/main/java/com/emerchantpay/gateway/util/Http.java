package com.emerchantpay.gateway.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyStore;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import com.emerchantpay.gateway.api.*;
import com.emerchantpay.gateway.api.exceptions.AuthenticationException;
import com.emerchantpay.gateway.api.exceptions.DownForMaintenanceException;
import com.emerchantpay.gateway.api.exceptions.NotFoundException;
import com.emerchantpay.gateway.api.exceptions.ServerException;
import com.emerchantpay.gateway.api.exceptions.UnexpectedException;
import com.emerchantpay.gateway.api.exceptions.UpgradeRequiredException;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit
 * persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT
 * OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * @license http://opensource.org/licenses/MIT The MIT License
 */

public class Http implements Serializable {

	enum RequestMethod {
		DELETE, GET, POST, PUT;
	}

	private Configuration configuration;

	public Http(Configuration configuration) {
		this.configuration = configuration;
	}

	public void delete(String url) {
		httpRequest(RequestMethod.DELETE, url);
	}

	public NodeWrapper get(String url) {
		return httpRequest(RequestMethod.GET, url);
	}

	public NodeWrapper post(String url) {
		return httpRequest(RequestMethod.POST, url, null);
	}

	public NodeWrapper post(String url, Request request) {
		return httpRequest(RequestMethod.POST, url, request.toXML());
	}

	public NodeWrapper postQuery(String url, Request request) {

		return httpRequest(RequestMethod.POST, url, request.toQueryString(""));
	}

	public NodeWrapper put(String url) {
		return httpRequest(RequestMethod.PUT, url, null);
	}

	public NodeWrapper put(String url, Request request) {
		return httpRequest(RequestMethod.PUT, url, request.toXML());
	}

	private NodeWrapper httpRequest(RequestMethod requestMethod, String url) {
		return httpRequest(requestMethod, url, null);
	}

	private NodeWrapper httpRequest(RequestMethod requestMethod, String url, String postBody) {
		HttpURLConnection connection = null;
		NodeWrapper nodeWrapper = null;

		try {
			connection = buildConnection(requestMethod, url);

			Logger logger = configuration.getLogger();
			if (postBody != null) {
				logger.log(Level.FINE, formatSanitizeBodyForLog(postBody));
			}

			if (connection instanceof HttpsURLConnection) {
				((HttpsURLConnection) connection).setSSLSocketFactory(getSSLSocketFactory());
			}

			if (postBody != null) {
				OutputStream outputStream = null;
				try {
					outputStream = connection.getOutputStream();
					outputStream.write(postBody.getBytes("UTF-8"));
				} finally {
					if (outputStream != null) {
						outputStream.close();
					}
				}
			}

			throwExceptionIfErrorStatusCode(connection.getResponseCode(), null);
			if (requestMethod.equals(RequestMethod.DELETE)) {
				return null;
			}

			InputStream responseStream = null;
			try {
				responseStream = connection.getResponseCode() == 422 ? connection.getErrorStream()
						: connection.getInputStream();

				// if ("gzip".equalsIgnoreCase(connection.getContentEncoding()))
				// {
				// responseStream = new GZIPInputStream(responseStream);
				// }

				String xml = StringUtils.inputStreamToString(responseStream);

				logger.log(Level.INFO, "[Genesis] [{0}]] {1} {2}",
						new Object[] { getCurrentTime(), requestMethod.toString(), url });
				logger.log(Level.FINE, "[Genesis] [{0}] {1} {2} {3}",
						new Object[] { getCurrentTime(), requestMethod.toString(), url, connection.getResponseCode() });

				if (xml != null) {
					logger.log(Level.FINE, formatSanitizeBodyForLog(xml));
				}

				nodeWrapper = NodeWrapperFactory.instance.create(xml);
			} finally {
				if (responseStream != null) {
					responseStream.close();
				}
			}
		} catch (IOException e) {
			try {
				throw new UnexpectedException(e.getMessage(), e);
			} catch (UnexpectedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

		return nodeWrapper;
	}

	private String formatSanitizeBodyForLog(String body) {
		if (body == null) {
			return body;
		}

		Pattern regex = Pattern.compile("(^)", Pattern.MULTILINE);
		Matcher regexMatcher = regex.matcher(body);
		if (regexMatcher.find()) {
			body = regexMatcher.replaceAll("[Genesis] $1");
		}

		regex = Pattern.compile("<number>(.{6}).+?(.{4})</number>");
		regexMatcher = regex.matcher(body);
		if (regexMatcher.find()) {
			body = regexMatcher.replaceAll("<number>$1******$2</number>");
		}

		body = body.replaceAll("<cvv>.+?</cvv>", "<cvv>***</cvv>");

		return body;
	}

	private String getCurrentTime() {
		return new SimpleDateFormat("d/MMM/yyyy HH:mm:ss Z").format(new Date());
	}

	private SSLSocketFactory getSSLSocketFactory() {
		try {
			KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
			keyStore.load(null);

			String[] certificateFilenames = new String[] { "ca-bundle.pem" };

			for (String certificateFilename : certificateFilenames) {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				InputStream certStream = null;
				try {
					certStream = Http.class.getClassLoader().getResourceAsStream(certificateFilename);

					Collection<? extends Certificate> coll = cf.generateCertificates(certStream);
					for (Certificate cert : coll) {
						if (cert instanceof X509Certificate) {
							X509Certificate x509cert = (X509Certificate) cert;
							Principal principal = x509cert.getSubjectDN();
							String subject = principal.getName();
							keyStore.setCertificateEntry(subject, cert);
						}
					}
				} finally {
					if (certStream != null) {
						certStream.close();
					}
				}
			}

			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(keyStore, null);
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			tmf.init(keyStore);

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init((KeyManager[]) kmf.getKeyManagers(), tmf.getTrustManagers(),
					SecureRandom.getInstance("SHA1PRNG"));

			return sslContext.getSocketFactory();
		} catch (Exception e) {
			Logger logger = configuration.getLogger();
			logger.log(Level.SEVERE, "SSL Verification failed. Error message: {0}", new Object[] { e.getMessage() });
			try {
				throw new UnexpectedException(e.getMessage(), e);
			} catch (UnexpectedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return null;
	}

	private HttpURLConnection buildConnection(RequestMethod requestMethod, String urlString)
			throws java.io.IOException {
		URL url = new URL(urlString);
		HttpURLConnection connection;

		String user_pass = configuration.getUsername() + ":" + configuration.getPassword();
		String encoded = Base64.getEncoder().encodeToString(user_pass.getBytes());

		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(requestMethod.toString());
		connection.addRequestProperty("Accept", "application/xml");
		connection.setRequestProperty("Content-Type", "application/xml");
		connection.addRequestProperty("Authorization", "Basic " + encoded);
		connection.setDoOutput(true);
		connection.setReadTimeout(60000);

		return connection;
	}

	public static void throwExceptionIfErrorStatusCode(int statusCode, String message) {
		@SuppressWarnings("unused")
		String decodedMessage = null;
		if (message != null) {
			try {
				decodedMessage = URLDecoder.decode(message, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (isErrorCode(statusCode)) {
			switch (statusCode) {
			case 401:
				throw new AuthenticationException();
			case 404:
				throw new NotFoundException();
			case 426:
				throw new UpgradeRequiredException();
			case 500:
				throw new ServerException();
			case 503:
				throw new DownForMaintenanceException();

			default:
				try {
					throw new UnexpectedException("Unexpected HTTP_RESPONSE " + statusCode);
				} catch (UnexpectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean isErrorCode(int responseCode) {
		return responseCode != 200 && responseCode != 201 && responseCode != 422;
	}
}
