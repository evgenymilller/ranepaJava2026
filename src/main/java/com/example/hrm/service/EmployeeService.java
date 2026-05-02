package com.example.hrm.service;
import com.example.hrm.model.Employee;
import com.example.hrm.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getEmployeeByName(String name) {
        return employeeRepository.findByName(name);
    }

    public List<Employee> getEmployeeByPosition(String position) {
        return employeeRepository.findByPosition(position);
    }

    public List<Employee> getEmployeeByHireDate(LocalDate hireDate) {
        return employeeRepository.findByHireDate(hireDate);
    }

    public List<Employee> getEmployeeByCreatedDate(LocalDate createdDate) {
        return employeeRepository.findByCreatedDate(createdDate);
    }

    public List<Employee> getEmployeeBySalaryGreaterThanEqual(BigDecimal salary) {
        return employeeRepository.findBySalaryGreaterThanEqual(salary);
    }
}
