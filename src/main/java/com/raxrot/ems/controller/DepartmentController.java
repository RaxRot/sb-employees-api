package com.raxrot.ems.controller;

import com.raxrot.ems.dto.DepartmentDTO;
import com.raxrot.ems.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Operation(summary = "Create a new department", description = "Adds a new department to the system")
    @ApiResponse(responseCode = "201", description = "Department successfully created")
    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        log.info("Creating department: {}", departmentDTO);
        DepartmentDTO savedDepartment = departmentService.createDepartment(departmentDTO);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @Operation(summary = "Get department by ID", description = "Returns a single department by its ID")
    @ApiResponse(responseCode = "200", description = "Department found")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.info("Fetching department with id: {}", id);
        DepartmentDTO savedDepartment = departmentService.getDepartment(id);
        return new ResponseEntity<>(savedDepartment, HttpStatus.OK);
    }

    @Operation(summary = "Get all departments", description = "Returns a list of all departments")
    @ApiResponse(responseCode = "200", description = "List of departments returned")
    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        log.info("Fetching all departments");
        List<DepartmentDTO> departmentDTOS = departmentService.getDepartments();
        return new ResponseEntity<>(departmentDTOS, HttpStatus.OK);
    }

    @Operation(summary = "Update department", description = "Updates an existing department by ID")
    @ApiResponse(responseCode = "200", description = "Department successfully updated")
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        log.info("Updating department with id: {}", id);
        DepartmentDTO savedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return new ResponseEntity<>(savedDepartment, HttpStatus.OK);
    }

    @Operation(summary = "Delete department", description = "Deletes a department by ID")
    @ApiResponse(responseCode = "204", description = "Department successfully deleted")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.warn("Deleting department with id: {}", id);
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}
