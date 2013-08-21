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

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceResponse;

import com.liferay.portletbox.issuesutil.HTMLUtil;
import com.liferay.portletbox.issuesutil.TableWriter;


/**
 * @author  Scott Nicklous
 */
public class TestPortlet3_35 extends GenericPortlet {
   
   // use public render parameter so that it cen be reset from another portlet
   // if rendering gets screwed up.
   private final String RENDER_PARM = "RenderParm";
   private enum RenderTest {NONE, BEFORE_GET, AFTER_GET, WRITE_RESET} 

   @Override
   protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
   IOException {

      // Get test to be performed

      String test = renderRequest.getParameter(RENDER_PARM);
      RenderTest rt = RenderTest.NONE;

      if (test != null) {
         try {rt = RenderTest.valueOf(test);}
         catch (Exception e) {}
      }

      // for things that occur before we have a PrintWriter
      StringWriter earlyWriter = new StringWriter();
      
      // collect infos about buffer -
      earlyWriter.write("Buffer size: " + renderResponse.getBufferSize() + "<br/>");
      earlyWriter.write("Character encoding: " + renderResponse.getCharacterEncoding() + "<br/>");
      earlyWriter.write("Content Type: " + renderResponse.getContentType() + "<br/>");
      earlyWriter.write("is committed: " + renderResponse.isCommitted() + "<br/>");
      earlyWriter.write("Locale: " + renderResponse.getLocale() + "<br/>");
      
      renderResponse.setContentType("text/html");

      // Set header information depending on test type -
      if (rt == RenderTest.BEFORE_GET) {
         try {renderResponse.setContentType("text/plain");}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at setContentType(\"text/plain\"):<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
         try {renderResponse.setBufferSize(2000);}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at setBufferSize(2000):<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
         try {renderResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "220");}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at setProperty(ResourceResponse.HTTP_STATUS_CODE, \"220\"):<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
         try {renderResponse.resetBuffer();}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at resetBuffer():<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
      }

      PrintWriter writer = renderResponse.getWriter();

      // Set header information depending on test type -
      if (rt == RenderTest.AFTER_GET) {
         earlyWriter.write("<br/>After getWriter but before writing:<br/>");
         try {renderResponse.setContentType("text/plain");}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at setContentType(\"text/plain\"):<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
         try {renderResponse.setBufferSize(2000);}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at setBufferSize(2000):<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
         try {renderResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "230");}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at setProperty(ResourceResponse.HTTP_STATUS_CODE, \"220\"):<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
         try {renderResponse.resetBuffer();}
         catch (Exception e) {
            earlyWriter.write("<br/>Exception at resetBuffer():<br/>");
            earlyWriter.write(e.getClass().getName() + ": " + e.getMessage() + "<br/>");
         }
      }

      // Set header information depending on test type -
      if (rt == RenderTest.WRITE_RESET) {
         writer.write("Test line before attempting to reset the buffer.<br/>");
         try {renderResponse.resetBuffer();}
         catch (Exception e) {
            writer.write("<br/>Exception at resetBuffer():<br/>");
            writer.write(e.getClass().getName() + ": " + e.getMessage() + "<br/><br/>");
         }
         writer.write("Test line after attempting to reset the buffer.<br/>");
      }

      writer.write(HTMLUtil.HR_TAG);
      writer.write("This portlet provides tests for buffer use.<br/><br/>");

      // Display current parameters -

      HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "parameterMap",
            renderRequest.getParameterMap());
      writer.write(HTMLUtil.HR_TAG);
      
      writer.write("Early Info (before getWriter called):<br/>");
      writer.write(earlyWriter.toString());
      writer.write(HTMLUtil.HR_TAG);
      
      // collect infos about buffer -
      writer.write("Late Info (after test has been carried out):<br/>");
      writer.write("Buffer size: " + renderResponse.getBufferSize() + "<br/>");
      writer.write("Character encoding: " + renderResponse.getCharacterEncoding() + "<br/>");
      writer.write("Content Type: " + renderResponse.getContentType() + "<br/>");
      writer.write("is committed: " + renderResponse.isCommitted() + "<br/>");
      writer.write("Locale: " + renderResponse.getLocale() + "<br/>");
      writer.write(HTMLUtil.HR_TAG);
      
      // collect infos about buffer -
      writer.write("Request properties:<br/>");
      Enumeration<String> props = renderRequest.getPropertyNames();
      while (props.hasMoreElements()) {
         String prop = props.nextElement();
         Enumeration<String> vals = renderRequest.getProperties(prop);
         while (vals.hasMoreElements()) {
            String val = vals.nextElement();
            String v2 = val.substring(0, ((40<val.length()) ? 40 : val.length()));
            if (val.length() > 40) v2 += "...";
            writer.write("Prop: " + prop + ", val: " + v2 + "<br/>");
         }
      }
      writer.write(HTMLUtil.HR_TAG);

      // Create URLs for tests -

      TableWriter tw = new TableWriter(writer);
      tw.startTable();

      // Create render URL w/o parameters -

      {
         String testName = "Remove all render parameters";      
         PortletURL renderURL = renderResponse.createRenderURL();
         Map<String, String[]> prp = renderRequest.getPublicParameterMap();
         for (String key : (Set<String>)prp.keySet()) {
            renderURL.removePublicRenderParameter(key);
         }
         tw.writeURL(testName,  renderURL.toString() );
      }

      // set public & private render parameters -

      {
         String testName = "Set render parameters";      
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter("publicRenderParameter1", "TestPortlet3_35: 200");
         renderURL.setParameter("privateRenderParameter1", "TestPortlet3_35: 100");
         tw.writeURL(testName,  renderURL.toString() );
      }

      // Render URL, set buffer before getWriter -

      {
         String testName = "set buffer before getWriter";     
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_PARM, RenderTest.BEFORE_GET.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }

      // Render URL, set buffer after getWriter -

      {
         String testName = "set buffer after getWriter";     
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_PARM, RenderTest.AFTER_GET.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }

      // Render URL, write to stream, then reset buffer -

      {
         String testName = "write, then reset buffer";     
         PortletURL renderURL = renderResponse.createRenderURL();
         renderURL.setParameter(RENDER_PARM, RenderTest.WRITE_RESET.toString());
         tw.writeURL(testName,  renderURL.toString() );
      }


      tw.endTable();
   }

}
