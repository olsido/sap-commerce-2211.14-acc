/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package org.training.facades.populators;

import de.hybris.platform.commercefacades.product.ImageFormatMapping;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.VariantOptionData;
import de.hybris.platform.commercefacades.product.data.VariantOptionQualifierData;
import de.hybris.platform.core.model.media.MediaContainerModel;
import de.hybris.platform.core.model.media.MediaFormatModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.type.ComposedTypeModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.media.MediaContainerService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.type.TypeService;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AcceleratorVariantOptionDataPopulatorTest
{
	private final String TEST_VARIANT_TYPE_CODE = "ApparelStyleVariantProduct";
	private final String TEST_VARIANT_OPTION_QUALIFIER = "style";
	private final String TEST_VARIANT_TYPE_KEY_FMT = "%s.%s";
	private final String TEST_VARIANT_TYPE_ATTRIBUTE_VALUE = "styleSwatch";
	private final String TEST_MEDIA_FORMAT = "30Hx30W";

	@Mock
	private TypeService typeService;
	@Mock
	private MediaService mediaService;
	@Mock
	private MediaContainerService mediaContainerService;
	@Mock
	private ImageFormatMapping imageFormatMapping;
	@Mock
	private Map<String, String> variantAttributeMapping;
	@Mock
	private Converter<MediaModel, ImageData> imageConverter;

	@InjectMocks
	@Spy
	private AcceleratorVariantOptionDataPopulator populator;

	@Test
	public void shouldPopulateImage()
	{
		// given
		final MediaModel picture = mock(MediaModel.class);
		final MediaContainerModel container = mock(MediaContainerModel.class);
		final VariantProductModel source = mock(VariantProductModel.class);
		when(source.getPicture()).thenReturn(picture);
		when(picture.getMediaContainer()).thenReturn(container);

		final ComposedTypeModel productType = mock(ComposedTypeModel.class);
		when(typeService.getComposedTypeForClass(VariantProductModel.class)).thenReturn(productType);

		final VariantOptionQualifierData variantOptionQualifier = mock(VariantOptionQualifierData.class);
		Collection<VariantOptionQualifierData> variantOptionQualifiers = List.of(variantOptionQualifier);
		final VariantOptionData target = mock(VariantOptionData.class);
		when(target.getVariantOptionQualifiers()).thenReturn(variantOptionQualifiers);

		when(productType.getCode()).thenReturn(TEST_VARIANT_TYPE_CODE);
		when(variantOptionQualifier.getQualifier()).thenReturn(TEST_VARIANT_OPTION_QUALIFIER);
		when(variantAttributeMapping.get(String.format(TEST_VARIANT_TYPE_KEY_FMT, TEST_VARIANT_TYPE_CODE, TEST_VARIANT_OPTION_QUALIFIER))).thenReturn(TEST_VARIANT_TYPE_ATTRIBUTE_VALUE);
		when(imageFormatMapping.getMediaFormatQualifierForImageFormat(TEST_VARIANT_TYPE_ATTRIBUTE_VALUE)).thenReturn(TEST_MEDIA_FORMAT);

		final MediaFormatModel mediaFormat = mock(MediaFormatModel.class);
		when(mediaService.getFormat(TEST_MEDIA_FORMAT)).thenReturn(mediaFormat);

		final MediaModel media = mock(MediaModel.class);
		when(mediaContainerService.getMediaForFormat(container, mediaFormat)).thenReturn(media);
		final ImageData image = mock(ImageData.class);
		when(imageConverter.convert(media)).thenReturn(image);

		// when
		populator.populate(source, target);

		// then
		verify(variantOptionQualifier, Mockito.times(1)).setImage(image);
	}

	@Test
	public void shouldNotPopulateImageWhenMediaNotFound()
	{
		// given
		final MediaModel picture = mock(MediaModel.class);
		final MediaContainerModel container = mock(MediaContainerModel.class);
		final VariantProductModel source = mock(VariantProductModel.class);
		when(source.getPicture()).thenReturn(picture);
		when(picture.getMediaContainer()).thenReturn(container);

		final ComposedTypeModel productType = mock(ComposedTypeModel.class);
		when(typeService.getComposedTypeForClass(VariantProductModel.class)).thenReturn(productType);

		final VariantOptionQualifierData variantOptionQualifier = mock(VariantOptionQualifierData.class);
		Collection<VariantOptionQualifierData> variantOptionQualifiers = List.of(variantOptionQualifier);
		final VariantOptionData target = mock(VariantOptionData.class);
		when(target.getVariantOptionQualifiers()).thenReturn(variantOptionQualifiers);

		when(productType.getCode()).thenReturn(TEST_VARIANT_TYPE_CODE);
		when(variantOptionQualifier.getQualifier()).thenReturn(TEST_VARIANT_OPTION_QUALIFIER);
		when(variantAttributeMapping.get(String.format(TEST_VARIANT_TYPE_KEY_FMT, TEST_VARIANT_TYPE_CODE, TEST_VARIANT_OPTION_QUALIFIER))).thenReturn(TEST_VARIANT_TYPE_ATTRIBUTE_VALUE);
		when(imageFormatMapping.getMediaFormatQualifierForImageFormat(TEST_VARIANT_TYPE_ATTRIBUTE_VALUE)).thenReturn(TEST_MEDIA_FORMAT);

		final MediaFormatModel mediaFormat = mock(MediaFormatModel.class);
		when(mediaService.getFormat(TEST_MEDIA_FORMAT)).thenReturn(mediaFormat);

		when(mediaContainerService.getMediaForFormat(container, mediaFormat)).thenThrow(new ModelNotFoundException(""));

		// when
		populator.populate(source, target);

		// then
		verify(variantOptionQualifier, Mockito.never()).setImage(Mockito.any(ImageData.class));
	}
}
