package com.udacity.jdnd.course3.critter.employee;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
@Entity
@Getter
@Setter
public class Employee extends User {

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> skills;

    @NotNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = DayOfWeek.class)
    private Set<DayOfWeek> daysAvailable;

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    @ManyToMany
    @JoinTable(
            name = "employee_schedule",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "schedule_id"))
    List<Schedule> schedules = new ArrayList<>();

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedule(Schedule schedule) {
        this.schedules.add(schedule) ;
    }
}
