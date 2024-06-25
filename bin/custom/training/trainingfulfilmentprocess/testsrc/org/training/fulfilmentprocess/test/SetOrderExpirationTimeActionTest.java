/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.fulfilmentprocess.test;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.time.TimeService;
import org.training.fulfilmentprocess.actions.order.SetOrderExpirationTimeAction;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class SetOrderExpirationTimeActionTest
{
	@InjectMocks
	private final SetOrderExpirationTimeAction action = new SetOrderExpirationTimeAction();

	@Mock
	private ModelService modelService;
	@Mock
	private TimeService timeService;

	@Test
	public void shouldSetOrderExpirationTime()
	{
		final OrderProcessModel businessProcessModel = mock(OrderProcessModel.class);
		final OrderModel order = mock(OrderModel.class);
		final Date expireDate = mock(Date.class);
		given(businessProcessModel.getOrder()).willReturn(order);
		given(timeService.getCurrentTime()).willReturn(expireDate);

		action.executeAction(businessProcessModel);
		verify(order).setExpirationTime(expireDate);
		verify(modelService).save(order);
	}
}
