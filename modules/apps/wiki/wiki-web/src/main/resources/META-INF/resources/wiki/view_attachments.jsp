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

<%@ include file="/wiki/init.jsp" %>

<%
WikiPage wikiPage = (WikiPage)request.getAttribute(WikiWebKeys.WIKI_PAGE);

boolean copyPageAttachments = ParamUtil.getBoolean(request, "copyPageAttachments", true);

List<FileEntry> attachmentsFileEntries = null;

if (wikiPage != null) {
	attachmentsFileEntries = wikiPage.getAttachmentsFileEntries();
}

long templateNodeId = ParamUtil.getLong(request, "templateNodeId");
String templateTitle = ParamUtil.getString(request, "templateTitle");

WikiPage templatePage = null;

if ((templateNodeId > 0) && Validator.isNotNull(templateTitle)) {
	try {
		templatePage = WikiPageServiceUtil.getPage(templateNodeId, templateTitle);

		attachmentsFileEntries = templatePage.getAttachmentsFileEntries();
	}
	catch (Exception e) {
	}
}
%>

<c:if test="<%= (attachmentsFileEntries != null) %>">
	<c:if test="<%= (templatePage != null) && !attachmentsFileEntries.isEmpty() %>">
		<aui:input name="copyPageAttachments" type="checkbox" value="<%= copyPageAttachments %>" />
	</c:if>

	<liferay-ui:search-container
		id="pageAttachments"
		iteratorURL="<%= currentURLObj %>"
		total="<%= attachmentsFileEntries.size() %>"
	>

		<liferay-ui:search-container-results
			results="<%= attachmentsFileEntries %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.repository.model.FileEntry"
			escapedModel="<%= true %>"
			keyProperty="fileEntryId"
			modelVar="fileEntry"
			rowVar="row"
		>

			<%
			int status = WorkflowConstants.STATUS_APPROVED;

			String rowURL = PortletFileRepositoryUtil.getDownloadPortletFileEntryURL(themeDisplay, fileEntry, "status=" + status);
			%>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="file-name"
				value="<%= fileEntry.getTitle()%>"
			/>

			<liferay-ui:search-container-column-text
				href="<%= rowURL %>"
				name="size"
				value="<%= TextFormatter.formatStorageSize(fileEntry.getSize(), locale) %>"
			/>

			<liferay-ui:search-container-column-jsp
				cssClass="entry-action"
				path="/wiki/page_attachment_action.jsp"
			/>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator markupView="lexicon" paginate="<%= false %>" />
	</liferay-ui:search-container>
</c:if>