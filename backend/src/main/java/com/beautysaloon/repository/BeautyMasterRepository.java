package com.beautysaloon.repository;

import com.beautysaloon.model.BeautyMaster;

import java.util.List;

public interface BeautyMasterRepository {

    List<BeautyMaster> findAll();
}
