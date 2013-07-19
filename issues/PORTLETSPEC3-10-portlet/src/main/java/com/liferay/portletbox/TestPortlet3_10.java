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

import com.liferay.portletbox.issuesutil.HTMLUtil;
import com.liferay.portletbox.issuesutil.TableWriter;


/**
 * @author  Neil Griffin
 */
public class TestPortlet3_10 extends GenericPortlet {

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
   }

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

      writer.write("Cacheability is set to: " + resourceRequest.getCacheability());
      writer.write(HTMLUtil.HR_TAG);

      writer.write("<span>");

      // Create resource URL with no further processing -

      String testName = "createResourceURL(), no new parameters";		
      ResourceURL resourceURL = resourceResponse.createResourceURL();

      TableWriter tw = new TableWriter(writer);
      tw.startTable();

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter -

      testName = "createResourceURL(), set new parameter";		
      resourceURL = resourceResponse.createResourceURL();
      resourceURL.setParameter("resourceURLParameter4", "44");

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter, cacheability=FULL -

      testName = "createResourceURL(), set parm, cache=FULL";      
      resourceURL = resourceResponse.createResourceURL();
      try {resourceURL.setCacheability(ResourceURL.FULL);}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
      resourceURL.setParameter("resourceURLParameter4", "setInResURL-FULL");

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter, cacheability=PORTLET -

      testName = "createResourceURL(), set parm, cache=PORTLET";      
      resourceURL = resourceResponse.createResourceURL();
      try {resourceURL.setCacheability(ResourceURL.PORTLET);}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
      resourceURL.setParameter("resourceURLParameter4", "setInResURL-PORTLET");

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter, cacheability=PAGE -

      testName = "createResourceURL(), set parm, cache=PAGE";      
      resourceURL = resourceResponse.createResourceURL();
      try {resourceURL.setCacheability(ResourceURL.PAGE);}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
      resourceURL.setParameter("resourceURLParameter4", "setInResURL-PAGE");

      tw.writeURL(testName,  resourceURL.toString() );

      // Create render URL, set public & private render parameters

      testName = "createRenderURL(), set parameters";
      PortletURL renderURL = null;
      try {renderURL = resourceResponse.createRenderURL();}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"createRenderURL() failed.<br/>" + e.toString() + "<br/>"); renderURL=null;}

      if (renderURL != null) {
         renderURL.setParameter("publicRenderParameter1", "Public parameter set during Resource Phase");
         renderURL.setParameter("privateRenderParameter9", "Private parameter set during Resource Phase");
         tw.writeURL(testName,  renderURL.toString() );
      }

      // Create action URL, set public & private render parameters

      testName = "createActionURL(), set parameters";      
      PortletURL actionURL = null;
      try {actionURL = resourceResponse.createActionURL();}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"createActionURL() failed.<br/>" + e.toString() + "<br/>"); actionURL=null;}

      if (actionURL != null) {
         actionURL.setParameter("publicRenderParameter1", "Public parameter set during Resource Phase");
         actionURL.setParameter("privateRenderParameter9", "Private parameter set during Resource Phase");
         tw.writeButton(testName,  actionURL.toString() );
      }

      // Create render URL, set no parameters

      testName = "createRenderURL(), set no parameters";
      renderURL = null;
      try {renderURL = resourceResponse.createRenderURL();}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"createRenderURL() failed.<br/>" + e.toString() + "<br/>"); renderURL=null;}

      if (renderURL != null) {
         tw.writeURL(testName,  renderURL.toString() );
      }

      tw.endTable();

      writer.write("</span>");
      writer.write("</body></html>");
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

      // Create render URL w/o parameters -

      String testName = "createRenderURL() w/o parameters";      
      PortletURL renderURL = renderResponse.createRenderURL();

      tw.writeURL(testName,  renderURL.toString() );

      // Create Render URL, copying all parameters from request using map -

      testName = "createRenderURL() copying all parameters";      
      renderURL = renderResponse.createRenderURL();

      {  
         Map<String, String[]> parmMap = renderRequest.getParameterMap();
         renderURL.setParameters(parmMap);
      }
      tw.writeURL(testName,  renderURL.toString() );

      // set public & private render parameters -

      testName = "createRenderURL(), set private & public parameters";		
      renderURL = renderResponse.createRenderURL();

      renderURL.setParameter("publicRenderParameter1", "2");
      renderURL.setParameter("privateRenderParameter1", "1");

      tw.writeURL(testName,  renderURL.toString() );

      // set public & private render parameters, making use of map -

      testName = "createRenderURL(), renderURL.getParameterMap().put()";		
      renderURL = renderResponse.createRenderURL();

      renderURL.setParameter("publicRenderParameter1", "2");
      renderURL.setParameter("privateRenderParameter1", "1");

      String[] values = { "Fred", "Wilma", "Pebbles" };
      renderURL.getParameterMap().put("privateRenderParameter2", values);

      tw.writeURL(testName,  renderURL.toString() );

      // set & delete parameters on render URL through setParameter() using null string -
      {
         testName = "createRenderURL(), remove parameters #1";      
         renderURL = renderResponse.createRenderURL();

         renderURL.setParameter("privateRenderParameter1", "1");


         Map<String, String[]> parmMap = renderURL.getParameterMap();
         Set<String> keySet = parmMap.keySet();
         for (String key: keySet){
            try { renderURL.setParameter(key, (String)null); }
            catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove " + key + " from URL failed.<br/>" + e.toString() + "<br/>");}
         }


         tw.writeURL(testName,  renderURL.toString() );
      }

      // set & delete parameters on render URL through setParameter() using "" string -

      {
         testName = "createRenderURL(), remove parameters #2";      
         renderURL = renderResponse.createRenderURL();

         renderURL.setParameter("privateRenderParameter1", "1");

         Map<String, String[]> parmMap = renderURL.getParameterMap();
         Set<String> keySet = parmMap.keySet();
         for (String key : keySet) {
            try { renderURL.setParameter(key, ""); }
            catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove " + key + " from URL failed.<br/>" + e.toString() + "<br/>");}
         }

         tw.writeURL(testName,  renderURL.toString() );
      }

      // set & delete parameters on render URL through setParameters() using String[] {null} -

      {
         testName = "createRenderURL() remove parameters #3";      
         renderURL = renderResponse.createRenderURL();

         renderURL.setParameter("privateRenderParameter1", "1");

         Map<String, String[]> parmMap = renderURL.getParameterMap();
         Set<String> keySet = parmMap.keySet();
         String[] parmVals = {null};
         for (String key: keySet){
            try { parmMap.put(key, parmVals); }
            catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"updating map entry " + key + "failed.<br/>" + e.toString() + "<br/>");}
         }
         try { renderURL.setParameters(parmMap); }
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setParameters() failed.<br/>" + e.toString() + "<br/>");}

         tw.writeURL(testName,  renderURL.toString() );
      }

      // set & delete parameters on render URL through setParameters() using String[] {""} -

      {
         testName = "createRenderURL() remove parameters #4";      
         renderURL = renderResponse.createRenderURL();

         renderURL.setParameter("privateRenderParameter1", "1");

         Map<String, String[]> parmMap = renderURL.getParameterMap();
         Set<String> keySet = parmMap.keySet();
         String[] parmVals2 = {""};
         for (String key: keySet){
            try { parmMap.put(key, parmVals2); }
            catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"updating map entry " + key + "failed.<br/>" + e.toString() + "<br/>");}
         }
         try { renderURL.setParameters(parmMap); }
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setParameters() failed.<br/>" + e.toString() + "<br/>");}

         tw.writeURL(testName,  renderURL.toString() );
      }

      // render URL with public render parameter removed -

      testName = "createRenderURL() and removePublicRenderParameter()";      
      renderURL = renderResponse.createRenderURL();

      try {renderURL.removePublicRenderParameter("publicRenderParameter1");}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove publicRenderParameter1 from URL failed.<br/>" + e.toString() + "<br/>");}

      tw.writeURL(testName,  renderURL.toString() );

      // render URL, multivalue parameters

      {
         testName = "Render URL, multivalue parms";      
         renderURL = renderResponse.createRenderURL();

         String[] vals = {"A", "B", "C"};
         try { renderURL.setParameter("publicRenderParameter1", vals); }
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setParameter() failed.<br/>" + e.toString() + "<br/>");}

         String[] vals2 = {"D", "E", "F"};
         try { renderURL.setParameter("privateRenderParameter1", vals2); }
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setParameter() failed.<br/>" + e.toString() + "<br/>");}

         tw.writeURL(testName,  renderURL.toString() );
      }

      // render URL, multivalue parameters with null string

      {
         testName = "Render URL, multivalue with null";      
         renderURL = renderResponse.createRenderURL();

         String[] vals = {"G", "", "I"};
         try { renderURL.setParameter("publicRenderParameter1", vals); }
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setParameter() failed.<br/>" + e.toString() + "<br/>");}

         String[] vals2 = {"J", "K", null, "M", "", "O"};
         try { renderURL.setParameter("privateRenderParameter1", vals2); }
         catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setParameter() failed.<br/>" + e.toString() + "<br/>");}

         tw.writeURL(testName,  renderURL.toString() );
      }

      // Resource URL with no further processing -

      testName = "createResourceURL(), no add'l parameters";      
      ResourceURL resourceURL = renderResponse.createResourceURL();

      tw.writeURL(testName,  resourceURL.toString() );

      // Resource URL attempting to remove parameters using setParameter("parm", null) -

      testName = "createResourceURL(), remove parameters (null)";		
      resourceURL = renderResponse.createResourceURL();

      try { resourceURL.setParameter("publicRenderParameter1", (String) null); }
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove publicRenderParameter1 from URL failed.<br/>" + e.toString() + "<br/>");}
      try { resourceURL.setParameter("privateRenderParameter1", (String) null); }
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove privateRenderParameter1 from URL failed.<br/>" + e.toString() + "<br/>");}

      tw.writeURL(testName,  resourceURL.toString() );

      // Resource URL attempting to remove parameters  using setParameter("parm", "") -

      testName = "createResourceURL(), remove parameters (\"\")";      
      resourceURL = renderResponse.createResourceURL();

      try { resourceURL.setParameter("publicRenderParameter1", ""); }
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove publicRenderParameter1 from URL failed.<br/>" + e.toString() + "<br/>");}
      try { resourceURL.setParameter("privateRenderParameter1", ""); }
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"remove privateRenderParameter1 from URL failed.<br/>" + e.toString() + "<br/>");}

      tw.writeURL(testName,  resourceURL.toString() );

      // Resource URL setting parameters using various means -

      testName = "createResourceURL() setting parameters";		
      resourceURL = renderResponse.createResourceURL();

      resourceURL.setParameter("publicRenderParameter1", "30");
      resourceURL.setParameter("privateRenderParameter1", "35");
      resourceURL.setParameter("resourceURLParameter2", "40");

      String[] values2 = { "Barney", "Betty" };
      resourceURL.getParameterMap().put("resourceURLParameter3", values2);

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter, cacheability=FULL -

      testName = "createResourceURL(), set parm, cache=FULL";      
      resourceURL = renderResponse.createResourceURL();
      try {resourceURL.setCacheability(ResourceURL.FULL);}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
      resourceURL.setParameter("resourceURLParameter3", "55");

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter, cacheability=PORTLET -

      testName = "createResourceURL(), set parm, cache=PORTLET";      
      resourceURL = renderResponse.createResourceURL();
      try {resourceURL.setCacheability(ResourceURL.PORTLET);}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
      resourceURL.setParameter("resourceURLParameter3", "66");

      tw.writeURL(testName,  resourceURL.toString() );

      // Create resource URL, setting parameter, cacheability=PAGE -

      testName = "createResourceURL(), set parm, cache=PAGE";      
      resourceURL = renderResponse.createResourceURL();
      try {resourceURL.setCacheability(ResourceURL.PAGE);}
      catch(Exception e) {writer.write("In test: "+testName+":<br/>"+"setCacheability() failed.<br/>" + e.toString() + "<br/>");}
      resourceURL.setParameter("resourceURLParameter3", "77");

      tw.writeURL(testName,  resourceURL.toString() );

      tw.endTable();
   }

}
