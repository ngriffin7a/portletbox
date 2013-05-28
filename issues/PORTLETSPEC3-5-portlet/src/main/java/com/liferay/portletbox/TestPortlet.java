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
import java.io.Writer;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import com.liferay.portletbox.issuesutil.ConsoleHTMLWriter;
import com.liferay.portletbox.issuesutil.HTMLUtil;


/**
 * @author  Neil Griffin
 */
public class TestPortlet extends GenericPortlet {

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {

		actionResponse.setRenderParameter("publicRenderParameter1", "actionResponse");
		actionResponse.setRenderParameter("privateRenderParameter1", "actionResponse");

		Writer writer = new ConsoleHTMLWriter();

		writer.write(HTMLUtil.HR_TAG);
		writer.write("Phase: " + PortletRequest.ACTION_PHASE);
		writer.write(HTMLUtil.BR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.ACTION_PHASE, "publicParameterMap",
			actionRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.ACTION_PHASE, "privateParameterMap",
			actionRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.ACTION_PHASE, "parameterMap", actionRequest.getParameterMap());
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		resourceResponse.setContentType("text/html");

		Writer writer = resourceResponse.getWriter();

		resourceRequest.getPrivateParameterMap();
		writer.write("<html><body>");
		writer.write("Phase: " + PortletRequest.RESOURCE_PHASE);
		writer.write(HTMLUtil.BR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.RESOURCE_PHASE, "publicParameterMap",
			resourceRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.RESOURCE_PHASE, "privateParameterMap",
			resourceRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.RESOURCE_PHASE, "parameterMap", resourceRequest.getParameterMap());
		writer.write("</body></html>");
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		PrintWriter writer = renderResponse.getWriter();

		if (renderRequest.getParameter("privateRenderParameter1") != null) {
			writer.write("Check the console log for output from the ACTION_PHASE");
			writer.write(HTMLUtil.HR_TAG);
		}

		writer.write("Phase: " + PortletRequest.RENDER_PHASE);
		writer.write(HTMLUtil.BR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.RENDER_PHASE, "publicParameterMap",
			renderRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.RENDER_PHASE, "privateParameterMap",
			renderRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.RENDER_PHASE, "parameterMap", renderRequest.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		// RenderURL
		PortletURL renderURL = renderResponse.createRenderURL();
		renderURL.setParameter("publicRenderParameter1", "renderURL");
		renderURL.setParameter("privateRenderParameter1", "renderURL");
		writer.write("<a href=\"" + renderURL.toString() + "\">" + "ReRender to set State</a>");

		// Form submitted with ActionURL
		PortletURL actionURL = renderResponse.createActionURL();
		actionURL.setParameter("publicRenderParameter1", "actionURL");
		actionURL.setParameter("actionURLParameter1", "1");
		writer.write("<form action=\"" + actionURL.toString() + "\" method=\"post\">");
		writer.write("<label>formParameter1</label>");
		writer.write("<input");

		String namespace = renderResponse.getNamespace();
		writer.write(" name=\"" + namespace + "formParameter1\"");
		writer.write(" value=\"1\"");
		writer.write("/>");
		writer.write("<input type=\"submit\" value=\"Invoke ACTION_PHASE\" />");
		writer.write("</form>");
		writer.write(HTMLUtil.HR_TAG);

		// Form submitted with ResourceURL
		ResourceURL resourceURL = renderResponse.createResourceURL();
		resourceURL.setParameter("publicRenderParameter1", "1");
		resourceURL.setParameter("resourceURLParameter1", "1");
		writer.write("<form action=\"" + resourceURL.toString() + "\" method=\"post\">");
		writer.write("<label>formParameter1</label>");
		writer.write("<input");
		writer.write(" name=\"" + namespace + "formParameter1\"");
		writer.write(" value=\"1\"");
		writer.write("/>");
		writer.write("<input type=\"submit\" value=\"Invoke RESOURCE_PHASE\" />");
		writer.write("</form>");
	}
}
