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
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portletbox.issuesutil.HTMLUtil;


/**
 * @author  Neil Griffin
 */
public class TestPortlet3_8 extends GenericPortlet {

	private final String ACTION_TEST = "ActionTest";

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {

		// Writer writer = new ConsoleHTMLWriter();
		StringWriter writer = new StringWriter();

		// output the parameters on the Action request -

		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.ACTION_PHASE, "publicParameterMap",
			actionRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.ACTION_PHASE, "privateParameterMap",
			actionRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);
		HTMLUtil.writeMapCompact(writer, PortletRequest.ACTION_PHASE, "parameterMap", actionRequest.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		// Get test to be performed

		String actionTest = actionRequest.getParameter(ACTION_TEST);
		int iActionTest = 0;

		if (actionTest != null) {

			try {
				iActionTest = Integer.parseInt(actionTest);
			}
			catch (Exception e) {
			}
		}

		writer.write("Performing test " + iActionTest);
		writer.write(HTMLUtil.HR_TAG);

		switch (iActionTest) {

		case 1: {

			// Try to set & delete public render parameter from ActionResponse -

			actionResponse.setRenderParameter("publicRenderParameter1", "15");

			try {
				actionResponse.setRenderParameter("publicRenderParameter1", (String) null);
			}
			catch (Exception e) {
				writer.write("remove publicRenderParameter1 from ActionResponse failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 2: {

			// Set and try to delete private render parameter from ActionResponse -

			actionResponse.setRenderParameter("privateRenderParameter1", "1");

			try {
				actionResponse.setRenderParameter("privateRenderParameter1", (String) null);
			}
			catch (Exception e) {
				writer.write("remove privateRenderParameter1 from ActionResponse failed.<br/>" + e.toString() +
					"<br/>");
			}

			break;
		}

		case 3: {

			// Set and try to delete private render parameter from ActionResponse thru values[] -

			actionResponse.setRenderParameter("privateRenderParameter2", "1");

			try {
				String[] values = { null };
				actionResponse.setRenderParameter("privateRenderParameter2", values);
			}
			catch (Exception e) {
				writer.write("remove privateRenderParameter2 from ActionResponse using values[] failed.<br/>" +
					e.toString() + "<br/>");
			}

			break;
		}

		case 4: {
			// Set Parameters using map directly (initial map empty) -

			String[] values = { "Fred", "Wilma", "Pebbles" };

			try {
				actionResponse.getRenderParameterMap().put("privateRenderParameter3", values);
			}
			catch (Exception e) {
				writer.write("Putting map values directly failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 5: {
			// Set Parameters using map directly (initial map non-empty) -

			actionResponse.setRenderParameter("privateRenderParameter2", "1");

			String[] values = { "Fred", "Wilma", "Pebbles" };

			try {
				actionResponse.getRenderParameterMap().put("privateRenderParameter3", values);
			}
			catch (Exception e) {
				writer.write("Putting map values directly failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 6: {
			// Set Parameters using setParameters() (initial map empty) -

			String[] values2 = { "Barney", "Betty" };

			try {
				Map<String, String[]> parmMap = actionResponse.getRenderParameterMap();
				parmMap.put("privateRenderParameter4", values2);
				actionResponse.setRenderParameters(parmMap);
			}
			catch (Exception e) {
				writer.write("setRenderParameters() failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 7: {
			// Set Parameters using setParameters() (initial map non-empty) -

			actionResponse.setRenderParameter("privateRenderParameter2", "1");

			String[] values2 = { "Barney", "Betty" };

			try {
				Map<String, String[]> parmMap = actionResponse.getRenderParameterMap();
				parmMap.put("privateRenderParameter4", values2);
				actionResponse.setRenderParameters(parmMap);
			}
			catch (Exception e) {
				writer.write("setRenderParameters()  failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 8: {
			// Set parameters on response and remove them using cleared map -

			actionResponse.setRenderParameter("privateRenderParameter2", "177");
			actionResponse.setRenderParameter("publicRenderParameter1", "354");

			Map<String, String[]> parmMap = actionResponse.getRenderParameterMap();

			try {
				parmMap.clear();
			}
			catch (Exception e) {
				writer.write("parmMap.clear() failed.<br/>" + e.toString() + "<br/>");
			}

			try {
				actionResponse.setRenderParameters(parmMap);
			}
			catch (Exception e) {
				writer.write("setRenderParameters()  failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 9: {
			// Set parameters on response and remove them using map of nulled values -

			actionResponse.setRenderParameter("privateRenderParameter2", "177");
			actionResponse.setRenderParameter("publicRenderParameter1", "354");

			Map<String, String[]> parmMap = actionResponse.getRenderParameterMap();

			Set<String> keySet = parmMap.keySet();

			for (String key : keySet) {
				String[] values = { null };

				try {
					parmMap.put(key, values);
				}
				catch (Exception e) {
					writer.write("parmMap.put(key, values)  failed.<br/>" + e.toString() + "<br/>");
				}
			}

			try {
				actionResponse.setRenderParameters(parmMap);
			}
			catch (Exception e) {
				writer.write("setRenderParameters()  failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 10: {
			// Copy all parameters on ActionRequest to ActionResponse -

			Map<String, String[]> parmMap = actionRequest.getParameterMap();

			try {
				actionResponse.setRenderParameters(parmMap);
			}
			catch (Exception e) {
				writer.write("setRenderParameters()  failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 11: {

			// Remove public render parameter from ActionResponse -

			try {
				actionResponse.removePublicRenderParameter("publicRenderParameter1");
			}
			catch (Exception e) {
				writer.write("removePublicRenderParameter() from ActionResponse failed.<br/>" + e.toString() + "<br/>");
			}

			break;
		}

		case 12: {
			break;
		}

		case 13: {
			break;
		}

		} // switch

		if (iActionTest > 0) {
			actionResponse.setRenderParameter(ACTION_TEST, actionTest);
		}

		String writtenStuff = writer.toString();
		actionRequest.getPortletSession().setAttribute("ActionString", writtenStuff);
	}

	@Override
	protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
		IOException {

		// Get test to be performed

		String actionTest = renderRequest.getParameter(ACTION_TEST);
		int iActionTest = 0;

		if (actionTest != null) {

			try {
				iActionTest = Integer.parseInt(actionTest);
			}
			catch (Exception e) {
			}
		}

		PrintWriter writer = renderResponse.getWriter();

		String actionString = (String) renderRequest.getPortletSession().getAttribute("ActionString");

		if (actionString != null) {
			writer.write("Messages from Action Phase:<br/>");
			writer.write(actionString);
			writer.write(HTMLUtil.HR_TAG);
			writer.write(HTMLUtil.HR_TAG);
			renderRequest.getPortletSession().removeAttribute("ActionString");
		}

		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "publicParameterMap",
			renderRequest.getPublicParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "privateParameterMap",
			renderRequest.getPrivateParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "parameterMap", renderRequest.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		// Form submitted with ActionURL
		PortletURL actionURL = renderResponse.createActionURL();

		// Add parameter through map before other parameters have been set -

		if (iActionTest == 12) {

			// Set parameters using map directly -

			Map<String, String[]> parmMap = actionURL.getParameterMap();

			if (parmMap == null) {
				writer.write("parmMap is null.<br/>");
			}
			else {
				writer.write("parmMap isEmpty=" + parmMap.isEmpty() + "<br/>");
				writer.write("parmMap has " + parmMap.size() + " elements.<br/>");
			}

			String[] values = { "30", "40", "50" };

			try {
				actionURL.getParameterMap().put("actionURLParameter2", values);
			}
			catch (Exception e) {
				writer.write("Putting map values directly failed.<br/>" + e.toString() + "<br/>");
			}
		}

		// Set public & private render parameters using setParameter() -

		actionURL.setParameter("publicRenderParameter1", "100");
		actionURL.setParameter("actionURLParameter1", "1");

		// Add parameter through map after other parameters have been set -

		if (iActionTest == 13) {

			// Set parameters using map directly -

			Map<String, String[]> parmMap = actionURL.getParameterMap();

			if (parmMap == null) {
				writer.write("parmMap is null.<br/>");
			}
			else {
				writer.write("parmMap isEmpty=" + parmMap.isEmpty() + "<br/>");
				writer.write("parmMap has " + parmMap.size() + " elements.<br/>");
			}

			String[] values = { "30", "40", "50" };

			try {
				actionURL.getParameterMap().put("actionURLParameter2", values);
			}
			catch (Exception e) {
				writer.write("Putting map values directly failed.<br/>" + e.toString() + "<br/>");
			}
		}

		// Add button for executing action with set parameters -

		writer.write("<form action=\"" + actionURL.toString() + "\" method=\"post\">");
		writer.write("<label>formParameter1</label>");
		writer.write("<input");

		String namespace = renderResponse.getNamespace();
		writer.write(" name=\"" + namespace + "formParameter1\"");
		writer.write(" value=\"1\"");
		writer.write("/>");
		writer.write("<input type=\"submit\" value=\"Invoke ACTION_PHASE\" />");
		writer.write("</form>");

		// display parameters set on URL -

		HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "actionURL.parameterMap",
			actionURL.getParameterMap());
		writer.write(HTMLUtil.HR_TAG);

		// Buttons for tests -

		writer.write("<table border=\"0\"><tr>");

		actionURL = renderResponse.createActionURL();
		writeButton(writer, actionURL.toString(), "Action w/o parameters");
		actionURL.setParameter(ACTION_TEST, "1");
		writeButton(writer, actionURL.toString(), "Set & Remove Public Parm");
		actionURL.setParameter(ACTION_TEST, "2");
		writeButton(writer, actionURL.toString(), "Set & Remove Private Parm");
		actionURL.setParameter(ACTION_TEST, "3");
		writeButton(writer, actionURL.toString(), "Remove Parameters using values[]");

		writer.write("</tr><tr>");

		actionURL.setParameter(ACTION_TEST, "4");
		writeButton(writer, actionURL.toString(), "Set Parms direct Map #1");
		actionURL.setParameter(ACTION_TEST, "5");
		writeButton(writer, actionURL.toString(), "Set Parms direct Map #2");
		actionURL.setParameter(ACTION_TEST, "6");
		writeButton(writer, actionURL.toString(), "SetParameters() #1");
		actionURL.setParameter(ACTION_TEST, "7");
		writeButton(writer, actionURL.toString(), "SetParameters() #2");

		writer.write("</tr><tr>");

		actionURL.setParameter(ACTION_TEST, "8");
		writeButton(writer, actionURL.toString(), "Remove Parms using cleared map");
		actionURL.setParameter(ACTION_TEST, "9");
		writeButton(writer, actionURL.toString(), "Remove Parms, values[]={null}");

		actionURL = renderResponse.createActionURL();
		actionURL.setParameter("publicRenderParameter1", "157");
		actionURL.setParameter("actionURLParameter1", "342");
		actionURL.setParameter(ACTION_TEST, "10");
		writeButton(writer, actionURL.toString(), "Copy action parms to response");

		actionURL = renderResponse.createActionURL();
		actionURL.setParameter(ACTION_TEST, "11");
		writeButton(writer, actionURL.toString(), "removePublicRenderParameter()");

		writer.write("</tr><tr>");

		actionURL.setParameter(ACTION_TEST, "12");
		writeButton(writer, actionURL.toString(), "actionURL.setParameters() before");
		actionURL.setParameter(ACTION_TEST, "13");
		writeButton(writer, actionURL.toString(), "actionURL.setParameters() after");

		writer.write("</tr></table>");

		writer.write(HTMLUtil.HR_TAG);
	}

	private void writeButton(PrintWriter writer, String url, String text) {
		writer.write("<td><form action=\"" + url + "\" method=\"post\">");
		writer.write("<input type=\"submit\" value=\"" + text + "\" />");
		writer.write("</form></td>");
	}

}
