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
   
   public static final String NAME_SPACE = "http://liferay.com/events";
   public static final String EVENT_1_NAME = "PORTLETSPEC3-15-EVENT";
   public static final String EVENT_2_NAME = "ECHOED-EVENT";
   public static final String EVENT_3_NAME = "EVENT-TO-COMPANION";

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

      String writtenStuff = writer.toString();
      actionRequest.getPortletSession().setAttribute("ActionString", writtenStuff);
      
      QName eventQName = new QName(NAME_SPACE, EVENT_1_NAME);
      actionResponse.setEvent(eventQName, "Sent by companion");

   }

   
   @Override
   public void processEvent(EventRequest eventRequest, EventResponse eventResponse)
         throws PortletException, IOException {

      // Writer writer = new ConsoleHTMLWriter();
      StringWriter writer = new StringWriter();

      // output the parameters on the Event request -

      writer.write(HTMLUtil.HR_TAG);
      HTMLUtil.writeMapCompact(writer, PortletRequest.EVENT_PHASE, "publicParameterMap",
            eventRequest.getPublicParameterMap());
      writer.write(HTMLUtil.HR_TAG);
      HTMLUtil.writeMapCompact(writer, PortletRequest.EVENT_PHASE, "privateParameterMap",
            eventRequest.getPrivateParameterMap());
      writer.write(HTMLUtil.HR_TAG);
      HTMLUtil.writeMapCompact(writer, PortletRequest.EVENT_PHASE, "parameterMap", eventRequest.getParameterMap());
      writer.write(HTMLUtil.HR_TAG);

      String writtenStuff = writer.toString();
      eventRequest.getPortletSession().setAttribute("EventString", writtenStuff);

      eventResponse.setRenderParameter( "publicRenderParameter1", "Pub: Comp event resp." );
      eventResponse.setRenderParameter( eventRequest.getEvent().getName(), (String)eventRequest.getEvent().getValue() );
      eventResponse.setRenderParameter( "privateRenderParameter1", "Priv: Comp event resp." );
      
      QName eventQName = new QName(NAME_SPACE, EVENT_2_NAME);
      eventResponse.setEvent(eventQName, "Echoed by companion.");
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
         writer.write(HTMLUtil.HR_TAG);
         renderRequest.getPortletSession().removeAttribute("ActionString");
      }

      // If available, write out messages from event request -

      String eventString = (String) renderRequest.getPortletSession().getAttribute("EventString");
      if (eventString != null) {
         writer.write("Messages from Event Phase:<br/>");
         writer.write(eventString);
         writer.write(HTMLUtil.HR_TAG);
         renderRequest.getPortletSession().removeAttribute("EventString");
      }

      // Display current parameters -

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

      // Create URLs for tests -

      TableWriter tw = new TableWriter(writer);
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

      // Create render URL w/o parameters -

      {
         String testName = "RenderURL, remove all parameters";      
         PortletURL renderURL = renderResponse.createRenderURL();

         renderURL.removePublicRenderParameter("publicRenderParameter1");

         tw.writeURL(testName,  renderURL.toString() );
      }

      tw.endTable();
   }

}
