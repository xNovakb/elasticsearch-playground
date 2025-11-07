package com.xnovakb.elasticPlayground;

import com.xnovakb.elasticPlayground.crudOps.entity.Employee;
import com.xnovakb.elasticPlayground.crudOps.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CrudOperationTest extends AbstractTest {

    @Autowired
    private EmployeeRepository repo;

    @Test
    public void crud() {
        final var employee = createEmployee(1, "Artur", 25);
        repo.save(employee);

        printAll();
        var employeeSaved = repo.findById(1).orElseThrow();

        Assertions.assertEquals(employee.toString(), employeeSaved.toString());

        employeeSaved.setAge(27);
        employeeSaved = repo.save(employeeSaved);

        printAll();
        Assertions.assertEquals(employeeSaved.getAge(), 27);

        repo.deleteById(1);

        printAll();
        Assertions.assertTrue(repo.findById(1).isEmpty());
    }

    private Employee createEmployee(Integer id, String name, Integer age) {
        return new Employee().setId(id)
            .setName(name)
            .setAge(age);
    }

    private void printAll() {
        repo.findAll().forEach(
            e -> log.info("employee: {}", e)
        );
    }

}
