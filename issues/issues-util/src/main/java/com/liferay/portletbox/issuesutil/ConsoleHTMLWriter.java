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


/**
 * @author  Neil Griffin
 */
public class ConsoleHTMLWriter extends Writer {

	private static final String CONSOLE_DASHES =
		"--------------------------------------------------------------------------------";
	private static final String CONSOLE_DOUBLE_SPACE = "  ";

	@Override
	public void close() throws IOException {
	}

	@Override
	public void flush() throws IOException {
	}

	@Override
	public void write(char[] str, int off, int len) throws IOException {
		String message = new String(str, off, len);

		if (message.equals(HTMLUtil.BR_TAG)) {
			System.err.println();
		}
		else if (message.equals(HTMLUtil.DOUBLE_SPACE)) {
			System.err.print(CONSOLE_DOUBLE_SPACE);
		}
		else if (message.equals(HTMLUtil.HR_TAG)) {
			System.err.println(CONSOLE_DASHES);
		}
		else {
			System.err.print(message);
		}
	}

}
