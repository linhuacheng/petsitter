// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.sjsu.petsitter.repository;

import com.sjsu.petsitter.domain.PetDetail;
import com.sjsu.petsitter.repository.PetDetailRepository;
import java.math.BigInteger;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

privileged aspect PetDetailRepository_Roo_Mongo_Repository {
    
    declare parents: PetDetailRepository extends PagingAndSortingRepository<PetDetail, BigInteger>;
    
    declare @type: PetDetailRepository: @Repository;
    
}
