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

import javax.portlet.ProcessAction;
import javax.portlet.ProcessEvent;
import javax.portlet.RenderMode;

import com.liferay.portletbox.issuesutil.HTMLUtil;
import com.liferay.portletbox.issuesutil.TableWriter;


/**
 * @author  Scott Nicklous
 */
@SuppressWarnings("unused")
public class TestPortlet3_23 extends GenericPortlet {

   private final String ACTION_TEST             = "ActionTest";
   private final String RENDER_TEST             = "RenderTest";
   
   protected enum ThrowType {NONE, PORTLET, IO, RUNTIME, NOOP}

   protected final static String ACTION_NAME   = "TestPortlet3_23-Action";
   protected final static String EVENT_NAME    = "AnnotatedEvent";
   protected final static String RENDER_NAME   = "view";
   
   public final String RENDER_PARM_NAME        = "RenderParameter";

   @SuppressWarnings("incomplete-switch")
   @ProcessAction(name=ACTION_NAME)
   public void ActionAble(ActionRequest actionRequest, ActionResponse actionResponse) 
         throws PortletException, IOException {

      // Get test to be performed

      String actionTest = actionRequest.getParameter(ACTION_TEST);
      ThrowType tt = ThrowType.NONE;

      if (actionTest != null) {

         try {
            tt = ThrowType.valueOf(actionTest);
         }
         catch (Exception e) {
         }
      }
      
      // actionResponse.setRenderParameter( RENDER_PARM_NAME, "Action " + tt.toString() + " will be performed." );
      actionRequest.getPortletSession().setAttribute(RENDER_PARM_NAME, "Action " + tt.toString() + " will be performed." );
      
      switch(tt) {
         case PORTLET: 
            throw new PortletException("PortletException thrown from action method");
         case IO: 
            throw new IOException("IOException thrown from action method");
         case RUNTIME: 
            throw new RuntimeException("RuntimeException thrown from action method");
      }
   }
   
   @SuppressWarnings("incomplete-switch")
   @ProcessEvent(name=EVENT_NAME)
   public void EventAble(EventRequest eventRequest, EventResponse eventResponse) 
         throws PortletException, IOException {
      // Get test to be performed

      String eventName = eventRequest.getEvent().getName();
      String eventTest = (String)eventRequest.getEvent().getValue();
      ThrowType tt = ThrowType.NONE;

      if (eventTest != null) {

         try {
            tt = ThrowType.valueOf(eventTest);
         }
         catch (Exception e) {
         }
      }

      eventRequest.getPortletSession().setAttribute(RENDER_PARM_NAME, "EventName=" + eventName
      // eventResponse.setRenderParameter( RENDER_PARM_NAME, "EventName=" + eventName 
            + ". Exception " + tt.toString() + " will be thrown." );
      
      switch(tt) {
         case PORTLET: 
            throw new PortletException("PortletException thrown from event method");
         case IO: 
            throw new IOException("IOException thrown from event method");
         case RUNTIME: 
            throw new RuntimeException("RuntimeException thrown from event method");
      }
   }
   
   @SuppressWarnings("incomplete-switch")
   @RenderMode(name=RENDER_NAME)
   public void RenderAble(RenderRequest renderRequest, RenderResponse renderResponse) 
         throws PortletException, IOException {

      PrintWriter writer = renderResponse.getWriter();
      
      writer.write("This portlet implements annotated render, action, and event methods.");
      writer.write("Exceptions are thrown according to the buttons clicked. <br/>");
      writer.write(HTMLUtil.HR_TAG);

      // If available, write out messages from action request -

      writer.write("Parameter from Action or Event Phase: <br/>");
      
      String parmText = (String) renderRequest.getPortletSession().getAttribute(RENDER_PARM_NAME);
      //String parmText = (String) renderRequest.getParameter(RENDER_PARM_NAME);
      if (parmText != null) {
         writer.write(parmText);
         renderRequest.getPortletSession().removeAttribute(RENDER_PARM_NAME);
      } else {
         writer.write("(none)");
      }
      writer.write("<br/>" +  HTMLUtil.HR_TAG);

      // Create URLs & buttons

      TableWriter tw = new TableWriter(writer, 2);
      tw.startTable();

      // render w/o parameters
      
      {
         String testName = "Render, no parameters";
         PortletURL renderURL = renderResponse.createRenderURL();
         tw.writeURL(testName,  renderURL.toString() );
      }

      // throw no exception  in action method
      
      {
         String testName = "No exception in Action";
         PortletURL actionURL = renderResponse.createActionURL();
         actionURL.setParameter(ACTION_TEST, ThrowType.NONE.toString());
         actionURL.setParameter(ActionRequest.ACTION_NAME, TestPortlet3_23.ACTION_NAME);
         tw.writeButton(testName,  actionURL.toString() );
      }

      // throw PortletException in action method
      
      {
         String testName = "PortletException in Action";
         PortletURL actionURL = renderResponse.createActionURL();
         actionURL.setParameter(ACTION_TEST, ThrowType.PORTLET.toString());
         actionURL.setParameter(ActionRequest.ACTION_NAME, TestPortlet3_23.ACTION_NAME);
         tw.writeButton(testName,  actionURL.toString() );
      }

      // throw IO in action method
      
      {
         String testName = "IOException in Action";
         PortletURL actionURL = renderResponse.createActionURL();
         actionURL.setParameter(ACTION_TEST, ThrowType.IO.toString());
         actionURL.setParameter(ActionRequest.ACTION_NAME, TestPortlet3_23.ACTION_NAME);
         tw.writeButton(testName,  actionURL.toString() );
      }

      // throw Runtime in action method
      
      {
         String testName = "RuntimeException in Action";
         PortletURL actionURL = renderResponse.createActionURL();
         actionURL.setParameter(ACTION_TEST, ThrowType.RUNTIME.toString());
         actionURL.setParameter(ActionRequest.ACTION_NAME, TestPortlet3_23.ACTION_NAME);
         tw.writeButton(testName,  actionURL.toString() );
      }

      // throw other exception in action method
      
      {
         String testName = "Other Exception in Action";
         PortletURL actionURL = renderResponse.createActionURL();
         actionURL.setParameter(ACTION_TEST, ThrowType.NOOP.toString());
         actionURL.setParameter(ActionRequest.ACTION_NAME, TestPortlet3_23.ACTION_NAME);
         tw.writeButton(testName,  actionURL.toString() );
      }

      // throw PortletException in render method
      
      {
         String testName = "PortletException in Render";
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_TEST, ThrowType.PORTLET.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }

      // throw IO in render method
      
      {
         String testName = "IOException in Render";
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_TEST, ThrowType.IO.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }

      // throw Runtime in render method
      
      {
         String testName = "RuntimeException in Render";
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_TEST, ThrowType.RUNTIME.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }

      // throw other exception in render method
      
      {
         String testName = "Other Exception in Render";
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_TEST, ThrowType.NOOP.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }
      
      tw.endTable();
      
      // Now handling throwing event during render phase
      
      ThrowType tt = ThrowType.NONE;
      String renderParm = (String) renderRequest.getParameter(RENDER_TEST);
      if (renderParm != null) {

         try {
            tt = ThrowType.valueOf(renderParm);
         }
         catch (Exception e) {
         }

         writer.write("Render request: throwing exception of type: " + tt.toString() + "<br/>");
         writer.write(HTMLUtil.HR_TAG);

         switch(tt) {
            case PORTLET: 
               throw new PortletException("PortletException thrown from render method");
            case IO: 
               throw new IOException("IOException thrown from render method");
            case RUNTIME: 
               throw new RuntimeException("RuntimeException thrown from render method");
         }
      }

   }


}
