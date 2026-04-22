package ru.employee.presentation;

import ru.employee.model.Employee;
import ru.employee.repository.EmployeeRepository;
import ru.employee.service.HrmService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

public class HrmApplication {
    public static void main(String[] args) {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService hrmService = new HrmService(repository);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HRM System Menu ===");
            System.out.println("1. Show all employees");
            System.out.println("2. Add employee");
            System.out.println("3. Delete employee by ID");
            System.out.println("4. Find employee by ID");
            System.out.println("5. Show statistics");
            System.out.println("6. Show employees filtered by position");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    showAllEmployees(hrmService);
                    break;
                case 2:
                    addEmployee(scanner, hrmService);
                    break;
                case 3:
                    deleteEmployee(scanner, hrmService);
                    break;
                case 4:
                    findEmployeeById(scanner, hrmService);
                    break;
                case 5:
                    showStatistics(hrmService);
                    break;
                case 6:
                    filteredEmployees(scanner, hrmService);
                    break;
                case 7:
                    while (true) {
                        System.out.println("Do you want to save changes before exiting?");
                        System.out.println("1. No");
                        System.out.println("2. Yes (txt)");
                        System.out.println("3. Yes (csv)");
                        System.out.print("Choose an option: ");
                        int choiceOut;
                        try {
                            choiceOut = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Enter a number.");
                            continue;
                        }
                        switch (choiceOut) {
                        case 1:
                            System.out.println("Goodbye!");
                            scanner.close();
                            return;
                        case 2:
                            saveEmployeesToTXT(hrmService);
                            System.out.println("Goodbye!");
                            scanner.close();
                            return;
                        case 3:
                            saveEmployeesToCSV(hrmService);
                            System.out.println("Goodbye!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Unknown menu item.");
                        }
                    }
                    
                default:
                    System.out.println("Unknown menu item.");
            }

        }

    }

    private static void showAllEmployees(HrmService hrmService) {
        List<Employee> employees = hrmService.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static void addEmployee(Scanner scanner, HrmService hrmService) {
        try {
            // имя
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            // позиция
            System.out.print("Enter position: ");
            String position = scanner.nextLine();
            // зп с проверкой на число и неотрицательность
            double salary;
            while (true) {
                System.out.print("Enter salary: ");
                try {
                    salary = Double.parseDouble(scanner.nextLine());
                    if (salary < 0) {
                        System.out.println("Salary cannot be negative.");
                        continue;
                    }
                    break; // всё ок — выходим из цикла
                } catch (NumberFormatException e) {
                    System.out.println("Invalid salary format.");
                }
            }
            // дата найма с проверкой формата
            LocalDate hireDate;
            while (true) {
                System.out.print("Enter hire date (yyyy-mm-dd): ");
                try {
                    hireDate = LocalDate.parse(scanner.nextLine());
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format.");
                }
            }
            // добавляем сотрудника
            Employee employee = hrmService.addEmployee(name, position, salary, hireDate);
            System.out.println("Employee added successfully with ID: " + employee.getId());
        } catch (Exception e) {
            System.out.println("Error while adding employee: " + e.getMessage());

        }

    }

    private static void deleteEmployee(Scanner scanner, HrmService hrmService) {
        try {
            System.out.print("Enter employee ID to delete: ");
            Long id = Long.parseLong(scanner.nextLine());
            boolean deleted = hrmService.deleteEmployee(id);
            if (deleted) {
                System.out.println("Employee deleted.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void findEmployeeById(Scanner scanner, HrmService hrmService) {
        try {
            System.out.print("Enter employee ID: ");
            Long id = Long.parseLong(scanner.nextLine());
            Employee employee = hrmService.getEmployeeById(id);
            if (employee != null) {
                System.out.println(employee);
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private static void showStatistics(HrmService hrmService) {
        double averageSalary = hrmService.calculateAverageSalary();
        Employee topPaidEmployee = hrmService.findTopPaidEmployee();
        System.out.println("Average salary: " + averageSalary);
        if (topPaidEmployee != null) {
            System.out.println("Top paid employee: " + topPaidEmployee);
        } else {
            System.out.println("No employees in system.");
        }
    }

    private static void saveEmployeesToTXT(HrmService hrmService) {
        try {
            hrmService.saveToTXT();
        } catch (Exception e) {
            System.out.println("Error while saving to TXT: " + e.getMessage());
        }
    }

    private static void saveEmployeesToCSV(HrmService hrmService) {
        try {
            hrmService.saveToCSV();
        } catch (Exception e) {
            System.out.println("Error while saving to CSV: " + e.getMessage());
        }
    }

    private static void filteredEmployees(Scanner scanner, HrmService hrmService) {
        System.out.print("Enter employee position: ");
        String position = scanner.nextLine().trim();

        if (position.isEmpty()) {
            System.out.println("Position cannot be empty.");
            return;
        }

        List<Employee> filteredEmployees = hrmService.findByPosition(position);
        if (filteredEmployees.isEmpty()) {
            System.out.println("No employees found for position: " + position);
            return;
        }

        for (Employee employee : filteredEmployees) {
            System.out.println(employee);
        }
    }
}
