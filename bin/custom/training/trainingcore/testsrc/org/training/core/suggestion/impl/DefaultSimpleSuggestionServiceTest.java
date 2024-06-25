/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.core.suggestion.impl;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.catalog.enums.ProductReferenceTypeEnum;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import org.training.core.suggestion.dao.impl.DefaultSimpleSuggestionDao;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import junit.framework.Assert;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


/**
 * JUnit test suite for {@link DefaultSimpleSuggestionServiceTest}
 */
@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class DefaultSimpleSuggestionServiceTest
{
	@Mock
	private DefaultSimpleSuggestionDao simpleSuggestionDao;
	private DefaultSimpleSuggestionService defaultSimpleSuggestionService;

	@Before
	public void setUp() throws Exception
	{
		defaultSimpleSuggestionService = new DefaultSimpleSuggestionService();
		defaultSimpleSuggestionService.setSimpleSuggestionDao(simpleSuggestionDao);
	}

	@Test
	public void testGetReferencedProductsForBoughtCategory()
	{
		final UserModel user = mock(UserModel.class);
		final CategoryModel category = mock(CategoryModel.class);

		final Integer limit = NumberUtils.INTEGER_ONE;
		final boolean excludePurchased = true;
		final List<ProductModel> result = Collections.emptyList();
		final ProductReferenceTypeEnum type = ProductReferenceTypeEnum.FOLLOWUP;
		given(simpleSuggestionDao.findProductsRelatedToPurchasedProductsByCategory(category, user, type, excludePurchased, limit))
				.willReturn(result);

		final List<ProductModel> actual = defaultSimpleSuggestionService.getReferencesForPurchasedInCategory(category, user, type,
				excludePurchased, limit);
		Assert.assertEquals(result, actual);
	}
}
