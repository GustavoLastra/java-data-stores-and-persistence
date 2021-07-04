package com.udacity.jdnd.course3.critter.api.endpoints;

import com.udacity.jdnd.course3.critter.api.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.save(scheduleDTO);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getSchedulesForPet(@PathVariable long petId) {
        return scheduleService.getSchedulesForPet(petId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getScheduleForEmployee(employeeId);
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getSchedulesForCustomer(@PathVariable long customerId) {
        return scheduleService.getSchedulesForCustomer(customerId);
    }
}
