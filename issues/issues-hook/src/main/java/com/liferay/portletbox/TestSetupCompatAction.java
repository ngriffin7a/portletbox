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

import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.PermissionChecker;


/**
 * This class provides a compatibility layer that isolates differences between different versions of Liferay Portal. The
 * purpose of this class is to minimize source code differences between different branches.
 *
 * @author  Neil Griffin
 */
public abstract class TestSetupCompatAction extends SimpleAction {

	protected void addPortlet(LayoutTypePortlet layoutTypePortlet, long userId, int columnNumber, String portletId) {
		layoutTypePortlet.setPortletIds("column-" + columnNumber, portletId);
	}

	/**
	 * This method clears the {@link PermissionChecker} that was setup via the {@link #setupPermissionChecker(long)}
	 * method.
	 */
	protected void clearPermissionChecker() {
		// This is a no-op for Liferay Portal 6.1
	}

	/**
	 * This method sets up the {@link PermissionChecker} {@link ThreadLocal} prior to performing additional test setup.
	 */
	protected void setupPermissionChecker(long companyId) {
		// This is a no-op for Liferay Portal 6.1
	}
}
