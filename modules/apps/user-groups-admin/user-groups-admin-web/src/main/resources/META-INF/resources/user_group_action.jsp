<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/init.jsp" %>

<%
UserGroupSearch searchContainer = (UserGroupSearch)request.getAttribute("liferay-ui:search:searchContainer");

String redirect = searchContainer.getIteratorURL().toString();

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

UserGroup userGroup = (UserGroup)row.getObject();
%>

<liferay-ui:icon-menu direction="left-side" icon="<%= StringPool.BLANK %>" markupView="lexicon" message="<%= StringPool.BLANK %>" showWhenSingleIcon="<%= true %>">
	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.UPDATE) && UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.VIEW) %>">
		<portlet:renderURL var="editURL">
			<portlet:param name="mvcPath" value="/edit_user_group.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-edit"
			message="edit"
			url="<%= editURL %>"
		/>
	</c:if>

	<%
	boolean hasPermissionsPermission = UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.PERMISSIONS);
	%>

	<c:if test="<%= hasPermissionsPermission %>">
		<liferay-security:permissionsURL
			modelResource="<%= UserGroup.class.getName() %>"
			modelResourceDescription="<%= userGroup.getName() %>"
			resourcePrimKey="<%= String.valueOf(userGroup.getUserGroupId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	Group userGroupGroup = userGroup.getGroup();

	hasPermissionsPermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.PERMISSIONS);
	%>

	<c:if test="<%= hasPermissionsPermission %>">
		<liferay-security:permissionsURL
			modelResource="<%= Group.class.getName() %>"
			modelResourceDescription='<%= LanguageUtil.format(request, "site-for-user-group-x", userGroup.getName(), false) %>'
			resourcePrimKey="<%= String.valueOf(userGroup.getGroup().getGroupId()) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			iconCssClass="icon-lock"
			message="site-permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.MANAGE_LAYOUTS) %>">

		<%
		PortletURL managePagesURL = PortletProviderUtil.getPortletURL(request, userGroupGroup, Layout.class.getName(), PortletProvider.Action.EDIT);

		managePagesURL.setParameter("redirect", redirect);
		managePagesURL.setWindowState(LiferayWindowState.POP_UP);
		%>

		<liferay-ui:icon
			iconCssClass="icon-copy"
			message="manage-site-pages"
			url="<%= managePagesURL.toString() %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<%
	boolean hasViewPermission = GroupPermissionUtil.contains(permissionChecker, userGroupGroup, ActionKeys.VIEW);
	%>

	<c:if test="<%= hasViewPermission && (userGroupGroup.getPublicLayoutsPageCount() > 0) %>">
		<liferay-ui:icon
			iconCssClass="icon-search"
			message="go-to-the-site's-public-pages"
			target="_blank"
			url="<%= userGroupGroup.getDisplayURL(themeDisplay, false) %>"
		/>
	</c:if>

	<c:if test="<%= hasViewPermission && (userGroupGroup.getPrivateLayoutsPageCount() > 0) %>">
		<liferay-ui:icon
			iconCssClass="icon-search"
			message="go-to-the-site's-private-pages"
			target="_blank"
			url="<%= userGroupGroup.getDisplayURL(themeDisplay, true) %>"
		/>
	</c:if>

	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.ASSIGN_MEMBERS) %>">
		<portlet:renderURL var="assignURL">
			<portlet:param name="mvcPath" value="/edit_user_group_assignments.jsp" />
			<portlet:param name="redirect" value="<%= redirect %>" />
			<portlet:param name="userGroupId" value="<%= String.valueOf(userGroup.getUserGroupId()) %>" />
		</portlet:renderURL>

		<liferay-ui:icon
			iconCssClass="icon-signin"
			message="assign-members"
			url="<%= assignURL %>"
		/>
	</c:if>

	<c:if test="<%= UserGroupPermissionUtil.contains(permissionChecker, userGroup.getUserGroupId(), ActionKeys.DELETE) %>">

		<%
		String taglibDeleteURL = "javascript:" + renderResponse.getNamespace() + "deleteUserGroup('" + userGroup.getUserGroupId() + "');";
		%>

		<liferay-ui:icon
			cssClass="item-remove"
			iconCssClass="icon-remove"
			message="delete"
			url="<%= taglibDeleteURL %>"
		/>
	</c:if>
</liferay-ui:icon-menu>