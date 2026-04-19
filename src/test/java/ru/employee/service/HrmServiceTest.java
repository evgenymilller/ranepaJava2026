package ru.employee.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ru.employee.repository.EmployeeRepository;

public class HrmServiceTest {
    @Test
    void testAddEmployee() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        service.addEmployee("Egor", "Analyst", 150.0, LocalDate.of(2025, 5, 1));

        assertEquals(1, repository.findAll().size());
    }

    @Test
    void testCalculateAverageSalary() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        service.addEmployee("Иван", "CEO", 300.0, LocalDate.of(2024, 1, 10));
        service.addEmployee("Мария", "РОП", 200.0, LocalDate.of(2024, 2, 10));
        service.addEmployee("Петр", "Аналитик", 100.0, LocalDate.of(2024, 3, 10));

        double average = service.calculateAverageSalary();

        assertEquals(200.0, average);
    }

    @Test
    void testFindTopPaidEmployee() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        service.addEmployee("Иван", "CEO", 300.0, LocalDate.of(2024, 1, 10));
        service.addEmployee("Мария", "РОП", 200.0, LocalDate.of(2024, 2, 10));
        service.addEmployee("Петр", "Аналитик", 100.0, LocalDate.of(2024, 3, 10));

        var top = service.findTopPaidEmployee();

        assertEquals("Иван", top.getName());
    }
}
