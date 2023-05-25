package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public PetDTO savePet(PetDTO petDTO) {
        Pet pet = convertPetDTOToPet(petDTO);
        petRepository.save(pet);
        Long petOwnerId = pet.getOwner().getId();
        Optional<Customer> optionalCustomer = customerRepository.findById(petOwnerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            // Add the pet to the customer's list of pets
            customer.getPets().add(pet);
            // Save the updated customer
            customerRepository.save(customer);
        } else {
            throw new EntityNotFoundException("Customer with id " + petOwnerId + " not found");
        }
        return convertPetToPetDTO(pet);
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

    public List<PetDTO> getAllPets() {
        List<Pet> allPetsList = petRepository.findAll();
        List<PetDTO> allPetDTOList = allPetsList.stream()
                .map(pet -> convertPetToPetDTO(pet))
                .collect(Collectors.toList());
        return allPetDTOList;

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
