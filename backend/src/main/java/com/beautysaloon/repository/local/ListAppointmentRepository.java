package com.beautysaloon.repository.local;

import com.beautysaloon.repository.AppointmentRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ListAppointmentRepository implements AppointmentRepository {
}
