package com.udacity.jdnd.course3.critter.employee;

import com.udacity.jdnd.course3.critter.user.UserBaseRepository;

import javax.transaction.Transactional;

@Transactional
public interface EmployeeRepository extends UserBaseRepository<Employee> {
}
