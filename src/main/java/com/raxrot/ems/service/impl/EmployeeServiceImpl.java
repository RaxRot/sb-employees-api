package com.raxrot.ems.service.impl;

import com.raxrot.ems.dto.EmployeeDTO;
import com.raxrot.ems.entity.Employee;
import com.raxrot.ems.exception.ResourceNotFoundException;
import com.raxrot.ems.repository.EmployeeRepository;
import com.raxrot.ems.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating employee: {}", employeeDTO.getEmail());
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with id: {}", id);
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee with id {} not found", id);
                    return new ResourceNotFoundException("Employee with id " + id + " not found");
                });
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        log.info("Fetching all employees");
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with id: {}", id);

        if (!employeeRepository.existsById(id)) {
            log.warn("Attempted to update non-existing employee with id: {}", id);
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }

        Optional<Employee> existing = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            log.warn("Email {} is already in use by employee with id {}", employeeDTO.getEmail(), existing.get().getId());
            throw new IllegalArgumentException("Email " + employeeDTO.getEmail() + " is already in use");
        }

        Employee employeeFromDb = employeeRepository.findById(id).get();
        employeeFromDb.setFirstName(employeeDTO.getFirstName());
        employeeFromDb.setLastName(employeeDTO.getLastName());
        employeeFromDb.setEmail(employeeDTO.getEmail());

        Employee savedEmployee = employeeRepository.save(employeeFromDb);
        log.info("Employee with id {} successfully updated", id);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id: {}", id);
        if (!employeeRepository.existsById(id)) {
            log.warn("Attempted to delete non-existing employee with id: {}", id);
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }
        employeeRepository.deleteById(id);
        log.info("Employee with id {} deleted successfully", id);
    }

}
