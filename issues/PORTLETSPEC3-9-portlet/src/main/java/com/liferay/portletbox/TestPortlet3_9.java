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
public class TestPortlet3_9 extends GenericPortlet {

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
