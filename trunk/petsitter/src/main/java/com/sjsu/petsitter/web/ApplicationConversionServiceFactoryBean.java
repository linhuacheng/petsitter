package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.PetDetail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import com.sjsu.petsitter.service.PetDetailService;
/**
 * A central place to register application converters and formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Autowired
    PetDetailService petDetailService;
    
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
        registry.addConverter(getPetDetailToStringConverter());
		// Register application converters and formatters
	}

	
	
    public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getAddressToStringConverter());
        registry.addConverter(getIdToAddressConverter());
        registry.addConverter(getStringToAddressConverter());
        registry.addConverter(getPetDetailToStringConverter());
        registry.addConverter(getIdToPetDetailConverter());
        registry.addConverter(getStringToPetDetailConverter());
        registry.addConverter(getRequestToStringConverter());
        registry.addConverter(getIdToRequestConverter());
        registry.addConverter(getStringToRequestConverter());
        registry.addConverter(getResponseToStringConverter());
        registry.addConverter(getIdToResponseConverter());
        registry.addConverter(getStringToResponseConverter());
        registry.addConverter(getReviewToStringConverter());
        registry.addConverter(getIdToReviewConverter());
        registry.addConverter(getStringToReviewConverter());
        registry.addConverter(getUserToStringConverter());
        registry.addConverter(getIdToUserConverter());
        registry.addConverter(getStringToUserConverter());
        registry.addConverter(getUserPreferenceToStringConverter());
        registry.addConverter(getIdToUserPreferenceConverter());
        registry.addConverter(getStringToUserPreferenceConverter());
    }
	
    public Converter<PetDetail, String> getPetDetailToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.PetDetail, java.lang.String>() {
            public String convert(PetDetail petDetail) {
                return new StringBuilder().append(petDetail.getPetType()).append(" - ").append(petDetail.getPetName()).toString();
            }
        };
    }
    
    public Converter<String, PetDetail> getStringToPetDetailConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.PetDetail>() {
            public com.sjsu.petsitter.domain.PetDetail convert(String id) {
            	PetDetail petDetail = petDetailService.findPetDetailByPetId(id);
                return petDetail;
            }
        };
    }

}
