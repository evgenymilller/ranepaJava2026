import org.junit.jupiter.api.Test;
import ru.employee.model.Employee;
import ru.employee.repository.EmployeeRepository;
import ru.employee.service.HrmService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class test {

    @Test
    void shouldCalculateAverageSalary() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        service.addEmployee("Иван", "CEO", 300.0, LocalDate.of(2024, 1, 10));
        service.addEmployee("Мария", "РОП", 200.0, LocalDate.of(2024, 2, 10));
        service.addEmployee("Петр", "Аналитик", 100.0, LocalDate.of(2024, 3, 10));

        double average = service.calculateAverageSalary();
        System.out.println("TEST RUNNING");
        System.out.println("Average salary: " + average);

        assertEquals(200.0, average, 0.000001);
    }

    @Test
    void shouldReturnTopPaidEmployee() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        service.addEmployee("Alice", "Engineer", 100.0, LocalDate.of(2024, 1, 10));
        service.addEmployee("Bob", "Manager", 200.0, LocalDate.of(2024, 2, 10));
        Employee top = service.addEmployee("Diana", "Director", 500.0, LocalDate.of(2024, 3, 10));

        Employee found = service.findTopPaidEmployee();

        assertNotNull(found);
        assertEquals(top.getId(), found.getId());
        assertEquals("Diana", found.getName());
    }

    @Test
    void shouldReturnNullForTopPaidEmployeeWhenRepositoryIsEmpty() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        Employee found = service.findTopPaidEmployee();

        assertNull(found);
    }

    @Test
    void shouldAddEmployeeAndAssignId() {
        EmployeeRepository repository = new EmployeeRepository();
        HrmService service = new HrmService(repository);

        Employee created = service.addEmployee("Egor", "Analyst", 150.0, LocalDate.of(2025, 5, 1));

        assertNotNull(created.getId());
        Employee fromRepository = service.getEmployeeById(created.getId());
        assertNotNull(fromRepository);
        assertEquals("Egor", fromRepository.getName());
    }
}
