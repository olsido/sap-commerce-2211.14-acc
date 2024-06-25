/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.fulfilmentprocess.actions.consignment;

import de.hybris.platform.ordersplitting.model.ConsignmentProcessModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.processengine.action.AbstractProceduralAction;
import org.training.fulfilmentprocess.constants.TrainingFulfilmentProcessConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 *
 */
public class SubprocessEndAction extends AbstractProceduralAction<ConsignmentProcessModel>
{
	private static final Logger LOG = Logger.getLogger(SubprocessEndAction.class);

	private static final String PROCESS_MSG = "Process: ";

	private static final String RANDOM_ALGORITHM = "SHA1PRNG";

	private static final int THREAD_SLEEP_TIME = 2000;

	private BusinessProcessService businessProcessService;

	protected BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	@Required
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	@Override
	public void executeAction(final ConsignmentProcessModel process)
	{
		LOG.info(PROCESS_MSG + process.getCode() + " in step " + getClass());

		try
		{
			// simulate different ending times
			SecureRandom sRnd = SecureRandom.getInstance(RANDOM_ALGORITHM);
			Thread.sleep((long) (sRnd.nextDouble() * THREAD_SLEEP_TIME));
		}
		catch (final InterruptedException | NoSuchAlgorithmException e)
		{
			LOG.error(e.getMessage(), e);
			Thread.currentThread().interrupt();
		}
		
		process.setDone(true);

		save(process);
		LOG.info(PROCESS_MSG + process.getCode() + " wrote DONE marker");

		getBusinessProcessService().triggerEvent(
				process.getParentProcess().getCode() + "_"
						+ TrainingFulfilmentProcessConstants.CONSIGNMENT_SUBPROCESS_END_EVENT_NAME);
		LOG.info(PROCESS_MSG + process.getCode() + " fired event "
				+ TrainingFulfilmentProcessConstants.CONSIGNMENT_SUBPROCESS_END_EVENT_NAME);
	}
}
