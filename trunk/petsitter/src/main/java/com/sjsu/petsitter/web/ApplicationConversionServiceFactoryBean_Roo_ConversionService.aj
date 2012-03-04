// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.web;

import com.sjsu.petsitter.domain.Address;
import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.domain.Request;
import com.sjsu.petsitter.domain.Response;
import com.sjsu.petsitter.domain.Review;
import com.sjsu.petsitter.domain.User;
import com.sjsu.petsitter.domain.UserPreference;
import com.sjsu.petsitter.repository.UserPreferenceRepository;
import com.sjsu.petsitter.service.AddressService;
import com.sjsu.petsitter.service.PetDetailService;
import com.sjsu.petsitter.service.RequestService;
import com.sjsu.petsitter.service.ResponseService;
import com.sjsu.petsitter.service.ReviewService;
import com.sjsu.petsitter.service.UserService;
import com.sjsu.petsitter.web.ApplicationConversionServiceFactoryBean;
import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    @Autowired
    AddressService ApplicationConversionServiceFactoryBean.addressService;
    
    @Autowired
    PetDetailService ApplicationConversionServiceFactoryBean.petDetailService;
    
    @Autowired
    RequestService ApplicationConversionServiceFactoryBean.requestService;
    
    @Autowired
    ResponseService ApplicationConversionServiceFactoryBean.responseService;
    
    @Autowired
    ReviewService ApplicationConversionServiceFactoryBean.reviewService;
    
    @Autowired
    UserService ApplicationConversionServiceFactoryBean.userService;
    
    @Autowired
    UserPreferenceRepository ApplicationConversionServiceFactoryBean.userPreferenceRepository;
    
    public Converter<Address, String> ApplicationConversionServiceFactoryBean.getAddressToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.Address, java.lang.String>() {
            public String convert(Address address) {
                return new StringBuilder().append(address.getLine1()).append(" ").append(address.getLine2()).append(" ").append(address.getCity()).append(" ").append(address.getZipCode()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Address> ApplicationConversionServiceFactoryBean.getIdToAddressConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.Address>() {
            public com.sjsu.petsitter.domain.Address convert(java.math.BigInteger id) {
                return addressService.findAddress(id);
            }
        };
    }
    
    public Converter<String, Address> ApplicationConversionServiceFactoryBean.getStringToAddressConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.Address>() {
            public com.sjsu.petsitter.domain.Address convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Address.class);
            }
        };
    }
    
    public Converter<PetDetail, String> ApplicationConversionServiceFactoryBean.getPetDetailToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.PetDetail, java.lang.String>() {
            public String convert(PetDetail petDetail) {
                return new StringBuilder().append(petDetail.getPetName()).append(" ").append(petDetail.getPetType()).append(" ").append(petDetail.getDescription()).append(" ").append(petDetail.getUserId()).toString();
            }
        };
    }
    
    public Converter<BigInteger, PetDetail> ApplicationConversionServiceFactoryBean.getIdToPetDetailConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.PetDetail>() {
            public com.sjsu.petsitter.domain.PetDetail convert(java.math.BigInteger id) {
                return petDetailService.findPetDetail(id);
            }
        };
    }
    
    public Converter<String, PetDetail> ApplicationConversionServiceFactoryBean.getStringToPetDetailConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.PetDetail>() {
            public com.sjsu.petsitter.domain.PetDetail convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), PetDetail.class);
            }
        };
    }
    
    public Converter<Request, String> ApplicationConversionServiceFactoryBean.getRequestToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.Request, java.lang.String>() {
            public String convert(Request request) {
                return new StringBuilder().append(request.getRequestType()).append(" ").append(request.getComment()).append(" ").append(request.getPetId()).append(" ").append(request.getStatus()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Request> ApplicationConversionServiceFactoryBean.getIdToRequestConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.Request>() {
            public com.sjsu.petsitter.domain.Request convert(java.math.BigInteger id) {
                return requestService.findRequest(id);
            }
        };
    }
    
    public Converter<String, Request> ApplicationConversionServiceFactoryBean.getStringToRequestConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.Request>() {
            public com.sjsu.petsitter.domain.Request convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Request.class);
            }
        };
    }
    
    public Converter<Response, String> ApplicationConversionServiceFactoryBean.getResponseToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.Response, java.lang.String>() {
            public String convert(Response response) {
                return new StringBuilder().append(response.getResponseId()).append(" ").append(response.getRequestId()).append(" ").append(response.getPetId()).append(" ").append(response.getComment()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Response> ApplicationConversionServiceFactoryBean.getIdToResponseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.Response>() {
            public com.sjsu.petsitter.domain.Response convert(java.math.BigInteger id) {
                return responseService.findResponse(id);
            }
        };
    }
    
    public Converter<String, Response> ApplicationConversionServiceFactoryBean.getStringToResponseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.Response>() {
            public com.sjsu.petsitter.domain.Response convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Response.class);
            }
        };
    }
    
    public Converter<Review, String> ApplicationConversionServiceFactoryBean.getReviewToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.Review, java.lang.String>() {
            public String convert(Review review) {
                return new StringBuilder().append(review.getFromUserId()).append(" ").append(review.getToUserId()).append(" ").append(review.getType()).append(" ").append(review.getRating()).toString();
            }
        };
    }
    
    public Converter<BigInteger, Review> ApplicationConversionServiceFactoryBean.getIdToReviewConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.Review>() {
            public com.sjsu.petsitter.domain.Review convert(java.math.BigInteger id) {
                return reviewService.findReview(id);
            }
        };
    }
    
    public Converter<String, Review> ApplicationConversionServiceFactoryBean.getStringToReviewConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.Review>() {
            public com.sjsu.petsitter.domain.Review convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), Review.class);
            }
        };
    }
    
    public Converter<User, String> ApplicationConversionServiceFactoryBean.getUserToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.User, java.lang.String>() {
            public String convert(User user) {
                return new StringBuilder().append(user.getUserName()).append(" ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(" ").append(user.getEmail()).toString();
            }
        };
    }
    
    public Converter<BigInteger, User> ApplicationConversionServiceFactoryBean.getIdToUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.User>() {
            public com.sjsu.petsitter.domain.User convert(java.math.BigInteger id) {
                return userService.findUser(id);
            }
        };
    }
    
    public Converter<String, User> ApplicationConversionServiceFactoryBean.getStringToUserConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.User>() {
            public com.sjsu.petsitter.domain.User convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), User.class);
            }
        };
    }
    
    public Converter<UserPreference, String> ApplicationConversionServiceFactoryBean.getUserPreferenceToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.sjsu.petsitter.domain.UserPreference, java.lang.String>() {
            public String convert(UserPreference userPreference) {
                return new StringBuilder().append(userPreference.getPreferenceId()).append(" ").append(userPreference.getServiceLocation()).append(" ").append(userPreference.getPetType()).append(" ").append(userPreference.getComment()).toString();
            }
        };
    }
    
    public Converter<BigInteger, UserPreference> ApplicationConversionServiceFactoryBean.getIdToUserPreferenceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.math.BigInteger, com.sjsu.petsitter.domain.UserPreference>() {
            public com.sjsu.petsitter.domain.UserPreference convert(java.math.BigInteger id) {
                return userPreferenceRepository.findOne(id);
            }
        };
    }
    
    public Converter<String, UserPreference> ApplicationConversionServiceFactoryBean.getStringToUserPreferenceConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.sjsu.petsitter.domain.UserPreference>() {
            public com.sjsu.petsitter.domain.UserPreference convert(String id) {
                return getObject().convert(getObject().convert(id, BigInteger.class), UserPreference.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
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
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
