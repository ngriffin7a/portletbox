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
import java.util.HashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portletbox.issuesutil.ConsoleHTMLWriter;
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

		actionResponse.setRenderParameters(new HashMap<String, String[]>());

//      actionResponse.removePublicRenderParameter("publicRenderParameter1");

		Writer writer = new ConsoleHTMLWriter();
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.ACTION_PHASE, "publicParameterMap",
			actionRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.ACTION_PHASE, "privateParameterMap",
			actionRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMap(writer, PortletRequest.ACTION_PHASE, "parameterMap", actionRequest.getParameterMap());
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		PrintWriter writer = renderResponse.getWriter();

		if (renderRequest.getParameter("privateRenderParameter1") != null) {
			writer.write("Check the console log for output from the ACTION_PHASE");
			writer.write(HTMLUtil.HR_TAG);
		}

		HTMLUtil.writeMap(writer, PortletRequest.RENDER_PHASE, "publicParameterMap",
			renderRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.RENDER_PHASE, "privateParameterMap",
			renderRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		HTMLUtil.writeMap(writer, PortletRequest.RENDER_PHASE, "parameterMap", renderRequest.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		// Form submitted with ActionURL
		PortletURL actionURL = renderResponse.createActionURL();
		actionURL.setParameter("publicRenderParameter1", "1");
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

	}
}
