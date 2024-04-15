package com.beautysaloon.repository;

import com.beautysaloon.model.BeautyMaster;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeautyMasterRepository {

    List<BeautyMaster> findAll();
}
