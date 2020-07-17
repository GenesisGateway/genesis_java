package com.emerchantpay.gateway.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

public abstract class NodeWrapper implements Serializable {

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String UTC_DESCRIPTOR = "UTC";

	public abstract List<NodeWrapper> findAll(String expression);

	public List<String> findAllStrings(String expression) {
		List<String> strings = new ArrayList<String>();

		for (NodeWrapper node : findAll(expression)) {
			strings.add(node.findString("."));
		}

		return strings;
	}

	public BigDecimal findBigDecimal(String expression) {
		String value = findString(expression);
		return value == null ? null : new BigDecimal(value);
	}

	public boolean findBoolean(String expression) {
		String value = findString(expression);
		return Boolean.valueOf(value);
	}

	public Calendar findDate(String expression) {
		try {
			String dateString = findString(expression);
			if (dateString == null) {
				return null;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			dateFormat.setTimeZone(TimeZone.getTimeZone(UTC_DESCRIPTOR));
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC_DESCRIPTOR));
			calendar.setTime(dateFormat.parse(dateString));
			return calendar;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Calendar findDateTime(String expression) {
		try {
			String dateString = findString(expression);
			if (dateString == null) {
				return null;
			}
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
			dateTimeFormat.setTimeZone(TimeZone.getTimeZone(UTC_DESCRIPTOR));
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(UTC_DESCRIPTOR));
			calendar.setTime(dateTimeFormat.parse(dateString));
			return calendar;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Integer findInteger(String expression) {
		String value = findString(expression);
		return value == null ? null : Integer.valueOf(value);
	}

	public abstract NodeWrapper findFirst(String expression);

	public abstract String findString(String expression);

	public abstract String getElementName(); // TODO MDM Rename to getName

	public boolean isSuccess() {
		return !(getElementName().equals("error"));
	}

	public Map<String, String> findMap(String expression) {
		Map<String, String> map = new HashMap<String, String>();

		for (NodeWrapper mapNode : findAll(expression)) {
			map.put(StringUtils.underscore(mapNode.getElementName()), mapNode.findString("."));
		}

		return map;
	}

	public abstract Map<String, String> getFormParameters();

	public abstract boolean isBlank();

    public abstract List<NodeWrapper> getChildNodes(String nodeName);
}
