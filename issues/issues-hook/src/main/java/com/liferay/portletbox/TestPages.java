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

import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to isolate source code differences between different versions of Liferay Portal.
 *
 * @author  Neil Griffin
 */
public class TestPages {

	public static final List<PortalPage> PORTLETSPEC3_ISSUE_PAGES;

	static {
		PORTLETSPEC3_ISSUE_PAGES = new ArrayList<PortalPage>();
		PORTLETSPEC3_ISSUE_PAGES.add(new PortalPage("PORTLETSPEC3-5", "1_WAR_PORTLETSPEC35portlet"));
		PORTLETSPEC3_ISSUE_PAGES.add(new PortalPage("PORTLETSPEC3-7", "1_WAR_PORTLETSPEC37portlet"));
		PORTLETSPEC3_ISSUE_PAGES.add(new PortalPage("PORTLETSPEC3-8", "1_WAR_PORTLETSPEC38portlet"));
		PORTLETSPEC3_ISSUE_PAGES.add(new PortalPage("PORTLETSPEC3-9", "1_WAR_PORTLETSPEC39portlet"));
		PORTLETSPEC3_ISSUE_PAGES.add(new PortalPage("PORTLETSPEC3-10", "1_WAR_PORTLETSPEC310portlet"));
		PORTLETSPEC3_ISSUE_PAGES.add(new PortalPage("PORTLETSPEC3-14", "portletnamefromdescriptor_WAR_PORTLETSPEC314portlet"));
	}
}
