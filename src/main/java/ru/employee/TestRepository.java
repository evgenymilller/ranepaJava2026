package ru.employee;

import ru.employee.model.Employee;
import ru.employee.repository.EmployeeRepository;

import java.time.LocalDate;

public class TestRepository {
    public static void main(String[] args) {
        EmployeeRepository repo = new EmployeeRepository();

        Employee e1 = new Employee(null, "Иван", "Менеджер", 50000, LocalDate.of(2024, 1, 10));
        Employee e2 = new Employee(null, "Мария", "Разработчик", 70000, LocalDate.of(2023, 5, 20));

        repo.save(e1);
        repo.save(e2);

        System.out.println("Все сотрудники:");
        System.out.println(repo.findAll());

        System.out.println("\nПоиск по id 1:");
        System.out.println(repo.findById(1L));

        System.out.println("\nУдаление id 1:");
        System.out.println(repo.delete(1L));

        System.out.println("\nПосле удаления:");
        System.out.println(repo.findAll());
    }
}