package com.beautysaloon.repository;

import com.beautysaloon.model.BeautyMaster;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BeautyMasterRepository extends MongoRepository<BeautyMaster, Long> {
}
