package com.udacity.jdnd.course3.critter.api.endpoints;

import com.udacity.jdnd.course3.critter.api.dtos.PetDTO;
import com.udacity.jdnd.course3.critter.api.dtos.PetCreateDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    public final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetCreateDTO petDTO) {
        return this.petService.save(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return this.petService.getPet(petId);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService.getPets();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService.getPetsByOwner(ownerId);
    }
}
