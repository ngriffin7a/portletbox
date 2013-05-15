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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portletbox.issuesutil.HTMLUtil;


/**
 * @author  Neil Griffin
 */
public class TestPortlet extends GenericPortlet {

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {

		actionResponse.setRenderParameter("publicRenderParameter1", "1");
		actionResponse.setRenderParameter("privateRenderParameter1", "1");
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		PrintWriter writer = renderResponse.getWriter();

		String renderURLAsString = renderResponse.createRenderURL().toString();
		writer.write(HTMLUtil.HR_TAG);
		writer.write("renderRequest.getParameter(\"publicRenderParameter1\")=[");

		String publicRenderParameter1 = renderRequest.getParameter("publicRenderParameter1");

		if (publicRenderParameter1 == null) {
			publicRenderParameter1 = "null";
		}

		writer.write(publicRenderParameter1);
		writer.write("]");
		writer.write(HTMLUtil.HR_TAG);
		writer.write(
			"The following URL is a result of calling renderResponse.createRenderURL() without setting a public render parameter:");
		writer.write(HTMLUtil.BR_TAG);
		writer.write("<a href=\"");
		writer.write(renderURLAsString);
		writer.write("\">");
		writer.write(renderURLAsString);
		writer.write("</a>");
		writer.write(HTMLUtil.HR_TAG);

		PortletURL renderURL = renderResponse.createRenderURL();
		renderURL.setParameter("publicRenderParameter1", "2");
		renderURLAsString = renderURL.toString();

		writer.write(HTMLUtil.HR_TAG);
		writer.write(
			"The following URL is a result of calling renderResponse.createRenderURL() and then setParameter(\"publicRenderParameter1\", \"2\")");
		writer.write(HTMLUtil.BR_TAG);
		writer.write("<a href=\"");
		writer.write(renderURLAsString);
		writer.write("\">");
		writer.write(renderURLAsString);
		writer.write("</a>");
		writer.write(HTMLUtil.HR_TAG);

		// Form submitted with ActionURL
		PortletURL actionURL = renderResponse.createActionURL();
		actionURL.setParameter("publicRenderParameter1", "1");
		writer.write("<form action=\"" + actionURL.toString() + "\" method=\"post\">");
		writer.write("<input type=\"submit\" value=\"Invoke ACTION_PHASE\" />");
		writer.write("</form>");
		writer.write(HTMLUtil.HR_TAG);

	}
}
