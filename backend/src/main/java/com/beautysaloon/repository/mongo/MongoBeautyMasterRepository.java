package com.beautysaloon.repository.mongo;

import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoBeautyMasterRepository extends BeautyMasterRepository, MongoRepository<BeautyMaster, Long> {
}
