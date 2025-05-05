package com.raxrot.ems.controller;

import com.raxrot.ems.dto.EmployeeDTO;
import com.raxrot.ems.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Create a new employee", description = "Adds a new employee to the system")
    @ApiResponse(responseCode = "201", description = "Employee successfully created")
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Creating employee with email: {}", employeeDTO.getEmail());
        EmployeeDTO savedEmployee = employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = "Get employee by ID", description = "Returns a single employee by their ID")
    @ApiResponse(responseCode = "200", description = "Employee found")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        log.info("Fetching employee with id: {}", id);
        EmployeeDTO savedEmployee = employeeService.getEmployeeById(id);
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }

    @Operation(summary = "Get all employees", description = "Returns all employees in the system")
    @ApiResponse(responseCode = "200", description = "List returned")
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getEmployees() {
        log.info("Fetching all employees");
        List<EmployeeDTO> employeeDTOS = employeeService.getAllEmployees();
        return new ResponseEntity<>(employeeDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Update employee", description = "Updates an existing employee by ID")
    @ApiResponse(responseCode = "200", description = "Employee updated")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id,@Valid @RequestBody EmployeeDTO employeeDTO) {
        log.info("Updating employee with id: {}", id);
        EmployeeDTO savedEmployee = employeeService.updateEmployee(id, employeeDTO);
        return new ResponseEntity<>(savedEmployee, HttpStatus.OK);
    }


    @Operation(summary = "Delete employee", description = "Deletes an employee by ID")
    @ApiResponse(responseCode = "204", description = "Employee deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Long id) {
        log.warn("Deleting employee with id: {}", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
