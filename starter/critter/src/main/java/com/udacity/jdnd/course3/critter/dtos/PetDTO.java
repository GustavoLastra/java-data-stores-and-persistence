package com.udacity.jdnd.course3.critter.dtos;

import com.udacity.jdnd.course3.critter.dtos.PetSaveDTO;

/**
 * Represents the form that pet request and response data takes. Does not map
 * to the database directly.
 */
public class PetDTO extends PetSaveDTO {
    private long id;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
