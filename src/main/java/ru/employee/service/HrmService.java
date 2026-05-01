package ru.employee.service;

import ru.employee.model.Employee;
import ru.employee.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;

public class HrmService {
    private final EmployeeRepository employeeRepository;
    public HrmService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(String name, String position, double salary, LocalDate hireDate) {
        Employee employee = new Employee(null, name, position, salary, hireDate);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public boolean deleteEmployee(Long id) {
        return employeeRepository.delete(id);
    }

    public double calculateAverageSalary() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (Employee employee : employees) {
            sum += employee.getSalary();
        }
        return sum / employees.size();
    }

    public Employee findTopPaidEmployee() {
        return employeeRepository.findAll().stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);
    }

    public List<Employee> findByPosition(String position) {
        return employeeRepository.findAll().stream()
                .filter(employee -> employee.getPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
    }

    public List<Employee> saveToTXT() {
        List<Employee> employees = employeeRepository.findAll();
        String fileName = "employees.txt";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(employees.toString());
            System.out.println("Data saved to: " + fileName);
        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }
        return employees;
    }


    public List<Employee> saveToCSV() {
        List<Employee> employees = employeeRepository.findAll();
        String fileName = "employees.csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Name,Position,Salary,HireDate\n");
            for (Employee employee : employees) {
                String line = employee.getName() + "," +
                employee.getPosition() + "," +
                employee.getSalary() + "," +
                employee.getHireDate() + "\n";
                writer.write(line);
            }
            System.out.println("Data saved to: " + fileName);
        } catch (IOException e) {
            System.out.println("Error while writing to file: " + e.getMessage());
        }
        return employees;
    }
}
