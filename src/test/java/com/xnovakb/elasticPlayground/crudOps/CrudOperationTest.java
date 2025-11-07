package com.xnovakb.elasticPlayground.crudOps;

import com.xnovakb.elasticPlayground.AbstractTest;
import com.xnovakb.elasticPlayground.crudOps.entity.Employee;
import com.xnovakb.elasticPlayground.crudOps.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class CrudOperationTest extends AbstractTest {

    @Autowired
    private EmployeeRepository repo;

    @Test
    public void crud() {
        final var employee = createEmployee(1, "Artur", 25);
        repo.save(employee);

        printAll();
        var employeeSavedMutable = repo.findById(1).orElseThrow();

        Assertions.assertEquals(employee.toString(), employeeSavedMutable.toString());

        employeeSavedMutable.setAge(27);
        employeeSavedMutable = repo.save(employeeSavedMutable);

        printAll();
        Assertions.assertEquals(employeeSavedMutable.getAge(), 27);

        repo.deleteById(1);

        printAll();
        Assertions.assertTrue(repo.findById(1).isEmpty());
    }

    @Test
    public void bulkCrud() {
        final var employees = IntStream.rangeClosed(1, 10)
            .mapToObj(i -> createEmployee(i, "name-" + 1, 25 + i))
            .toList();

        repo.saveAll(employees);

        printAll();
        Assertions.assertEquals(10, repo.count());

        final var ids = List.of(2, 4, 6);
        final var iterableEmployeeHits = repo.findAllById(ids);
        final var employeeHits = Streamable.of(iterableEmployeeHits).toList();

        Assertions.assertEquals(3, employeeHits.size());

        employeeHits.forEach(e -> e.setAge(e.getAge() + 10));
        repo.saveAll(employeeHits);

        repo.findAllById(ids).forEach(
            e -> Assertions.assertEquals(e.getId() + 35, e.getAge())
        );

        repo.deleteAllById(ids);

        printAll();
        Assertions.assertEquals(7, repo.count());
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
