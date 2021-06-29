package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.dtos.ScheduleCreateDTO;
import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.employee.Employee;
import com.udacity.jdnd.course3.critter.employee.EmployeeNotFoundException;
import com.udacity.jdnd.course3.critter.employee.EmployeeRepository;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetNotFoundException;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private static final Logger log = LoggerFactory.getLogger(Schedule.class);


    public ScheduleService(PetRepository petRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository) {
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleDTO save(ScheduleCreateDTO scheduleCreateDTO) {
        Schedule schedule  = new Schedule();
        Set<Pet> pets = scheduleCreateDTO.getPetIds().stream().map( petId -> petRepository.findById(petId).orElseThrow(PetNotFoundException::new)).collect(Collectors.toSet());
        Set<Employee> employees = scheduleCreateDTO.getEmployeeIds().stream().map(employeeId -> employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new)).collect(Collectors.toSet());
        schedule.setPets(pets);
        schedule.setEmployees(employees);
        schedule.setActivities(scheduleCreateDTO.getActivities());
        schedule.setDate(scheduleCreateDTO.getDate());
        Schedule createdSchedule = this.scheduleRepository.save(schedule);
        return this.mapToScheduleDto(createdSchedule);
    }

    public ScheduleDTO mapToScheduleDto(Schedule savedSchedule) {
        ScheduleDTO savedScheduleDto = new ScheduleDTO();
        savedScheduleDto.setPetIds(savedSchedule.getPets().stream().map(pet-> pet.getId()).collect(Collectors.toList()));
        savedScheduleDto.setEmployeeIds(savedSchedule.getEmployees().stream().map(employee-> employee.getId()).collect(Collectors.toList()));
        savedScheduleDto.setActivities(savedSchedule.getActivities());
        savedScheduleDto.setDate(savedSchedule.getDate());
        return savedScheduleDto;
    }

    public List<ScheduleDTO> getAllSchedules() {
        Iterable<Schedule> schedules = this.scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
        for (Schedule schedule : schedules) {
            scheduleDTOList.add(this.mapToScheduleDto(schedule));
        }
        return scheduleDTOList;
    }
}
