/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commercefacades.quote.data.QuoteData;
import de.hybris.platform.commerceservices.model.process.QuoteProcessModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.order.QuoteModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.QuoteService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Required;

import java.util.Optional;


/**
 * Velocity context for a quote notification email.
 */
public class QuoteNotificationEmailContext extends AbstractEmailContext<QuoteProcessModel>
{
    private transient QuoteService quoteService;

    private transient Converter<QuoteModel, QuoteData> quoteConverter;

    private QuoteData quoteData;

    @Override
    public void init(final QuoteProcessModel quoteProcessModel, final EmailPageModel emailPageModel)
    {
        super.init(quoteProcessModel, emailPageModel);

        quoteData = getQuoteConverter().convert(getQuote(quoteProcessModel));
    }

    public QuoteData getQuote()
    {
        return quoteData;
    }

    @Override
    protected BaseSiteModel getSite(final QuoteProcessModel quoteProcessModel)
    {
        return getQuote(quoteProcessModel).getSite();
    }

    @Override
    protected CustomerModel getCustomer(final QuoteProcessModel quoteProcessModel)
    {
        return (CustomerModel) getQuote(quoteProcessModel).getUser();
    }

    @Override
    protected LanguageModel getEmailLanguage(final QuoteProcessModel quoteProcessModel)
    {
        return getCustomer(quoteProcessModel).getSessionLanguage();
    }

    protected QuoteModel getQuote(final QuoteProcessModel quoteProcessModel)
    {
        return Optional.of(quoteProcessModel)
                .map(QuoteProcessModel::getQuoteCode)
                .map(getQuoteService()::getCurrentQuoteForCode).orElseThrow();
    }

    @Required
    public void setQuoteService(QuoteService quoteService)
    {
        this.quoteService = quoteService;
    }

    protected QuoteService getQuoteService()
    {
        return quoteService;
    }

    @Required
    public void setQuoteConverter(Converter<QuoteModel, QuoteData> quoteConverter)
    {
        this.quoteConverter = quoteConverter;
    }

    protected Converter<QuoteModel, QuoteData> getQuoteConverter()
    {
        return quoteConverter;
    }
}
