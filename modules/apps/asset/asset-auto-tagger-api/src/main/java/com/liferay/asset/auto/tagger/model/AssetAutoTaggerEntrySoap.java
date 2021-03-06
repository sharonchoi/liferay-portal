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

package com.liferay.asset.auto.tagger.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetAutoTaggerEntrySoap implements Serializable {
	public static AssetAutoTaggerEntrySoap toSoapModel(
		AssetAutoTaggerEntry model) {
		AssetAutoTaggerEntrySoap soapModel = new AssetAutoTaggerEntrySoap();

		soapModel.setAssetAutoTaggerEntryId(model.getAssetAutoTaggerEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setAssetTagId(model.getAssetTagId());

		return soapModel;
	}

	public static AssetAutoTaggerEntrySoap[] toSoapModels(
		AssetAutoTaggerEntry[] models) {
		AssetAutoTaggerEntrySoap[] soapModels = new AssetAutoTaggerEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetAutoTaggerEntrySoap[][] toSoapModels(
		AssetAutoTaggerEntry[][] models) {
		AssetAutoTaggerEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetAutoTaggerEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetAutoTaggerEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetAutoTaggerEntrySoap[] toSoapModels(
		List<AssetAutoTaggerEntry> models) {
		List<AssetAutoTaggerEntrySoap> soapModels = new ArrayList<AssetAutoTaggerEntrySoap>(models.size());

		for (AssetAutoTaggerEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetAutoTaggerEntrySoap[soapModels.size()]);
	}

	public AssetAutoTaggerEntrySoap() {
	}

	public long getPrimaryKey() {
		return _assetAutoTaggerEntryId;
	}

	public void setPrimaryKey(long pk) {
		setAssetAutoTaggerEntryId(pk);
	}

	public long getAssetAutoTaggerEntryId() {
		return _assetAutoTaggerEntryId;
	}

	public void setAssetAutoTaggerEntryId(long assetAutoTaggerEntryId) {
		_assetAutoTaggerEntryId = assetAutoTaggerEntryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public long getAssetTagId() {
		return _assetTagId;
	}

	public void setAssetTagId(long assetTagId) {
		_assetTagId = assetTagId;
	}

	private long _assetAutoTaggerEntryId;
	private long _groupId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _assetEntryId;
	private long _assetTagId;
}