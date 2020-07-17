package com.emerchantpay.gateway.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.emerchantpay.gateway.api.*;
import com.emerchantpay.gateway.api.constants.ContentTypes;
import com.emerchantpay.gateway.api.exceptions.AuthenticationException;
import com.emerchantpay.gateway.api.exceptions.DownForMaintenanceException;
import com.emerchantpay.gateway.api.exceptions.NotFoundException;
import com.emerchantpay.gateway.api.exceptions.ServerException;
import com.emerchantpay.gateway.api.exceptions.UnexpectedException;
import com.emerchantpay.gateway.api.exceptions.UpgradeRequiredException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.xalan.xsltc.trax.TransformerFactoryImpl;

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

    private String host;
    private Integer port;

    public Http(Configuration configuration) {
        this.configuration = configuration;
    }

    public void delete(String url) {
        httpRequestXML(RequestMethod.DELETE, url);
    }

    public NodeWrapper getXML(String url) {
        return httpRequestXML(RequestMethod.GET, url);
    }

    public JsonNode getJson(String url) {
        return httpRequestJson(RequestMethod.GET, url);
    }

    public NodeWrapper postXML(String url) {
        return httpRequestXML(RequestMethod.POST, url, null);
    }

    public NodeWrapper postXML(String url, Request request) {
        return httpRequestXML(RequestMethod.POST, url, request.toXML());
    }

    public JsonNode postJson(String url) {
        return httpRequestJson(RequestMethod.POST, url, null);
    }

    public JsonNode postJson(String url, Request request) {
        return httpRequestJson(RequestMethod.POST, url, request.toJSON());
    }

    public JsonNode postJson(String url, String requestBody) {
        return httpRequestJson(RequestMethod.POST, url, requestBody);
    }

    public NodeWrapper postQuery(String url, Request request) {

        return httpRequestXML(RequestMethod.POST, url, request.toQueryString(""));
    }

    public NodeWrapper put(String url) {
        return httpRequestXML(RequestMethod.PUT, url, null);
    }

    public NodeWrapper put(String url, Request request) {
        return httpRequestXML(RequestMethod.PUT, url, request.toXML());
    }

    private NodeWrapper httpRequestXML(RequestMethod requestMethod, String url) {
        return httpRequestXML(requestMethod, url, null);
    }

    private JsonNode httpRequestJson(RequestMethod requestMethod, String url) {
        return httpRequestJson(requestMethod, url, null);
    }

    private NodeWrapper httpRequestXML(RequestMethod requestMethod, String url, String postBody) {
        HttpURLConnection connection = null;
        NodeWrapper nodeWrapper = null;

        try {
            connection = buildConnection(requestMethod, url, ContentTypes.XML);

            host = connection.getURL().getHost();
            port = connection.getURL().getDefaultPort();

            if (postBody != null) {
                postBody = formatPostBody(postBody);
                configuration.getLogger().log(Level.FINE, formatSanitizeBodyForLog(postBody));
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

                String xml = StringUtils.inputStreamToString(responseStream);

                configuration.getLogger().log(Level.INFO, "[Genesis] [{0}]] {1} {2}",
                        new Object[]{getCurrentTime(), requestMethod.toString(), url});
                configuration.getLogger().log(Level.FINE, "[Genesis] [{0}] {1} {2} {3}",
                        new Object[]{getCurrentTime(), requestMethod.toString(), url, connection.getResponseCode()});

                if (xml != null && configuration.isDebugModeEnabled() == true) {
                    configuration.getLogger().log(Level.INFO, formatSanitizeBodyForLog(postBody));
                    configuration.getLogger().log(Level.INFO, formatSanitizeBodyForLog(xml));
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

    private JsonNode httpRequestJson(RequestMethod requestMethod, String url, String postBody) {
        HttpURLConnection connection = null;
        JsonNode jsonNode = null;

        try {
            connection = buildConnection(requestMethod, url, ContentTypes.JSON);

            host = connection.getURL().getHost();
            port = connection.getURL().getDefaultPort();

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

                String json = StringUtils.inputStreamToString(responseStream);

                configuration.getLogger().log(Level.INFO, "[Genesis] [{0}]] {1} {2}",
                        new Object[]{getCurrentTime(), requestMethod.toString(), url});
                configuration.getLogger().log(Level.FINE, "[Genesis] [{0}] {1} {2} {3}",
                        new Object[]{getCurrentTime(), requestMethod.toString(), url, connection.getResponseCode()});

                if (json != null && configuration.isDebugModeEnabled() == true) {
                    configuration.getLogger().log(Level.INFO, formatSanitizeBodyForLog(postBody));
                    configuration.getLogger().log(Level.INFO, formatSanitizeBodyForLog(json));
                }

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.writer().writeValueAsString(json);

                jsonNode = objectMapper.readTree(json);
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

        return jsonNode;
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

    private String formatPostBody(String input, int indent) {
        try {
            Source xmlInput = new StreamSource(new StringReader(input));
            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory transformerFactory = new TransformerFactoryImpl();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
            transformer.transform(xmlInput, xmlOutput);
            return xmlOutput.getWriter().toString();
        } catch (Exception e) {
            throw new RuntimeException(e); // simple exception handling, please review it
        }
    }

    private String formatPostBody(String input) {
        return formatPostBody(input, 2);
    }

    private String getCurrentTime() {
        return new SimpleDateFormat("d/MMM/yyyy HH:mm:ss Z").format(new Date());
    }

    private HttpURLConnection buildConnection(RequestMethod requestMethod, String urlString, String contentType)
            throws java.io.IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection;

        String user_pass = configuration.getUsername() + ":" + configuration.getPassword();
        String encoded = Base64.getEncoder().encodeToString(user_pass.getBytes());

        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod.toString());
        connection.addRequestProperty("Accept", contentType);
        connection.setRequestProperty("Content-Type",contentType);
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