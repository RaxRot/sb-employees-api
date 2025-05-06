package com.raxrot.ems.service;

import com.raxrot.ems.dto.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO getDepartment(Long id);
    List<DepartmentDTO> getDepartments();
    DepartmentDTO updateDepartment(Long id,DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
}
