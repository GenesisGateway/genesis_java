package com.emerchantpay.gateway.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @license     http://opensource.org/licenses/MIT The MIT License
 */

public class StringUtils {

	String uid;

	public static <T> String classToXMLName(Class<T> klass) {
		return dasherize(klass.getSimpleName()).toLowerCase();
	}

	public static Map<String, String> replaceMap =
			Collections.unmodifiableMap(new HashMap<String, String>() {{
				put("&amp;", "&");
				put("&lt;", "<");
				put("&gt;", ">");
				put("&apos;", "'");
				put("&quot;", "\"");
				put("<>", "");
				put("</>", "");
			}});

	public static String dasherize(String str) {
		if (str == null)
			return null;

		return str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1-$2").replaceAll("([a-z])([A-Z])", "$1-$2")
				.replaceAll("_", "-").toLowerCase();
	}

	public static String getFullPathOfFile(String filename) {
		return getClassLoader().getResource(filename).getFile();
	}

	private static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public static String inputStreamToString(InputStream inputStream) throws IOException {
		InputStreamReader inputReader = new InputStreamReader(inputStream);
		StringBuilder builder = new StringBuilder();
		char[] buffer = new char[0x1000];
		int bytesRead = inputReader.read(buffer, 0, buffer.length);
		while (bytesRead >= 0) {
			builder.append(buffer, 0, bytesRead);
			bytesRead = inputReader.read(buffer, 0, buffer.length);
		}
		return builder.toString();
	}

	public static String nullIfEmpty(String str) {
		return str == null || str.length() == 0 ? null : str;
	}

	public static String underscore(String str) {
		if (str == null)
			return null;

		return str.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2")
				.replaceAll("-", "_").toLowerCase();
	}

	public static String join(Object[] tokens, String delimiter) {
		if (tokens.length == 0)
			return "";

		StringBuilder joined = new StringBuilder();

		boolean first = true;
		for (Object token : tokens) {
			if (!first)
				joined.append(delimiter);
			else
				first = false;
			joined.append(token);
		}

		return joined.toString();
	}

	public static String join(String delimiter, Object... tokens) {
		return join(tokens, delimiter);
	}

	public static String mapToString(Map<String, Object> map) {
		LinkedList<String> pairs = new LinkedList<String>();
		ArrayList<String> keyList = new ArrayList<String>(map.keySet());
		Collections.sort(keyList);
		for (String s : keyList) {
			Object value = map.get(s);
			String valueStr = toString(value);
			pairs.add(s + ": " + valueStr);
		}

		return "{" + join(", ", pairs.toArray()) + "}";
	}

	@SuppressWarnings("unchecked")
	public static String toString(Object value) {
		if (value instanceof Map)
			return mapToString((Map<String, Object>) value);
		else if (value instanceof List)
			return listToString((List<Object>) value);
		else if (value == null)
			return "null";
		else
			return value.toString().trim();
	}

	public static String listToString(List<Object> value) {
		Object[] valueStrings = new Object[value.size()];
		for (int i = 0; i < valueStrings.length; i++) {
			valueStrings[i] = toString(value.get(i));
		}
		return "[" + join(", ", valueStrings) + "]";
	}

	public static String unescapeUtf8(String encodedString) {
		int i = 0;
		int len = encodedString.length();
		char c;
		StringBuffer buffer = new StringBuffer(len);

		while (i < len) {
			c = encodedString.charAt(i++);
			if (c == '\\') {
				if (i < len) {
					c = encodedString.charAt(i++);
					if (c == 'u') {
						c = (char) Integer.parseInt(encodedString.substring(i, i + 4), 16);
						i += 4;
					}
				}
			}
			buffer.append(c);
		}
		return buffer.toString();
	}

	public String generateUID() {
		UUID uuid = UUID.randomUUID();
		uid = uuid.toString();
		uid = uid.replace("-", "").substring(0, 30);

		return uid;
	}

	public static String replaceAllSpecialCharacters(String input) {
		for(String key: replaceMap.keySet()) {
			input = input.replace(key, replaceMap.get(key));
		}
		return input;
	}
}
