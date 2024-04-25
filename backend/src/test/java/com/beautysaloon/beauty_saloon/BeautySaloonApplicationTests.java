package com.beautysaloon.beauty_saloon;

import com.beautysaloon.model.BeautyMaster;
import com.beautysaloon.repository.BeautyMasterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class BeautySaloonApplicationTests {

    @Autowired
    BeautyMasterRepository beautyMasterRepository;
    @Test
    void createBeautyMasters() {

        var master1 = BeautyMaster.builder()
                .id(0L)
                .name("Emily Johnson")
                .appointments(new ArrayList<>())
                .build();
        var master2 = BeautyMaster.builder()
                .id(1L)
                .name("Christopher Davis")
                .appointments(new ArrayList<>())
                .build();
        var master3 = BeautyMaster.builder()
                .id(2L)
                .name("Samantha Martinez")
                .appointments(new ArrayList<>())
                .build();

        beautyMasterRepository.save(master1);
        beautyMasterRepository.save(master2);
        beautyMasterRepository.save(master3);
    }
}
