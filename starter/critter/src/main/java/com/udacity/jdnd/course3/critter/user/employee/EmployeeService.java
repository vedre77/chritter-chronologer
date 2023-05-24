package com.udacity.jdnd.course3.critter.user.employee;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        Employee newEmployee = convertEmployeeDTOToEmployee(employeeDTO);
        return employeeRepository.save(newEmployee);
    }

    public EmployeeDTO getEmployeeById(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return convertEmployeeToEmployeeDTO(employee.get());
        } else {
            throw new EntityNotFoundException("Employee with id " + employeeId + " not found");
        }
    }

    private EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        // employee.setSchedules()
        return employee;
    }
}
