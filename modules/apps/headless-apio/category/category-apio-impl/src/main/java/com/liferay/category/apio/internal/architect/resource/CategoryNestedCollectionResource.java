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

package com.liferay.category.apio.internal.architect.resource;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.representor.Representor;
import com.liferay.apio.architect.resource.NestedCollectionResource;
import com.liferay.apio.architect.routes.ItemRoutes;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryService;
import com.liferay.asset.kernel.service.AssetVocabularyService;
import com.liferay.category.apio.identifier.architect.CategoryIdentifier;
import com.liferay.category.apio.internal.architect.form.AssetCategoryForm;
import com.liferay.person.apio.architect.identifier.PersonIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.taxonomy.apio.identifier.architect.TaxonomyIdentifier;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(immediate = true)
public class CategoryNestedCollectionResource
	implements NestedCollectionResource<AssetCategory, Long, CategoryIdentifier,
		Long, TaxonomyIdentifier> {

	@Override
	public NestedCollectionRoutes<AssetCategory, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<AssetCategory, Long> builder) {

		return builder.addGetter(
			this::_getPageItems
		).addCreator(
			this::_addAssetCategory,
			(credentials, assetCategoryId) -> true, AssetCategoryForm::buildForm
		).build();
	}

	@Override
	public String getName() {
		return "categories";
	}

	@Override
	public ItemRoutes<AssetCategory, Long> itemRoutes(
		ItemRoutes.Builder<AssetCategory, Long> builder) {

		return builder.addGetter(
			this::_getAssetCategory
		).addUpdater(
			this::_updateAssetCategory,
			(credentials, assetVocabularyId) -> true,
			AssetCategoryForm::buildForm
		).addRemover(
			this::_removeAssetCategory, (credentials, assetVocabularyId) -> true
		).build();
	}

	@Override
	public Representor<AssetCategory> representor(
		Representor.Builder<AssetCategory, Long> builder) {

		return builder.types(
			"Category"
		).identifier(
			AssetCategory::getCategoryId
		).addBidirectionalModel(
			"category", "categories", CategoryIdentifier.class,
			AssetCategory::getParentCategoryId
		).addBidirectionalModel(
			"taxonomy", "categories", TaxonomyIdentifier.class,
			AssetCategory::getVocabularyId
		).addDate(
			"dateCreated", AssetCategory::getCreateDate
		).addDate(
			"dateModified", AssetCategory::getModifiedDate
		).addDate(
			"datePublished", AssetCategory::getLastPublishDate
		).addLinkedModel(
			"author", PersonIdentifier.class, AssetCategory::getUserId
		).addLinkedModel(
			"creator", PersonIdentifier.class, AssetCategory::getUserId
		).addLocalizedStringByLocale(
			"description", AssetCategory::getDescription
		).addLocalizedStringByLocale(
			"title", AssetCategory::getTitle
		).addString(
			"name", AssetCategory::getName
		).build();
	}

	private AssetCategory _addAssetCategory(
			Long vocabularyId, AssetCategoryForm assetCategoryForm)
		throws PortalException {

		AssetVocabulary vocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		ServiceContext serviceContext = new ServiceContext();

		return _assetCategoryService.addCategory(
			vocabulary.getGroupId(), 0, assetCategoryForm.getTitleMap(),
			assetCategoryForm.getDescriptionMap(), vocabularyId, null,
			serviceContext);
	}

	private AssetCategory _getAssetCategory(Long assetCategoryId)
		throws PortalException {

		return _assetCategoryService.getCategory(assetCategoryId);
	}

	private PageItems<AssetCategory> _getPageItems(
			Pagination pagination, Long vocabularyId)
		throws PortalException {

		AssetVocabulary vocabulary = _assetVocabularyService.getVocabulary(
			vocabularyId);

		List<AssetCategory> assetCategories =
			_assetCategoryService.getVocabularyRootCategories(
				vocabulary.getGroupId(), vocabularyId,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);
		int count = _assetCategoryService.getVocabularyRootCategoriesCount(
			vocabulary.getGroupId(), vocabularyId);

		return new PageItems<>(assetCategories, count);
	}

	private void _removeAssetCategory(Long assetCategoryId)
		throws PortalException {

		_assetCategoryService.deleteCategory(assetCategoryId);
	}

	private AssetCategory _updateAssetCategory(
		Long categoryId, AssetCategoryForm assetCategoryForm) {

		ServiceContext serviceContext = new ServiceContext();

		return Try.fromFallible(
			() -> _assetCategoryService.updateCategory(
				categoryId, 0L, assetCategoryForm.getTitleMap(),
				assetCategoryForm.getDescriptionMap(),
				assetCategoryForm.getVocabularyId(), null, serviceContext)
		).getUnchecked();
	}

	@Reference
	private AssetCategoryService _assetCategoryService;

	@Reference
	private AssetVocabularyService _assetVocabularyService;

	@Reference
	private HasPermission _hasPermission;

}