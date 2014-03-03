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
package com.liferay.portal.util;

import java.io.IOException;
import java.util.Collection;

import javax.portlet.PortletRequest;
import javax.portlet.filter.PortletRequestWrapper;
import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * This class isolates methods that were added to {@link HttpServletRequest} in the Servlet 3.0 API.
 *
 * @author  Neil Griffin
 */
public abstract class HttpServletRequest_3_0_Adapter extends PortletRequestWrapper implements HttpServletRequest {
	public HttpServletRequest_3_0_Adapter(PortletRequest portletRequest) {
		super(portletRequest);
	}

	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		throw new UnsupportedOperationException();
	}

	public void login(String username, String password) throws ServletException {
		throw new UnsupportedOperationException();
	}

	public void logout() throws ServletException {
		throw new UnsupportedOperationException();
	}

	public AsyncContext startAsync() throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
		throws IllegalStateException {
		throw new UnsupportedOperationException();
	}

	public AsyncContext getAsyncContext() {
		throw new UnsupportedOperationException();
	}

	public boolean isAsyncStarted() {
		throw new UnsupportedOperationException();
	}

	public boolean isAsyncSupported() {
		throw new UnsupportedOperationException();
	}

	public DispatcherType getDispatcherType() {
		throw new UnsupportedOperationException();
	}

	public Part getPart(String name) throws IOException, ServletException {
		throw new UnsupportedOperationException();
	}

	public Collection<Part> getParts() throws IOException, ServletException {
		throw new UnsupportedOperationException();
	}

	public ServletContext getServletContext() {
		throw new UnsupportedOperationException();
	}
}
