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
public class TestPortlet3_7 extends GenericPortlet {

   private final String ACTION_TEST             = "ActionTest";
   private final int    TEST_DELETE_PARAMETERS  = 1;
   private final int    TEST_SET_PARAMETERS     = 2;
   private final int    TEST_SET_PARAMETERS_MAP = 3;
   
	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
		IOException {

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

      if ((iActionTest == TEST_DELETE_PARAMETERS) || (iActionTest == TEST_SET_PARAMETERS)) {
         actionResponse.setRenderParameter("publicRenderParameter1", "1");
         actionResponse.setRenderParameter("privateRenderParameter1", "1");

         if (iActionTest == TEST_DELETE_PARAMETERS) {
            actionResponse.setRenderParameters(new HashMap<String, String[]>());
         }
      }
      
      if ((iActionTest == TEST_SET_PARAMETERS_MAP)) {
         HashMap<String, String[]> map = new HashMap<String, String[]>();
         String parm[] = new String[1];
         parm[0] = "PORTLETSPEC3-7-PublicParm";
         map.put("publicRenderParameter1", parm);
         String parm1[] = new String[1];
         parm1[0] = "PORTLETSPEC3-7-PrivateParm";
         map.put("privateRenderParameter1", parm1);
         actionResponse.setRenderParameters(map);         
      }


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
		actionURL.setParameter(ACTION_TEST, "" + TEST_DELETE_PARAMETERS);
		writer.write("<form action=\"" + actionURL.toString() + "\" method=\"post\">");
		writer.write("<label>formParameter1</label>");
		writer.write("<input");
		String namespace = renderResponse.getNamespace();
		writer.write(" name=\"" + namespace + "formParameter1\"");
		writer.write(" value=\"1\"");
		writer.write("/>");
		writer.write("<input type=\"submit\" value=\"Delete Parameters\" />");
		writer.write("</form>");
	    
      actionURL = renderResponse.createActionURL();
		actionURL.setParameter(ACTION_TEST, "" + TEST_SET_PARAMETERS);
      writer.write("<form action=\"" + actionURL.toString() + "\" method=\"post\">");
      writer.write("<input type=\"submit\" value=\"Set Parameters\" />");
      writer.write("</form>");
      
      actionURL = renderResponse.createActionURL();
      actionURL.setParameter(ACTION_TEST, "" + TEST_SET_PARAMETERS_MAP);
      writer.write("<form action=\"" + actionURL.toString() + "\" method=\"post\">");
      writer.write("<input type=\"submit\" value=\"Set Parms with Map\" />");
      writer.write("</form>");

		writer.write(HTMLUtil.HR_TAG);

	}
}
