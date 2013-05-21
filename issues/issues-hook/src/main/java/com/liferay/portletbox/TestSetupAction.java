/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.portletbox;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;


/**
 * @author  Neil Griffin
 */
public class TestSetupAction extends TestSetupCompatAction {

	private static final Log logger = LogFactory.getLog(TestSetupAction.class);

	@Override
	public void run(String[] companyIds) throws ActionException {

		try {

			for (String companyIdAsString : companyIds) {

				long companyId = Long.parseLong(companyIdAsString);
				setupPermissionChecker(companyId);

				Company company = CompanyLocalServiceUtil.getCompanyById(companyId);
				long userId = company.getDefaultUser().getUserId();
				setupSites(companyId, userId);
				clearPermissionChecker();
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	protected void addAllUsersToSite(long companyId, long groupId) throws Exception {

		List<User> users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		ArrayList<Long> userIdList = new ArrayList<Long>();

		for (User user : users) {

			if (!user.isDefaultUser()) {
				userIdList.add(user.getUserId());
			}
		}

		long[] userIds = new long[userIdList.size()];

		for (int i = 0; i < userIds.length; i++) {
			userIds[i] = userIdList.get(i);
		}

		UserLocalServiceUtil.addGroupUsers(groupId, userIds);
	}

	protected void setupPage(long userId, long groupId, PortalPage portalPage, boolean privateLayout) throws Exception {
		String portalPageName = portalPage.getName();
		String[] portletIds = portalPage.getPortletIds();
		Layout portalPageLayout = getPortalPageLayout(userId, groupId, portalPageName, privateLayout);
		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) portalPageLayout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, "2_columns_i", false);

		int columnNumber = 1;

		for (String portletId : portletIds) {

			if (portletId.endsWith("_INSTANCE_")) {
				portletId = portletId + "ABCD";
			}

			addPortlet(layoutTypePortlet, userId, columnNumber, portletId);
			columnNumber++;
		}

		LayoutLocalServiceUtil.updateLayout(portalPageLayout);

		logger.info("Setting up page name=[" + portalPageName + "]");
	}

	protected void setupPortletSpec3IssuesSite(long companyId, long userId) throws Exception {
		Group site = getSiteForSetup(companyId, userId, "PORTLETSPEC3 Issues");
		long groupId = site.getGroupId();
		addAllUsersToSite(companyId, groupId);

		for (PortalPage portalPage : TestPages.PORTLETSPEC3_ISSUE_PAGES) {
			setupPublicPage(userId, groupId, portalPage);
		}
	}

	protected void setupPrivatePage(long userId, long groupId, PortalPage portalPage) throws Exception {
		setupPage(userId, groupId, portalPage, true);
	}

	protected void setupPublicPage(long userId, long groupId, PortalPage portalPage) throws Exception {
		setupPage(userId, groupId, portalPage, false);
	}

	protected void setupSites(long companyId, long userId) throws Exception, DocumentException {
		setupPortletSpec3IssuesSite(companyId, userId);
	}

	protected Layout getPortalPageLayout(long userId, long groupId, String portalPageName, boolean privateLayout)
		throws Exception {
		Layout portalPageLayout = null;

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);

		for (Layout layout : layouts) {

			if (layout.getName(Locale.US).equals(portalPageName)) {
				portalPageLayout = layout;
			}
		}

		if (portalPageLayout == null) {
			long parentLayoutId = LayoutConstants.DEFAULT_PARENT_LAYOUT_ID;
			String type = LayoutConstants.TYPE_PORTLET;
			boolean hidden = false;
			String friendlyURL = "/" + portalPageName.toLowerCase();
			portalPageLayout = ServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId, portalPageName,
					portalPageName, portalPageName, type, hidden, friendlyURL);
		}

		return portalPageLayout;
	}

	protected Group getSiteForSetup(long companyId, long userId, String name) throws Exception {

		Group site = null;

		try {
			site = GroupLocalServiceUtil.getGroup(companyId, name);
		}
		catch (NoSuchGroupException e) {
			site = ServiceUtil.addActiveOpenGroup(userId, name);
		}

		logger.info("Setting up site name=[" + site.getName() + "] publicLayouts=[" + site.hasPublicLayouts() + "]");

		return site;
	}

}
