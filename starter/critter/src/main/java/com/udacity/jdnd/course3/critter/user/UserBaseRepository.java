package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends User>
        extends CrudRepository<T, Long> {

    public T findByName(String name);

}
