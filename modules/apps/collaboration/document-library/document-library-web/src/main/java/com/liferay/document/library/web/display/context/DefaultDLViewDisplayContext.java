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

package com.liferay.document.library.web.display.context;

import com.liferay.document.library.display.context.DLViewDisplayContext;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.web.display.context.util.DLRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public class DefaultDLViewDisplayContext implements DLViewDisplayContext {

	public DefaultDLViewDisplayContext(
			HttpServletRequest request, HttpServletResponse response,
			Folder folder)
		throws PortalException {

		_folder = folder;

		_dlRequestHelper = new DLRequestHelper(request);
	}

	@Override
	public Map<String, String> getOrderColumns() {
		Map<String, String> orderColumns = new HashMap();

		orderColumns.put("creationDate", "create-date");
		orderColumns.put("downloads", "downloads");
		orderColumns.put("modifiedDate", "modified-date");
		orderColumns.put("size", "size");
		orderColumns.put("title", "title");

		return orderColumns;
	}

	@Override
	public PortletURL getSortURL() {
		LiferayPortletResponse liferayPortletResponse =
			_dlRequestHelper.getLiferayPortletResponse();

		PortletURL sortURL = liferayPortletResponse.createRenderURL();

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (_folder != null) {
			folderId = _folder.getFolderId();
		}

		String mvcRenderCommandName = "/document_library/view";

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			mvcRenderCommandName = "/document_library/view_folder";
		}

		String navigation = ParamUtil.getString(
			_dlRequestHelper.getRequest(), "navigation", "home");
		long fileEntryTypeId = ParamUtil.getLong(
			_dlRequestHelper.getRequest(), "fileEntryTypeId", -1);

		sortURL.setParameter("mvcRenderCommandName", mvcRenderCommandName);
		sortURL.setParameter("navigation", navigation);
		sortURL.setParameter("folderId", String.valueOf(folderId));
		sortURL.setParameter(
			"fileEntryTypeId", String.valueOf(fileEntryTypeId));

		return sortURL;
	}

	@Override
	public UUID getUuid() {
		return _UUID;
	}

	private static final UUID _UUID = UUID.fromString(
		"9A2EC49C-4E47-4C8d-B49A-4C428D19E662");

	private final DLRequestHelper _dlRequestHelper;
	private final Folder _folder;

}