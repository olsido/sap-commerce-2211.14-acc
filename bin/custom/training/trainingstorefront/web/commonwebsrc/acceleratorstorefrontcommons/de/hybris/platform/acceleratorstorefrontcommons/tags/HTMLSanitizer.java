/*
 * Copyright (c) 2019 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.acceleratorstorefrontcommons.tags;

import de.hybris.platform.acceleratorservices.util.HtmlSanitizerPolicyProvider;

import java.util.Arrays;

import org.owasp.html.FilterUrlByProtocolAttributePolicy;


/**
 * This file contains static methods that are used by JSP EL.
 */
public class HTMLSanitizer
{
	protected static final FilterUrlByProtocolAttributePolicy URL_POLICY = new FilterUrlByProtocolAttributePolicy(
			Arrays.asList("http", "https", "mailto"));

	/**
	 * JSP EL Function to sanitize unsafe HTML string
	 *
	 * @param untrustedHTML
	 *           potentially unsafe HTML string
	 * @return safe HTML string with allowed elements only. All other elements that are not specified as allowed are
	 *         removed.
	 */
	public static String sanitizeHTML(final String untrustedHTML)
	{
		return HtmlSanitizerPolicyProvider.defaultPolicy().sanitize(untrustedHTML);
	}

	/**
	 * Validate input URL scheme against declared URL Policy
	 *
	 * @param the
	 *           dirtyUrl that needs to be validated
	 * @return whether the URL is valid or not
	 */
	public static boolean validateUrlScheme(final String dirtyUrl)
	{
		return URL_POLICY.apply(null, null, dirtyUrl) != null;
	}
}
