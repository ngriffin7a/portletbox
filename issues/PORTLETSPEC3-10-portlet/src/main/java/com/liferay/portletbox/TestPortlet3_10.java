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
import java.util.Map;
import java.util.Set;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
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
public class TestPortlet3_10 extends GenericPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException, IOException {

		resourceResponse.setContentType("text/html");

		PrintWriter writer = resourceResponse.getWriter();
		writer.write("<html><body>");

		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.RESOURCE_PHASE, "publicParameterMap",
			resourceRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.RESOURCE_PHASE, "privateParameterMap",
			resourceRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.RESOURCE_PHASE, "parameterMap",
			resourceRequest.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeParameters(writer, PortletRequest.RESOURCE_PHASE, resourceRequest);
		writer.write(HTMLUtil.HR_TAG);

		writer.write("<span>");

		// Create resource URL with no further processing -

		ResourceURL resourceURL = resourceResponse.createResourceURL();

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling resourceResponse.createResourceURL() without setting a new parameter:",
			resourceURL.toString());

		// Create resource URL, setting parameter -

		resourceURL = resourceResponse.createResourceURL();
		resourceURL.setParameter("resourceURLParameter4", "44");

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling resourceResponse.createResourceURL() and setting a new parameter:",
			resourceURL.toString());

		writer.write("</span>");
		writer.write("</body></html>");
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		PrintWriter writer = renderResponse.getWriter();

		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "publicParameterMap",
			renderRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "privateParameterMap",
			renderRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "parameterMap", renderRequest.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeParameters(writer, PortletRequest.RENDER_PHASE, renderRequest);

		writer.write(HTMLUtil.HR_TAG);
		writer.write("renderRequest.getParameter(\"publicRenderParameter1\")=[");

		String publicRenderParameter1 = renderRequest.getParameter("publicRenderParameter1");

		if (publicRenderParameter1 == null) {
			publicRenderParameter1 = "null";
		}

		writer.write(publicRenderParameter1);
		writer.write("]");
		writer.write(HTMLUtil.HR_TAG);

		// Create render URL w/o parameters -

		PortletURL renderURL = renderResponse.createRenderURL();

		HTMLUtil.writeURL(writer, "Created render URL w/o parameters -", renderURL.toString());

		// Create Render URL, copying all parameters from request using map -

		renderURL = renderResponse.createRenderURL();

		Map<String, String[]> parmMap = renderRequest.getParameterMap();
		renderURL.setParameters(parmMap);

		HTMLUtil.writeURL(writer, "Create Render URL, copying all parameters from request using map -",
			renderURL.toString());

		// set public & private render parameters -

		renderURL = renderResponse.createRenderURL();

		renderURL.setParameter("publicRenderParameter1", "2");
		renderURL.setParameter("privateRenderParameter1", "1");

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createRenderURL() and then setParameter(\"publicRenderParameter1\", \"2\") and setParameter(\"privateRenderParameter1\", \"1\")",
			renderURL.toString());

		// set public & private render parameters, making use of map -

		renderURL = renderResponse.createRenderURL();

		renderURL.setParameter("publicRenderParameter1", "2");
		renderURL.setParameter("privateRenderParameter1", "1");

		String[] values = { "Fred", "Wilma", "Pebbles" };
		renderURL.getParameterMap().put("privateRenderParameter2", values);

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createRenderURL(), setting public and private parameters, and getParameterMap().put()",
			renderURL.toString());

		// set & delete parameters on render URL through setParameter() -

		renderURL = renderResponse.createRenderURL();

		renderURL.setParameter("privateRenderParameter1", "1");

		Set<String> keySet = parmMap.keySet();

		for (String key : keySet) {

			try {
				renderURL.setParameter(key, (String) null);
			}
			catch (Exception e) {
				writer.write("remove " + key + " from URL failed.<br/>" + e.getMessage() + "<br/>");
			}
		}

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createRenderURL() and removing all parameters through setParameter(name, null) for each parameter name:",
			renderURL.toString());

		// set & delete parameters on render URL through setParameters() -

		renderURL = renderResponse.createRenderURL();

		renderURL.setParameter("privateRenderParameter1", "1");

		parmMap = renderURL.getParameterMap();
		keySet = parmMap.keySet();

		String[] parmVals = { null };

		for (String key : keySet) {

			try {
				parmMap.put(key, parmVals);
			}
			catch (Exception e) {
				writer.write("updating map entry " + key + "failed.<br/>" + e.getMessage() + "<br/>");
			}
		}

		try {
			renderURL.setParameters(parmMap);
		}
		catch (Exception e) {
			writer.write("setParameters() failed.<br/>" + e.getMessage() + "<br/>");
		}

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createRenderURL() and removing all parameters through setParameters()",
			renderURL.toString());

		// render URL with public render parameter removed -

		renderURL = renderResponse.createRenderURL();

		try {
			renderURL.removePublicRenderParameter("publicRenderParameter1");
		}
		catch (Exception e) {
			writer.write("remove publicRenderParameter1 from URL failed.<br/>" + e.getMessage() + "<br/>");
		}

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createRenderURL() and removePublicRenderParameter()",
			renderURL.toString());

		// Resource URL with no further processing -

		ResourceURL resourceURL = renderResponse.createResourceURL();

		HTMLUtil.writeURL(writer, "The following URL is a result of calling renderResponse.createResourceURL()",
			resourceURL.toString());

		// Resource URL attempting to remove parameters -

		resourceURL = renderResponse.createResourceURL();

		try {
			resourceURL.setParameter("publicRenderParameter1", (String) null);
		}
		catch (Exception e) {
			writer.write("remove publicRenderParameter1 from URL failed.<br/>" + e.getMessage() + "<br/>");
		}

		try {
			resourceURL.setParameter("privateRenderParameter1", (String) null);
		}
		catch (Exception e) {
			writer.write("remove privateRenderParameter1 from URL failed.<br/>" + e.getMessage() + "<br/>");
		}

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createResourceURL() and attempting to remove publicRenderParameter1 & privateRenderParameter1:",
			resourceURL.toString());

		// Resource URL setting parameters using various means -

		resourceURL = renderResponse.createResourceURL();

		resourceURL.setParameter("publicRenderParameter1", "30");
		resourceURL.setParameter("privateRenderParameter1", "35");
		resourceURL.setParameter("resourceURLParameter2", "40");

		String[] values2 = { "Barney", "Betty" };
		resourceURL.getParameterMap().put("resourceURLParameter3", values2);

		HTMLUtil.writeURL(writer,
			"The following URL is a result of calling renderResponse.createResourceURL() and setting resource parameters:",
			resourceURL.toString());

	}

}
