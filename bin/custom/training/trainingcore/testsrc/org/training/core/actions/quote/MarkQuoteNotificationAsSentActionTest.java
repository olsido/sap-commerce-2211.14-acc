/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.core.actions.quote;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.commerceservices.enums.QuoteNotificationType;
import de.hybris.platform.commerceservices.model.process.QuoteProcessModel;
import de.hybris.platform.core.model.order.QuoteModel;
import de.hybris.platform.order.QuoteService;
import de.hybris.platform.servicelayer.model.ModelService;

import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;


@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class MarkQuoteNotificationAsSentActionTest
{
	@Mock
	protected ModelService modelService;
	@Mock
	private QuoteModel quoteModel;
	@Mock
	private QuoteProcessModel quoteProcessModel;
	@Mock
	private QuoteService quoteService;
	@Mock
	private QuoteNotificationType quoteNotificationType;

	@Spy
	@InjectMocks
	private MarkQuoteNotificationAsSentAction action;

	@Before
	public void setup()
	{
		action.setQuoteNotificationType(QuoteNotificationType.EXPIRING_SOON);
	}

	@Test
	public void testExecuteAction() throws Exception
	{
		final String quoteCode = "quoteCode";

		doReturn(quoteCode).when(quoteProcessModel).getQuoteCode();
		doReturn(quoteModel).when(quoteService).getCurrentQuoteForCode(quoteCode);
		doReturn(SetUtils.EMPTY_SET).when(quoteModel).getGeneratedNotifications();

		action.executeAction(quoteProcessModel);

		verify(quoteModel).setGeneratedNotifications((Set<QuoteNotificationType>) argThat(Matchers.contains(QuoteNotificationType.EXPIRING_SOON)));
	}
}
