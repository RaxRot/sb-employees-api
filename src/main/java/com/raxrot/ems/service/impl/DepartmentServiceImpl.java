package com.raxrot.ems.service.impl;

import com.raxrot.ems.dto.DepartmentDTO;
import com.raxrot.ems.entity.Department;
import com.raxrot.ems.exception.ResourceNotFoundException;
import com.raxrot.ems.repository.DepartmentRepository;
import com.raxrot.ems.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        log.info("Creating new department: {}", departmentDTO.getDepartmentName());
        Department department = modelMapper.map(departmentDTO, Department.class);
        Department savedDepartment = departmentRepository.save(department);
        log.info("Department created with id: {}", savedDepartment.getId());
        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    @Override
    public DepartmentDTO getDepartment(Long id) {
        log.info("Fetching department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department with id {} not found", id);
                    return new ResourceNotFoundException("Department with id " + id + " not found");
                });
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @Override
    public List<DepartmentDTO> getDepartments() {
        log.info("Fetching all departments");
        return departmentRepository.findAll().stream()
                .map(dep -> modelMapper.map(dep, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        log.info("Updating department with id: {}", id);
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Department with id {} not found for update", id);
                    return new ResourceNotFoundException("Department with id " + id + " not found");
                });

        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setDepartmentDescription(departmentDTO.getDepartmentDescription());
        Department updated = departmentRepository.save(department);
        log.info("Department with id {} successfully updated", id);
        return modelMapper.map(updated, DepartmentDTO.class);
    }

    @Override
    public void deleteDepartment(Long id) {
        log.info("Deleting department with id: {}", id);
        if (!departmentRepository.existsById(id)) {
            log.warn("Attempted to delete non-existing department with id: {}", id);
            throw new ResourceNotFoundException("Department with id " + id + " not found");
        }
        departmentRepository.deleteById(id);
        log.info("Department with id {} deleted successfully", id);
    }
}
