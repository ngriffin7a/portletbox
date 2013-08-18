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
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.xml.namespace.QName;

import com.liferay.portletbox.issuesutil.HTMLUtil;
import com.liferay.portletbox.issuesutil.TableWriter;


/**
 * @author  Scott Nicklous
 */
@SuppressWarnings("unused")
public class TestPortlet3_15_Companion extends GenericPortlet {

   @Override
   public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException,
   IOException {

      StringWriter writer = new StringWriter();
      writer.write("Event was sent.");
      String writtenStuff = writer.toString();
      actionRequest.getPortletSession().setAttribute("ActionString", writtenStuff);
      
      QName eventQName = new QName(getDefaultNamespace(), TestPortlet3_15_Able.EVENT_NAME);
      actionResponse.setEvent(eventQName, "Sent by companion");

   }


   @Override
   protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
   IOException {

      PrintWriter writer = renderResponse.getWriter();

      // If available, write out messages from action request -

      String actionString = (String) renderRequest.getPortletSession().getAttribute("ActionString");
      if (actionString != null) {
         writer.write("Messages from Action Phase:<br/>");
         writer.write(actionString);
         renderRequest.getPortletSession().removeAttribute("ActionString");
      }

      writer.write(HTMLUtil.HR_TAG);

      // Create URLs for tests -

      TableWriter tw = new TableWriter(writer, 2);
      tw.startTable();

      // Create action URL, set public & private render parameters

      {
         String testName = "Send Event";      
         PortletURL actionURL = null;
         try {actionURL = renderResponse.createActionURL();}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"createActionURL() failed.<br/>" + e.toString() + "<br/>"); actionURL=null;}

         if (actionURL != null) {
            tw.writeButton(testName,  actionURL.toString() );
         }
      }

      tw.endTable();
   }

}
