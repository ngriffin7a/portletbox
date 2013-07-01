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
package com.liferay.portletbox;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portletbox.issuesutil.HTMLUtil;


/**
 * @author  Neil Griffin
 */
public class TestPortlet3_14 extends GenericPortlet {

	private String title;
	private String titleError;

	@Override
	protected void doHeaders(RenderRequest renderRequest, RenderResponse renderResponse) {

		if (title == null) {

			try {
				title = super.getTitle(renderRequest);
			}
			catch (Exception e) {
				titleError = e.getMessage();
				title = "title-from-doHeaders";
			}
		}
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		PrintWriter writer = renderResponse.getWriter();

		if (titleError != null) {
			writer.write("The portlet title was set in doHeaders, because getTitle() returned the following error:");
			writer.write(HTMLUtil.BR_TAG);
			writer.write(titleError);
		}
		else {
			writer.write("The portlet title was discovered without any errors.");
		}

		writer.write(HTMLUtil.HR_TAG);
		writer.write("GenericPortlet.getPortletName()=" + getPortletName());
		writer.write(HTMLUtil.BR_TAG);
		writer.write("GenericPortlet.getPortletTitle()=" + getTitle(renderRequest));
	}

	@Override
	protected String getTitle(RenderRequest renderRequest) {
		return title;
	}
}
