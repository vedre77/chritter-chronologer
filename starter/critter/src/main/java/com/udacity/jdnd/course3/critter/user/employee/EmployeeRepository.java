package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    @Query("SELECT e FROM Employee e JOIN e.skills s WHERE :day MEMBER OF e.daysAvailable AND s IN :skills GROUP BY e HAVING COUNT(e) = :skillsCount")
    Optional<List<Employee>> findEmployeesByDayAndSkills(@Param("day") DayOfWeek day, @Param("skills") Set<EmployeeSkill> skills, @Param("skillsCount") long skillsCount);

}
