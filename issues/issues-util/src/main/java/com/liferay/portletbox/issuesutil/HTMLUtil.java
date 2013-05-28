/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.portletbox.issuesutil;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.portlet.PortletRequest;


/**
 * @author  Neil Griffin
 */
public class HTMLUtil {

	public static final String BR_TAG = "<br />";
	public static final String HR_TAG = "<hr />";
	public static final String DOUBLE_SPACE = "&nbsp;&nbsp;";

	public static void writeMap(Writer writer, String portletPhase, String mapName, Map<String, String[]> parameterMap)
		throws IOException {
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();

		writer.write(DOUBLE_SPACE);
		writer.write(mapName);
		writer.write(BR_TAG);

		for (Map.Entry<String, String[]> mapEntry : entrySet) {
			String key = mapEntry.getKey();
			String[] values = mapEntry.getValue();

			writer.write(DOUBLE_SPACE);
			writer.write(DOUBLE_SPACE);
			writer.write(key);
			writer.write(": ");

			for (int i = 0; i < values.length; i++) {

				if (i > 0)
					writer.write(", ");

				writer.write(values[i]);
			}

			writer.write(BR_TAG);
		}
	}

	public static void writeMapCompact(Writer writer, String portletPhase, String mapName,
		Map<String, String[]> parameterMap) throws IOException {
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();

		writer.write("mapName=" + mapName + ":");
		writer.write(BR_TAG);

		for (Map.Entry<String, String[]> mapEntry : entrySet) {
			String key = mapEntry.getKey();
			String[] values = mapEntry.getValue();

			for (int i = 0; i < values.length; i++) {
				writer.write("key=[" + key + "] values[" + i + "]=[" + values[i] + "]");
				writer.write(BR_TAG);
			}
		}
	}

	/**
	 * Writes parameters from the getParameterNames() and getParameterValues(String) methods -
	 */
	public static void writeParameters(PrintWriter writer, String portletPhase, PortletRequest req) throws IOException {

		Enumeration<String> parmNames = req.getParameterNames();

		writer.write("getParameterNames() and getParameterValues():");
		writer.write(BR_TAG);

		while (parmNames.hasMoreElements()) {
			String name = parmNames.nextElement();
			String[] values = req.getParameterValues(name);

			for (int i = 0; i < values.length; i++) {
				writer.write("Name=[" + name + "] values[" + i + "]=[" + values[i] + "]");
				writer.write(HTMLUtil.BR_TAG);
			}
		}
	}

	public static void writeURL(PrintWriter writer, String comment, String urlAsString) {
		writer.write(comment);
		writer.write(HTMLUtil.BR_TAG);
		writer.write("<a href=\"");
		writer.write(urlAsString);
		writer.write("\">");
		writer.write(urlAsString);
		writer.write("</a>");
		writer.write(HTMLUtil.HR_TAG);
	}

}
