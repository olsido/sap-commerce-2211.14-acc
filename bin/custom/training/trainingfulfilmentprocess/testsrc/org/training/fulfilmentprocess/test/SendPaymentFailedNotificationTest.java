/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.fulfilmentprocess.test;

import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.orderprocessing.events.PaymentFailedEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import org.training.fulfilmentprocess.actions.order.SendPaymentFailedNotificationAction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class SendPaymentFailedNotificationTest
{
	@InjectMocks
	private final SendPaymentFailedNotificationAction sendPaymentFailedNotification = new SendPaymentFailedNotificationAction();

	@Mock
	private EventService eventService;

	/**
	 * Test method for
	 * {@link org.training.fulfilmentprocess.actions.order.SendPaymentFailedNotificationAction#executeAction(OrderProcessModel)}
	 * .
	 */
	@Test
	public void testExecuteActionOrderProcessModel()
	{
		final OrderProcessModel process = new OrderProcessModel();
		sendPaymentFailedNotification.executeAction(process);

		final ArgumentMatcher<PaymentFailedEvent> matcher = event -> event.getProcess().equals(process);
		Mockito.verify(eventService).publishEvent(Mockito.argThat(matcher));

	}
}
