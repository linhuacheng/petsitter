package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.Address;
import java.util.List;
import org.springframework.roo.addon.layers.repository.mongo.RooMongoRepository;

@RooMongoRepository(domainType = Address.class)
public interface AddressRepository {

    List<com.sjsu.petsitter.domain.Address> findAll();
}
