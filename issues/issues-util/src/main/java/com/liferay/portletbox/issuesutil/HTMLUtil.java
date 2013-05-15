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
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * @author  Neil Griffin
 */
public class HTMLUtil {

	public static final String BR_TAG = "<br />";
	public static final String HR_TAG = "<hr />";

	public static void writeMap(Writer writer, String portletPhase, String mapName, Map<String, String[]> parameterMap)
		throws IOException {
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();

		for (Map.Entry<String, String[]> mapEntry : entrySet) {
			String key = mapEntry.getKey();
			String[] values = mapEntry.getValue();

			for (int i = 0; i < values.length; i++) {
				writer.write("portletPhase=[" + portletPhase + "] mapName=[" + mapName + "] key=[" + key + "] values[" +
					i + "]=[" + values[i] + "]");
				writer.write(BR_TAG);
			}
		}
	}

}
