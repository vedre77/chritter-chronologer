package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public PetDTO savePet(PetDTO petDTO) {
        return convertPetToPetDTO(petRepository.save(convertPetDTOToPet(petDTO)));
    }

    public PetDTO getPet(Long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return convertPetToPetDTO(pet.get());
        } else {
            throw new EntityNotFoundException("Pet with id " + petId + " not found");
        }
    }

    public List<PetDTO> getPetsByOwner(Long ownerId) {
        List<Pet> ownerPetList = petRepository.findByOwnerId(ownerId);
        List<PetDTO> ownerPetDTOList = ownerPetList.stream()
                .map(pet -> convertPetToPetDTO(pet))
                .collect(Collectors.toList());
        return ownerPetDTOList;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }

    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setType(petDTO.getType());
        Long ownerId = petDTO.getOwnerId();
        Customer petOwner = customerRepository.getOne(ownerId);
        pet.setOwner(petOwner);
        pet.setNotes(petDTO.getNotes());
        return pet;
    }
}
