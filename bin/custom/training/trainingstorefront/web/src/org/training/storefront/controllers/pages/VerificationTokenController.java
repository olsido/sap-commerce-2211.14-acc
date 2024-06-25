/*
 * Copyright (c) 2024 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.storefront.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractPageController;
import de.hybris.platform.acceleratorstorefrontcommons.forms.LoginForm;
import de.hybris.platform.commercefacades.verificationtoken.VerificationTokenFacade;
import de.hybris.platform.commercefacades.verificationtoken.data.CreateVerificationTokenInputData;
import de.hybris.platform.core.enums.SAPUserVerificationPurpose;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Verification Token Controller.
 */
@Controller
@RequestMapping(value = "/verificationToken")
public class VerificationTokenController extends AbstractPageController
{

	@Resource(name = "verificationTokenFacade")
	public VerificationTokenFacade verificationTokenFacade;

	@ResponseBody
	@PostMapping
	public String createVerificationToken(@RequestParam String username, @RequestParam String password, final Model model, @Valid
	final LoginForm form, final BindingResult bindingResult)
	{
		final CreateVerificationTokenInputData inputData = new CreateVerificationTokenInputData();
		inputData.setLoginId(username);
		inputData.setPassword(password);
		inputData.setPurpose(SAPUserVerificationPurpose.LOGIN.getCode());
		// create otp verification token
		return verificationTokenFacade.createVerificationToken(inputData);
	}
}
