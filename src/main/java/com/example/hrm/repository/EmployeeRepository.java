package com.example.hrm.repository;

import com.example.hrm.model.Employee;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long id);
    List<Employee> findByName(String name);
    List<Employee> findByPosition(String position);
    List<Employee> findByHireDate(LocalDate hireDate);
    List<Employee> findByCreatedDate(LocalDate createdDate);
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);

}
