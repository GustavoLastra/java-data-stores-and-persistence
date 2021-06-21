package com.udacity.jdnd.course3.critter.user;
import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends UserBaseRepository<User> {


}
