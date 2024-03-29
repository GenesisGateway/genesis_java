package com.emerchantpay.gateway.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.emerchantpay.gateway.util.QueryString;
import com.emerchantpay.gateway.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


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

public class RequestBuilder {

    private String parent;
    private List<Map.Entry<String, String>> topLevelElements;
    private List<Map.Entry<String, Object>> elements;

    public RequestBuilder(String parent) {
        this.parent = parent;
        this.topLevelElements = new ArrayList<Map.Entry<String, String>>();
        this.elements = new ArrayList<Map.Entry<String, Object>>();
    }

    public RequestBuilder addTopLevelElement(String name, String value) {
        topLevelElements.add(new AbstractMap.SimpleEntry<String, String>(name, value));
        return this;
    }

    public RequestBuilder addElement(Object value) {
        if (value instanceof String) {
            elements.removeIf(elements -> elements.getKey().equals("") && elements.getValue().equals(value.toString()));
        }
        elements.add(new AbstractMap.SimpleEntry<String, Object>("", value));

        return this;
    }

    public RequestBuilder addElement(String name, Object value) {
        if (name != null && !"reminder".equals(name)) {
            elements.removeIf(elements -> elements.getKey().equals(name));
        }

        elements.add(new AbstractMap.SimpleEntry<String, Object>(name, value));

        return this;
    }

    public String toQueryString() {
        QueryString queryString = new QueryString();
        for (Map.Entry<String, String> entry : topLevelElements) {
            queryString.append(StringUtils.underscore(entry.getKey()), entry.getValue());
        }
        for (Map.Entry<String, Object> entry : elements) {
            queryString.append(
                    parentBracketChildString(StringUtils.underscore(parent), StringUtils.underscore(entry.getKey())),
                    entry.getValue());
        }
        return queryString.toString();
    }

    public String toXML() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, Object> entry : elements) {
            builder.append(buildXMLElement(entry.getKey(), entry.getValue()));
        }
        //Add parent tag only if its content is not empty
        if(!builder.toString().isEmpty()) {
            builder.insert(0, String.format("<%s>", parent));
            builder.append(String.format("</%s>", parent));
        }
        return StringUtils.replaceAllSpecialCharacters(builder.toString());
    }

    public String toJSON() {
        XmlMapper xmlMapper = new XmlMapper();
        JsonNode node = null;

        try {
            node = xmlMapper.readTree(StringUtils.replaceAllSpecialCharacters(toXML()).getBytes());
        } catch (IOException error) {
            error.printStackTrace();
        }

        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (JsonProcessingException error) {
            error.printStackTrace();
            return null;
        }
    }

    protected static String buildXMLElement(Object element) {
        return buildXMLElement("", element);
    }

    @SuppressWarnings("unchecked")
    protected static String buildXMLElement(String name, Object element) {
        if (element == null) {
            return "";
        } else if(element instanceof String && element.toString().isEmpty()) {
            return "";
        } else if (element instanceof Request) {
            return ((Request) element).toXML();
        } else if (element instanceof Calendar) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return String.format("<%s type=\"datetime\">%s</%s>", name,
                    dateFormat.format(((Calendar) element).getTime()), name);
        } else if (element instanceof Map<?, ?>) {
            return formatAsXML(name, (Map<String, Object>) element);
        } else if (element instanceof List<?>) {
            StringBuilder xml = new StringBuilder();
            for (Object item : (List<Object>) element) {
                xml.append(buildXMLElement("item", item));
            }
            return wrapInXMLTag(name, xml.toString(), "array");
        } else {

            if (name == "payment_token") {
                return String.format("<%s>%s</%s>", xmlEscape(name), element == null ? "" : jsonEscape(element.toString()),
                        xmlEscape(name));
            } else {
                return String.format("<%s>%s</%s>", xmlEscape(name), element == null ? "" : xmlEscape(element.toString()),
                        xmlEscape(name));
            }
        }
    }

    protected static String formatAsXML(String name, Map<String, Object> map) {
        if (map == null)
            return "";
        StringBuilder xml = new StringBuilder();
        xml.append(String.format("<%s>", name));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            xml.append(buildXMLElement(entry.getKey(), entry.getValue()));
        }
        xml.append(String.format("</%s>", name));
        return xml.toString();
    }

    protected static Object buildQueryStringElement(String name, String value) {
        if (value != null) {
            try {
                return String.format("%s=%s&", URLEncoder.encode(name, "UTF-8"), URLEncoder.encode(value, "UTF-8"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return "";
        }
    }

    protected static String parentBracketChildString(String parent, String child) {
        return String.format("%s[%s]", parent, child);
    }

    protected static String wrapInXMLTag(String tagName, String xml) {
        return String.format("<%s/>%s</%s>", tagName, xml, tagName);
    }

    protected static String wrapInXMLTag(String tagName, String xml, String type) {
        return String.format("<%s type=\"%s\">%s</%s>", tagName, type, xml, tagName);
    }

    protected static String xmlEscape(String input) {
        return StringUtils.replaceXMLEscapeCharacters(input);
    }

    public static String jsonEscape(String input) {
        return StringUtils.replaceJSONEscapeCharacters(input);
    }

    public List<Map.Entry<String, Object>> getElements() {
        return elements;
    }
}