
package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.customer.Customer;
import com.udacity.jdnd.course3.critter.employee.Employee;
import com.udacity.jdnd.course3.critter.employee.EmployeeSkill;
import com.udacity.jdnd.course3.critter.pet.Pet;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private long id;

    @NotNull
    @ManyToMany
    Set<Employee> employees;

    @NotNull
    @ManyToMany
    Set<Pet> pets;

    @NotNull
    @ManyToMany
    Set<Customer> owner;

    @NotNull
    private LocalDate date;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Collection<EmployeeSkill> activities;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }

    public Set<Customer> getOwner() {
        return owner;
    }

    public void setOwner(Set<Customer> owner) {
        this.owner = owner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Collection<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Collection<EmployeeSkill> activities) {
        this.activities = activities;
    }
}
