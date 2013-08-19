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

import java.util.Map;
import java.util.Set;
import java.util.Locale;

import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;
import javax.portlet.PortletMode;
import javax.portlet.WindowState;

import com.liferay.portletbox.issuesutil.HTMLUtil;
import com.liferay.portletbox.issuesutil.TableWriter;


/**
 * @author  Neil Griffin
 */
public class TestPortlet3_26 extends GenericPortlet {
   
   private final String RES_TEST = "ResourceTest";
   private enum ResourceTest {NONE, BEFORE_GET, AFTER_GET, AFTER_FLUSH,
      AFTER_PARTIAL_WRITING,AFTER_COMPLETE_WRITING} 

   @Override
   public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
         throws PortletException, IOException {

      // Get test to be performed

      String test = resourceRequest.getParameter(RES_TEST);
      ResourceTest rt = ResourceTest.NONE;

      if (test != null) {
         try {
            rt = ResourceTest.valueOf(test);
         }
         catch (Exception e) {
         }
      }

      resourceResponse.setContentType("text/html");

      // Set header information depending on test type -
      if (rt == ResourceTest.BEFORE_GET) {
         resourceResponse.setContentType("text/plain");
         resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "220");
         resourceResponse.setLocale(new Locale("EN", "NZ", "P26"));
         resourceResponse.setContentLength(1000);
      }

      PrintWriter writer = resourceResponse.getWriter();

      // Set header information depending on test type -
      if (rt == ResourceTest.AFTER_GET) {
         resourceResponse.setContentType("text/plain");
         resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "230");
         resourceResponse.setLocale(new Locale("EN", "AU", "P27"));
         resourceResponse.setContentLength(2000);
      }

      // Set header information depending on test type -
      if (rt == ResourceTest.AFTER_FLUSH) {
         resourceResponse.flushBuffer();
         resourceResponse.setContentType("text/plain");
         resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "235");
         resourceResponse.setLocale(new Locale("EN", "AU", "P27"));
         resourceResponse.setContentLength(2000);
      }

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

      // Set header information depending on test type -
      if (rt == ResourceTest.AFTER_PARTIAL_WRITING) {
         resourceResponse.setContentType("text/plain");
         resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "240");
         resourceResponse.setLocale(new Locale("EN", "CA", "P27a"));
         resourceResponse.setContentLength(3000);
      }
      
      // display the cacheability, portlet mode and window state -

      WindowState ws = resourceRequest.getWindowState();
      String text = "WindowState: " + ((null==ws) ? ("(null)") : (ws.toString())) + "<br/>";
      writer.write(text);
      
      PortletMode pm = resourceRequest.getPortletMode();
      text = "PortletMode: " + ((null==pm) ? ("(null)") : (pm.toString())) + "<br/>";
      writer.write(text);
      
      writer.write("Cacheability: " + resourceRequest.getCacheability() + "<br/>");
      writer.write(HTMLUtil.HR_TAG);

      writer.write("<span>");

      // Create resource URL with no further processing -

      TableWriter tw = new TableWriter(writer);
      tw.startTable();


      // Resource URL with no further processing -

      {
         String testName = "ResourceURL, no add'l parameters";      
         ResourceURL resourceURL = resourceResponse.createResourceURL();
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL with resource parameters -

      {
         String testName = "ResourceURL w/ resource parameters";     
         ResourceURL resourceURL = resourceResponse.createResourceURL();
         resourceURL.setParameter("publicRenderParameter1", "PORTLETSPEC3-26: 400");
         resourceURL.setParameter("ResourceParameter2", "PORTLETSPEC3-26: 500");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Create resource URL, setting parameter, cacheability=FULL -

      {
         String      testName = "ResourceURL, cacheability FULL";      
         ResourceURL resourceURL = resourceResponse.createResourceURL();
         try {resourceURL.setCacheability(ResourceURL.FULL);}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
         resourceURL.setParameter("resourceURLParameter3", "PORTLETSPEC3-26: 550");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Create resource URL, setting parameter, cacheability=PORTLET -

      {
         String      testName = "ResourceURL, cacheability PORTLET";      
         ResourceURL resourceURL = resourceResponse.createResourceURL();
         try {resourceURL.setCacheability(ResourceURL.PORTLET);}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
         resourceURL.setParameter("resourceURLParameter3", "PORTLETSPEC3-26: 660");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Create resource URL, setting parameter, cacheability=PAGE -

      {
         String      testName = "ResourceURL, cacheability PAGE";      
         ResourceURL resourceURL = resourceResponse.createResourceURL();
         try {resourceURL.setCacheability(ResourceURL.PAGE);}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
         resourceURL.setParameter("resourceURLParameter3", "PORTLETSPEC3-26: 770");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      tw.endTable();

      writer.write("</span>");
      writer.write("</body></html>");

      // Set header information depending on test type -
      if (rt == ResourceTest.AFTER_COMPLETE_WRITING) {
         resourceResponse.setContentType("text/plain");
         resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, "250");
         resourceResponse.setLocale(new Locale("EN", "GB", "P27b"));
         resourceResponse.setContentLength(4000);
      }
   }

   @Override
   protected void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws PortletException,
   IOException {

      PrintWriter writer = renderResponse.getWriter();
      writer.write("This portlet provides tests for issues PORTLETSPEC3-26 and PORTLETSPEC3-27.<br/>");

      // Display current parameters -

      writer.write(HTMLUtil.HR_TAG);
      HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "publicParameterMap",
            renderRequest.getPublicParameterMap());
      writer.write(HTMLUtil.HR_TAG);
      HTMLUtil.writeMapCompact(writer, PortletRequest.RENDER_PHASE, "privateParameterMap",
            renderRequest.getPrivateParameterMap());

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
         renderURL.setParameter("publicRenderParameter1", "PORTLETSPEC3-26: 200");
         renderURL.setParameter("privateRenderParameter1", "PORTLETSPEC3-26: 100");
         tw.writeURL(testName,  renderURL.toString() );
      }

      // Resource URL with no further processing -

      {
         String testName = "ResourceURL, no add'l parameters";      
         ResourceURL resourceURL = renderResponse.createResourceURL();
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL with resource parameters -

      {
         String testName = "ResourceURL w/ resource parameters";		
         ResourceURL resourceURL = renderResponse.createResourceURL();
         resourceURL.setParameter("publicRenderParameter1", "PORTLETSPEC3-26: 40");
         resourceURL.setParameter("ResourceParameter2", "PORTLETSPEC3-26: 50");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Create resource URL, setting parameter, cacheability=FULL -

      {
         String      testName = "ResourceURL, cacheability FULL";      
         ResourceURL resourceURL = renderResponse.createResourceURL();
         try {resourceURL.setCacheability(ResourceURL.FULL);}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
         resourceURL.setParameter("resourceURLParameter3", "PORTLETSPEC3-26: 55");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Create resource URL, setting parameter, cacheability=PORTLET -

      {
         String      testName = "ResourceURL, cacheability PORTLET";      
         ResourceURL resourceURL = renderResponse.createResourceURL();
         try {resourceURL.setCacheability(ResourceURL.PORTLET);}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
         resourceURL.setParameter("resourceURLParameter3", "PORTLETSPEC3-26: 66");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Create resource URL, setting parameter, cacheability=PAGE -

      {
         String      testName = "ResourceURL, cacheability PAGE";      
         ResourceURL resourceURL = renderResponse.createResourceURL();
         try {resourceURL.setCacheability(ResourceURL.PAGE);}
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
         resourceURL.setParameter("resourceURLParameter3", "PORTLETSPEC3-26: 77");
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL, write headers before getWriter -

      {
         String testName = "Write headers before getWriter";     
         ResourceURL resourceURL = renderResponse.createResourceURL();
         resourceURL.setParameter(RES_TEST, ResourceTest.BEFORE_GET.toString());
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL, write headers after getWriter -

      {
         String testName = "Write headers after getWriter";     
         ResourceURL resourceURL = renderResponse.createResourceURL();
         resourceURL.setParameter(RES_TEST, ResourceTest.AFTER_GET.toString());
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL, write headers after flushBuffer -

      {
         String testName = "Write headers after flushBuffer";     
         ResourceURL resourceURL = renderResponse.createResourceURL();
         resourceURL.setParameter(RES_TEST, ResourceTest.AFTER_FLUSH.toString());
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL, write headers after partial write -

      {
         String testName = "Write headers after partial write";     
         ResourceURL resourceURL = renderResponse.createResourceURL();
         resourceURL.setParameter(RES_TEST, ResourceTest.AFTER_PARTIAL_WRITING.toString());
         tw.writeURL(testName,  resourceURL.toString() );
      }

      // Resource URL, write headers after complete output is written -

      {
         String testName = "Write headers after complete write";     
         ResourceURL resourceURL = renderResponse.createResourceURL();
         resourceURL.setParameter(RES_TEST, ResourceTest.AFTER_COMPLETE_WRITING.toString());
         tw.writeURL(testName,  resourceURL.toString() );
      }


      tw.endTable();
   }

}
