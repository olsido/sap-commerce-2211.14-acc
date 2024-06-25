/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.fulfilmentprocess.test;


import de.hybris.bootstrap.annotations.UnitTest;
import de.hybris.platform.orderprocessing.events.AuthorizationFailedEvent;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import org.training.fulfilmentprocess.actions.order.SendAuthorizationFailedNotificationAction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;


@UnitTest
@RunWith(MockitoJUnitRunner.class)
public class SendAuthorizationFailedNotificationTest
{
	@InjectMocks
	private final SendAuthorizationFailedNotificationAction sendAuthorizationFailedNotification = new SendAuthorizationFailedNotificationAction();

	@Mock
	private EventService eventService;

	/**
	 * Test method for
	 * {@link org.training.fulfilmentprocess.actions.order.SendOrderPlacedNotificationAction#executeAction(OrderProcessModel)}
	 * .
	 */
	@Test
	public void testExecuteActionOrderProcessModel()
	{
		final OrderProcessModel process = new OrderProcessModel();
		sendAuthorizationFailedNotification.executeAction(process);

		final ArgumentMatcher<AuthorizationFailedEvent> matcher = event -> event.getProcess().equals(process);

		Mockito.verify(eventService).publishEvent(Mockito.argThat(matcher));
	}
}
