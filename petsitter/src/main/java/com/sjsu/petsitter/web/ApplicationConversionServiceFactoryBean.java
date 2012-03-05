package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.PetDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
        registry.addConverter(getPetDetailToStringConverter());
		// Register application converters and formatters
	}

    public Converter<PetDetail, String> getPetDetailToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.PetDetail, java.lang.String>() {
            public String convert(PetDetail petDetail) {
                return new StringBuilder().append(petDetail.getPetName()).append(" ").append(petDetail.getPetType()).append(" ").append(petDetail.getDescription()).append(" ").append(petDetail.getUserId()).toString();
            }
        };
    }

}
