package com.raxrot.ems.service.impl;

import com.raxrot.ems.dto.EmployeeDTO;
import com.raxrot.ems.entity.Employee;
import com.raxrot.ems.exception.ResourceNotFoundException;
import com.raxrot.ems.repository.EmployeeRepository;
import com.raxrot.ems.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Employee with id " + id + " not found"));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee with id " + id + " not found");
        }

        Optional<Employee> existing = employeeRepository.findByEmail(employeeDTO.getEmail());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            throw new IllegalArgumentException("Email " + employeeDTO.getEmail() + " is already in use");
        }

        Employee employeeFromDb = employeeRepository.findById(id).get();
        employeeFromDb.setFirstName(employeeDTO.getFirstName());
        employeeFromDb.setLastName(employeeDTO.getLastName());
        employeeFromDb.setEmail(employeeDTO.getEmail());

        Employee savedEmployee = employeeRepository.save(employeeFromDb);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }
}
