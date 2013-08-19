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
public class TestPortlet3_15_Able extends GenericPortlet {

   protected final static String ACTION_NAME   = "TestPortlet3_15-Action";
   protected final static String EVENT_NAME    = "AnnotatedEvent";
   protected final static String RENDER_NAME   = "view";
   
   public final String RENDER_PARM_NAME        = "RenderParameter";
   
   private final String PORTLET_SUFFIX         = "Able";

   @ProcessAction(name=ACTION_NAME)
   public void ActionAble(ActionRequest actionRequest, ActionResponse actionResponse) 
         throws PortletException, IOException {
      actionResponse.setRenderParameter( RENDER_PARM_NAME, "Render Parameter set by Action" + PORTLET_SUFFIX);
   }
   
   @ProcessEvent(name=EVENT_NAME)
   public void EventAble(EventRequest eventRequest, EventResponse eventResponse) 
         throws PortletException, IOException {
      eventResponse.setRenderParameter( RENDER_PARM_NAME, "Render Parameter set by Event"  + PORTLET_SUFFIX);
   }
   
   @RenderMode(name=RENDER_NAME)
   public void RenderAble(RenderRequest renderRequest, RenderResponse renderResponse) 
         throws PortletException, IOException {
      writePortletText(PORTLET_SUFFIX, renderRequest, renderResponse);
   }
   
   // common render method used by all subclasses.   
   protected void writePortletText(String suffix, RenderRequest renderRequest, 
         RenderResponse renderResponse) throws IOException {

      PrintWriter writer = renderResponse.getWriter();
      
      writer.write("Rendered by Render"  + suffix + " ... (Should be Render3_15)<br/>");
      writer.write("<ul>");
      writer.write("<li>TestPortlet3_15_Able extends GenericPortlet</li>");
      writer.write("<li>TestPortlet3_15_Baker extends TestPortlet3_15_Able</li>");
      writer.write("<li>TestPortlet3_15_Charlie extends TestPortlet3_15_Baker</li>");
      writer.write("<li>TestPortlet3_15 extends TestPortlet3_15_Charlie</li>");
      writer.write("</ul>");
      writer.write(HTMLUtil.HR_TAG);


      // If available, write out messages from action request -

      writer.write("Parameter from Action or Event Phase: <br/> ... (Should be set by (none), EventBaker or ActionCharlie)<br/>");
      String parmText = (String) renderRequest.getParameter(RENDER_PARM_NAME);
      if (parmText != null) {
         writer.write(parmText);
      } else {
         writer.write("(none)");
      }
      writer.write("<br/>" +  HTMLUtil.HR_TAG);

      // Create URLs & buttons

      TableWriter tw = new TableWriter(writer, 2);
      tw.startTable();

      // Create render URL w/o parameters -

      {
         String testName = "RenderURL";      
         PortletURL renderURL = renderResponse.createRenderURL();
         tw.writeURL(testName,  renderURL.toString() );
      }

      // Create action URL, set public & private render parameters

      {
         String testName = "Do action";      
         PortletURL actionURL = null;
         try {actionURL = renderResponse.createActionURL();}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"createActionURL() failed.<br/>" + e.toString() + "<br/>"); actionURL=null;}
         if (actionURL != null) {
            actionURL.setParameter(ActionRequest.ACTION_NAME, TestPortlet3_15_Able.ACTION_NAME);
            tw.writeButton(testName,  actionURL.toString() );
         }
      }
      
      tw.endTable();
   }


}
