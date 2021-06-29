package com.udacity.jdnd.course3.critter.employee;

import com.udacity.jdnd.course3.critter.user.UserBaseRepository;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.Set;

@Transactional
public interface EmployeeRepository extends UserBaseRepository<Employee> {
    Set<Employee> findByDaysAvailable(DayOfWeek dayOfWeek);
}
