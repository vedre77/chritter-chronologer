package com.udacity.jdnd.course3.critter.user.customer;

import com.udacity.jdnd.course3.critter.user.BaseUser;
import com.udacity.jdnd.course3.critter.user.BaseUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private BaseUserRepository baseUserRepository;

    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = convertCustomerDTOToCustomer(customerDTO);
        baseUserRepository.save(newCustomer);
        return customerDTO;
    }

    public List<CustomerDTO> getAllCustomers() {
        List<BaseUser> baseUsers = baseUserRepository.findAll();
        return baseUsers.stream()
                .filter(user -> user instanceof Customer)
                .map(user -> (Customer) user)
                .map(this::convertCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }
    private CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        //customer.setPets(new HashSet<>());
        return customer;
    }
}
