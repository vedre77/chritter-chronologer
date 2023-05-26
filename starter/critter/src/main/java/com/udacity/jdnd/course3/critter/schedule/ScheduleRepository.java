package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT c FROM Schedule c JOIN c.pets p WHERE p.id = :petId")
    List<Schedule> findScheduleByPetId(@Param("petId") Long petId);

    @Query("SELECT c FROM Schedule c JOIN c.employees p WHERE p.id = :employeeId")
    List<Schedule> findScheduleByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT s FROM Schedule s JOIN s.pets p JOIN p.owner o WHERE o.id = :ownerId")
    List<Schedule> findScheduleByCustomerId(@Param("ownerId") Long customerId);
}
