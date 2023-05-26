package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.customer.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOToSchedule(scheduleDTO);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return convertScheduleToScheduleDTO(savedSchedule);
    }

    public List<ScheduleDTO> retrieveAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        List<ScheduleDTO> scheduleDTOsList = scheduleList.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
        return scheduleDTOsList;
    }

    public List<ScheduleDTO> findAllScheduleForPet(long petId) {
        List<Schedule> petScheduleList = scheduleRepository.findScheduleByPetId(petId);
        List<ScheduleDTO> retrievedPetScheduleDTOs = petScheduleList.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
        return retrievedPetScheduleDTOs;
    }

    public List<ScheduleDTO> findAllScheduleForEmployee(long employeeId) {
        List<Schedule> employeeScheduleList = scheduleRepository.findScheduleByEmployeeId(employeeId);
        List<ScheduleDTO> retrievedEmployeeScheduleDTOs = employeeScheduleList.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
        return retrievedEmployeeScheduleDTOs;
    }

    public List<ScheduleDTO> findAllScheduleForCustomer(long customerId) {
        // debugging: first check all the repo:
        List<Schedule> allSchedules = scheduleRepository.findAll();

        List<Schedule> customerScheduleList = scheduleRepository.findScheduleByCustomerId(customerId);
        List<ScheduleDTO> retrievedCustomerScheduleList = customerScheduleList.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
        return retrievedCustomerScheduleList;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        // Retrieve the Pet entities using the provided IDs
        List<Pet> pets = petRepository.findAllById(scheduleDTO.getPetIds());
        schedule.setPets(pets);
        // Retrieve the Employee entities using the provided IDs
        List<Employee> employees = employeeRepository.findAllById(scheduleDTO.getEmployeeIds());
        schedule.setEmployees(employees);

        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());

        // Save the Schedule entity
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return savedSchedule;

    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());
        scheduleDTO.setPetIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        return scheduleDTO;
    }
}
