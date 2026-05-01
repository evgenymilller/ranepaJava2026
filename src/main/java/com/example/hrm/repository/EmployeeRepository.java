package com.example.hrm.repository;

import com.example.hrm.model.Employee;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByPosition(String position);
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
    Employee findByID(Long id);
    

}
