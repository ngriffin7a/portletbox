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
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import com.liferay.portletbox.issuesutil.HTMLUtil;


/**
 * @author  Neil Griffin
 */
public class TestPortlet extends GenericPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		resourceResponse.setContentType("text/html");

		PrintWriter writer = resourceResponse.getWriter();
		writer.write("<html><body>");
		writer.write("<span>");
		writer.write("resourceURL=<br />");

		ResourceURL resourceURL = resourceResponse.createResourceURL();
		resourceURL.setParameter("publicRenderParameter1", "4");

		String resourceURLAsString = resourceURL.toString();
		writer.write("The following URL is a result of calling renderResponse.createResourceURL()");
		writer.write(HTMLUtil.BR_TAG);
		writer.write("<a href=\"");
		writer.write(resourceURLAsString);
		writer.write("\">");
		writer.write(resourceURLAsString);
		writer.write("</a>");
		writer.write("</span>");
		writer.write("</body></html>");
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

		String resourceURLAsString = renderResponse.createResourceURL().toString();
		writer.write(HTMLUtil.HR_TAG);
		writer.write("The following URL is a result of calling renderResponse.createResourceURL()");
		writer.write(HTMLUtil.BR_TAG);
		writer.write("<a href=\"");
		writer.write(resourceURLAsString);
		writer.write("\">");
		writer.write(resourceURLAsString);
		writer.write("</a>");
		writer.write(HTMLUtil.HR_TAG);

		ResourceURL resourceURL = renderResponse.createResourceURL();

		// resourceURL.setParameter("publicRenderParameter1", (String) null); // IllegalArgumentException
		resourceURL.setParameter("publicRenderParameter1", "3"); // IllegalArgumentException
		writer.write(HTMLUtil.HR_TAG);
		writer.write(
			"The following URL is a result of calling renderResponse.createResourceURL() and then setParameter(\"publicRenderParameter1\", \"3\")");
		writer.write(HTMLUtil.BR_TAG);
		writer.write("<a href=\"");
		writer.write(resourceURLAsString);
		writer.write("\">");
		writer.write(resourceURLAsString);
		writer.write("</a>");
		writer.write(HTMLUtil.HR_TAG);

	}
}
