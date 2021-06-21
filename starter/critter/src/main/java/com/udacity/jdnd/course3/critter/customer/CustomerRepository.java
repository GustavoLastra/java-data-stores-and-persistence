package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.user.UserBaseRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomerRepository extends UserBaseRepository<Customer> {
}
