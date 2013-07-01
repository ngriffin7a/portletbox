/**
 * Software License Agreement (BSD License)
 * 
 * Copyright (c) 2013, Liferay Inc.
 * All rights reserved.
 * 
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * * Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 * 
 * * The name of Liferay Inc. may not be used to endorse or promote products
 *   derived from this software without specific prior
 *   written permission of Liferay Inc.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
