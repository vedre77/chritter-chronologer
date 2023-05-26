package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
        } else {
            throw new EntityNotFoundException("The employee with id" + employeeId + "not found");
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeRequestDTO) {
        DayOfWeek day = employeeRequestDTO.getDate().getDayOfWeek();
        Set<EmployeeSkill> requiredSkills = employeeRequestDTO.getSkills();
        Optional<List<Employee>> optionalAdequateEmployees = employeeRepository.findEmployeesByDayAndSkills(day, requiredSkills, requiredSkills.size());
        if (optionalAdequateEmployees.isPresent()) {
            List<Employee> adequateEmployees = optionalAdequateEmployees.get();
            List<EmployeeDTO> adequateEmployeeDTOs = adequateEmployees.stream()
                    .map(employee -> convertEmployeeToEmployeeDTO(employee))
                    .collect(Collectors.toList());
            return adequateEmployeeDTOs;
        } else {
            throw new EntityNotFoundException("No adequate employee found.");
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
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        employee.setSkills(employeeDTO.getSkills());
        // employee.setSchedules()
        return employee;
    }
}
