package com.beautysaloon.repository.mongo;

import com.beautysaloon.model.Appointment;
import com.beautysaloon.repository.AppointmentRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoAppointmentRepository extends AppointmentRepository, MongoRepository<Appointment, Long> {
}
