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

import javax.inject.Inject;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.cdi.portlet.bridge.CDIBeanManagerUtil;

/**
 * @author Neil Griffin
 */
public class CDIPortlet extends GenericPortlet {

	@Inject
	MyInjectedBean myInjectedBean;

	protected void include(String path, PortletRequest portletRequest,
			PortletResponse portletResponse) throws IOException,
			PortletException {

		PortletContext portletContext = getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher = portletContext
				.getRequestDispatcher(path);

		if (portletRequestDispatcher == null) {
			System.err.println("Invalid path: " + path);
		} else {
			portletRequestDispatcher.include(portletRequest, portletResponse);
		}
	}

	@Override
	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) throws PortletException, IOException {
		System.out.println("*** INFO: processAction - BEGIN");
		
		// Since we can't use @Inject into a portlet yet, ask CDI to create
		// an insteance of MyRequestScopedBean for us. 
		MyRequestScopedBean myRequestScopedBean = (MyRequestScopedBean) CDIBeanManagerUtil.getManagedBeanReference(MyRequestScopedBean.class);
		
		String currentTimeFromActionRequest = myRequestScopedBean.getCurrentTime();
		actionResponse.setRenderParameter("currentTimeFromActionRequest", currentTimeFromActionRequest);
		
		System.out.println("*** INFO: processAction - END");
	}

	@Override
	protected void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws PortletException, IOException {

		System.out.println("*** INFO: doView - BEGIN");

		if (myInjectedBean == null) {
			renderRequest
					.setAttribute(
							"myInjectedBeanInfo",
							"Injection of myInjectedBean failed in CDIPortlet. "
									+ "This means that the portlet container did ask the CDI "
									+ "implementation to create the instance of CDIPortlet.");
		} else {
			renderRequest.setAttribute("myInjectedBeanInfo",
					"Injection of myInjectedBean was successful in CDIPortlet");
		}

		String path = "/views/portletViewMode.jsp";
		System.out.println("*** INFO: doView - start including JSP");
		include(path, renderRequest, renderResponse);
		System.out.println("*** INFO: doView - finish including JSP");

		System.out.println("*** INFO: doView - END");
	}
}
