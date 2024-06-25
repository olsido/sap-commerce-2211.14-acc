/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.fulfilmentprocess.test;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.core.enums.OrderStatus;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.orderprocessing.events.OrderFraudCustomerNotificationEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.training.fulfilmentprocess.actions.order.NotifyCustomerAboutFraudAction;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class SendOrderFraudCustomerNotificationEventTest
{
	@InjectMocks
	private final NotifyCustomerAboutFraudAction action = new NotifyCustomerAboutFraudAction();

	@Mock
	private EventService eventService;
	@Mock
	private ModelService modelService;

	@Test
	public void testExecuteAction()
	{
		final OrderProcessModel process = new OrderProcessModel();
		final OrderModel order = new OrderModel();
		process.setOrder(order);
		action.executeAction(process);

		final ArgumentMatcher<OrderFraudCustomerNotificationEvent> matcher = event -> event.getProcess().equals(process);

		Mockito.verify(eventService).publishEvent(Mockito.argThat(matcher));

		Mockito.verify(modelService).save(order);
		Assert.assertEquals(OrderStatus.SUSPENDED, order.getStatus());
	}
}
