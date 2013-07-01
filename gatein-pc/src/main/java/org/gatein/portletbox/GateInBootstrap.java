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

package org.gatein.portletbox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Set;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.gatein.pc.embed.EmbedServlet;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;


/**
 * Implement the {@link ServletContainerInitializer} interface for embedding GateIn Portlet Container in a war file
 * containing portlets.
 *
 * @author  Julien Viet
 */
public class GateInBootstrap implements ServletContainerInitializer {

	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		ArrayList<String> portletNames = null;

		try {
			URL url = ctx.getResource("WEB-INF/portlet.xml");

			//
			if (url != null) {

				// XPath to get the portlet names
				portletNames = new ArrayList<String>();

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(url.openStream());
				XPath xpath = XPathFactory.newInstance().newXPath();
				XPathExpression expr = xpath.compile("//portlet-name/text()");
				NodeList portletNameNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

				for (int i = 0; i < portletNameNodes.getLength(); i++) {
					Text portletNameNode = (Text) portletNameNodes.item(i);
					portletNames.add(portletNameNode.getWholeText().trim());
				}
			}
			else {
				ctx.log("No portlets inside this web application");
			}

		}
		catch (Exception e) {
			ctx.log("Could not obtain a valid portlet.xml file", e);
		}

		//
		if (portletNames != null) {
			ctx.log("Detected portlet XML file with portlets " + portletNames +
				" -> Bootstrapping GateIn Portlet Container");

			//
			ServletRegistration.Dynamic embedRegistration = ctx.addServlet("EmbedServlet", EmbedServlet.class);
			embedRegistration.addMapping("/embed/*");

			//
			ServletRegistration.Dynamic redirectRegistration = ctx.addServlet("RedirectServlet",
					new RedirectServlet(portletNames));
			redirectRegistration.addMapping("/");
		}
	}
}
