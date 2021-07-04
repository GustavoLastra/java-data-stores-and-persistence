package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.api.dtos.ScheduleCreateDTO;
import com.udacity.jdnd.course3.critter.api.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.customer.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.customer.CustomerRepository;
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
import java.util.stream.Collectors;

@Service
public class ScheduleService {
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final ScheduleRepository scheduleRepository;
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(Schedule.class);


    public ScheduleService(PetRepository petRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, ScheduleRepository scheduleRepository, CustomerRepository customerRepository1) {
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.scheduleRepository = scheduleRepository;
        this.customerRepository = customerRepository1;
    }

    public ScheduleDTO save(ScheduleCreateDTO scheduleCreateDTO) {
        List<Pet> pets = getPets(scheduleCreateDTO);
        List<Employee> employees = getEmployees(scheduleCreateDTO);
        Schedule createdSchedule = createSchedule(scheduleCreateDTO, pets,employees);
        updatePetsWithSchedule(pets, createdSchedule);
        updateEmployeesWithSchedule(employees, createdSchedule);
        return this.mapToScheduleDto(createdSchedule);
    }

    public List<ScheduleDTO> getAllSchedules() {
        Iterable<Schedule> schedules = this.scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOList = new ArrayList<ScheduleDTO>();
        for (Schedule schedule : schedules) {
            scheduleDTOList.add(this.mapToScheduleDto(schedule));
        }
        return scheduleDTOList;
    }

    public List<ScheduleDTO> getSchedulesForCustomer(long customerId) {
        List<ScheduleDTO> schedules = new ArrayList<>();
        List<Pet> pets = getCustomerPets(customerId);
        pets.forEach(pet -> pet.getSchedules()
                .forEach(schedule -> schedules.add(this.mapToScheduleDto(schedule))));
        List<ScheduleDTO> scheduleDTOList = new ArrayList<>(schedules);
        return scheduleDTOList;
    }


    public List<ScheduleDTO> getScheduleForEmployee(long employeeId) {
        List<Schedule> schedules = this.employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new).getSchedules();
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : schedules){
            ScheduleDTO scheduleDTO = this.mapToScheduleDto(schedule);
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }

    public List<ScheduleDTO> getSchedulesForPet(long petId) {
        List<Schedule> schedules = this.petRepository.findById(petId).orElseThrow(PetNotFoundException::new).getSchedules();
        List<ScheduleDTO>  scheduleDTOS= new ArrayList<>();
        for (Schedule schedule: schedules){
            scheduleDTOS.add(this.mapToScheduleDto(schedule));
        }
        return scheduleDTOS;
    }

    public ScheduleDTO mapToScheduleDto(Schedule savedSchedule) {
        ScheduleDTO savedScheduleDto = new ScheduleDTO();
        savedScheduleDto.setPetIds(savedSchedule.getPets().stream().map(pet-> pet.getId()).collect(Collectors.toList()));
        savedScheduleDto.setEmployeeIds(savedSchedule.getEmployees().stream().map(employee-> employee.getId()).collect(Collectors.toList()));
        savedScheduleDto.setActivities(savedSchedule.getActivities());
        savedScheduleDto.setDate(savedSchedule.getDate());
        return savedScheduleDto;
    }

    private void updateEmployeesWithSchedule(List<Employee> employees, Schedule createdSchedule) {
        employees.forEach(employee-> {
            employee.setSchedule(createdSchedule);
            this.employeeRepository.save(employee);
        });
    }

    private void updatePetsWithSchedule(List<Pet> pets, Schedule createdSchedule) {
        pets.forEach(pet-> {
            pet.setSchedule(createdSchedule);
            this.petRepository.save(pet);
        });
    }

    private Schedule createSchedule(ScheduleCreateDTO scheduleCreateDTO, List<Pet> pets, List<Employee> employees) {
        Schedule schedule  = new Schedule();
        schedule.setPets(pets);
        schedule.setEmployees(employees);
        schedule.setActivities(scheduleCreateDTO.getActivities());
        schedule.setDate(scheduleCreateDTO.getDate());
        return this.scheduleRepository.save(schedule);
    }

    private List<Employee> getEmployees(ScheduleCreateDTO scheduleCreateDTO) {
        return scheduleCreateDTO.getEmployeeIds().stream().map(employeeId -> employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new)).collect(Collectors.toList());
    }

    private List<Pet> getPets(ScheduleCreateDTO scheduleCreateDTO) {
        return scheduleCreateDTO.getPetIds().stream().map(petId -> petRepository.findById(petId).orElseThrow(PetNotFoundException::new)).collect(Collectors.toList());
    }

    private List<Pet> getCustomerPets(long customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new).getPets();
    }
}
