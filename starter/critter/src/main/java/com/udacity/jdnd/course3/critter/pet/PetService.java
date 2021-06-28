package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.customer.Customer;
import com.udacity.jdnd.course3.critter.customer.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.dtos.EmployeeDTO;
import com.udacity.jdnd.course3.critter.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.dtos.PetSaveDTO;
import com.udacity.jdnd.course3.critter.employee.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.lang.Object;
import java.util.Set;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(Pet.class);


    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetDTO save(PetSaveDTO petSaveDTO) {
        Pet pet = new Pet();
        pet.setName(petSaveDTO.getName());
        pet.setPetType(petSaveDTO.getType().toString());
        pet.setBirthDate(petSaveDTO.getBirthDate());
        pet.setNotes(petSaveDTO.getNotes());
        Customer customer = this.customerRepository.findById(petSaveDTO.getOwnerId()).orElseThrow(CustomerNotFoundException::new);
        pet.setCustomer(customer);
        customer.setPet(pet);
        Pet savedPet = this.petRepository.save(pet);
        this.customerRepository.save(customer);
        return this.mapToPetDto(savedPet);
    }

    public List<PetDTO> getPets() {
        Iterable<Pet> pets = this.petRepository.findAll();
        List<PetDTO> petDtoList = new ArrayList<PetDTO>();
        for (Pet pet : pets) {
            petDtoList.add(this.mapToPetDto(pet));
        }
        return petDtoList;
    }

    public PetDTO getPet(Long petId) {
        Optional<Pet> pet = this.petRepository.findById(petId);
        return this.mapToPetDto(pet.get());
    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {
        Customer customer = this.customerRepository.findById(ownerId).orElseThrow(CustomerNotFoundException::new);

        Iterable<Pet> pets   = customer.getPets();
        List<PetDTO> petDtoList = new ArrayList<PetDTO>();
        if (pets != null) {
            for (Pet pet : pets) {
                petDtoList.add(this.mapToPetDto(pet));
            }
        }
        return petDtoList;
    }

    public PetDTO mapToPetDto(Pet savedPet) {
        PetDTO savedPetDto = new PetDTO();
        savedPetDto.setId(savedPet.getId());
        savedPetDto.setName(savedPet.getName());
        savedPetDto.setNotes(savedPet.getNotes());
        savedPetDto.setOwnerId(savedPet.getCustomer().getId());
        savedPetDto.setType(PetType.valueOf(savedPet.getPetType()));
        return savedPetDto;
    }
}
