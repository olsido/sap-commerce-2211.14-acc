/*
 * Copyright (c) 2024 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.acceleratorstorefrontcommons.forms;

import de.hybris.platform.acceleratorstorefrontcommons.constants.WebConstants;
import de.hybris.platform.util.Config;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


public class LoginForm
{
	private static final Boolean OTP_CUSTOMER_LOGIN_ENABLED = Config.getBoolean(WebConstants.OTP_CUSTOMER_LOGIN_ENABLED, false);
	@NotNull(message = "{general.required}")
	@Email
	private String otpUserName;
	@NotNull(message = "{general.required}")
	private String otpPassword;
	@NotNull(message = "{general.required}")
	private String j_username; 
	@NotNull(message = "{general.required}")
	private String j_password;
	@NotNull(message = "{general.required}")
	private String lastOtpUserName;

	/**
	 * @return the j_username
	 */
	public String getJ_username() 
	{
		return j_username;
	}

	/**
	 * @param j_username
	 *           the j_username to set
	 */
	public void setJ_username(final String j_username) 
	{
		this.j_username = j_username;
	}

	/**
	 * @return the j_password
	 */
	public String getJ_password() 
	{
		return j_password;
	}

	/**
	 * @param j_password
	 *           the j_password to set
	 */
	public void setJ_password(final String j_password) 
	{
		this.j_password = j_password;
	}


	public String getOtpPassword()
	{
		return otpPassword;
	}

	public void setOtpPassword(final String otpPassword)
	{
		this.otpPassword = otpPassword;
	}

	public String getOtpUserName()
	{
		return otpUserName;
	}

	public void setOtpUserName(final String otpUserName)
	{
		this.otpUserName = otpUserName;
	}

	public String getLastOtpUserName()
	{
		return lastOtpUserName;
	}

	public void setLastOtpUserName(final String lastOtpUserName)
	{
		this.lastOtpUserName = lastOtpUserName;
	}

	public Boolean getOtpVerificationTokenEnabled()
	{
		return OTP_CUSTOMER_LOGIN_ENABLED;
	}
}
