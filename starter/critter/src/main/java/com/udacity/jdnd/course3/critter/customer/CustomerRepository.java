package com.udacity.jdnd.course3.critter.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.UserBaseRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Transactional
public interface CustomerRepository extends UserBaseRepository<Customer> {

    Optional<Customer> findCustomerByPetsIn(Set<Pet> pets);
}
