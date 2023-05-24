package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Transactional
    public Customer saveCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = convertCustomerDTOToCustomer(customerDTO);
        return customerRepository.save(newCustomer);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOList = customers.stream()
                .map(customer -> convertCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());
        return customerDTOList;
    }
    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        for (Pet pet : customer.getPets()) {
            customerDTO.setPetIds(pet.getId());
        }
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        if (!CollectionUtils.isEmpty(customerDTO.getPetIds())) {
            List<Pet> pets = customerDTO.getPetIds().stream()
                    .map(petRepository::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            customer.getPets().addAll(pets);
        }

        return customer;
    }
}
