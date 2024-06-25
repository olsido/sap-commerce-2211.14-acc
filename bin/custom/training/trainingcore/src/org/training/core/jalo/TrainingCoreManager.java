/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.core.jalo;

import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.extension.ExtensionManager;
import org.training.core.constants.TrainingCoreConstants;
import org.training.core.setup.CoreSystemSetup;


/**
 * Do not use, please use {@link CoreSystemSetup} instead.
 * 
 */
public class TrainingCoreManager extends GeneratedTrainingCoreManager
{
	public static final TrainingCoreManager getInstance()
	{
		final ExtensionManager em = JaloSession.getCurrentSession().getExtensionManager();
		return (TrainingCoreManager) em.getExtension(TrainingCoreConstants.EXTENSIONNAME);
	}
}
