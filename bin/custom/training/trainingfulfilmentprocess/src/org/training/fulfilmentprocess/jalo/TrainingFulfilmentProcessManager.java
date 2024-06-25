/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.fulfilmentprocess.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.training.fulfilmentprocess.constants.TrainingFulfilmentProcessConstants;

public class TrainingFulfilmentProcessManager extends GeneratedTrainingFulfilmentProcessManager
{
	public static final TrainingFulfilmentProcessManager getInstance()
	{
		ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TrainingFulfilmentProcessManager) em.getExtension(TrainingFulfilmentProcessConstants.EXTENSIONNAME);
	}
	
}
