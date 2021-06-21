package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends UserBaseRepository<User> {


}
