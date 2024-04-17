package com.beautysaloon.repository;

import com.beautysaloon.model.BeautyMaster;

import java.util.List;
import java.util.Optional;

public interface BeautyMasterRepository {

    List<BeautyMaster> findAll();

    Optional<BeautyMaster> findById(long beautyMasterId);

    BeautyMaster save(BeautyMaster master);
}
