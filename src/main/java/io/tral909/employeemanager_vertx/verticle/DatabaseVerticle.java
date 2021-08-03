package io.tral909.employeemanager_vertx.verticle;

import io.tral909.employeemanager_vertx.codec.ArrayListCodec;
import io.tral909.employeemanager_vertx.codec.EmployeeCodec;
import io.tral909.employeemanager_vertx.model.Employee;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.Message;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowIterator;
import io.vertx.sqlclient.Tuple;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DatabaseVerticle extends AbstractVerticle {

    private PgPool pgPool;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        // можно явно указать настройки
        pgPool = PgPool.pool(vertx, new PgConnectOptions()
            .setHost("127.0.0.1")
            .setUser("postgres")
            .setDatabase("employeemanager")
            .setPassword("12345"), new PoolOptions());

        // или через переменные окружения
        // в ubuntu в /etc/environment (забираются через System.getenv(..))
        // pgPool = PgPool.pool();

        vertx.eventBus().registerDefaultCodec(ArrayList.class, new ArrayListCodec());
        vertx.eventBus().registerDefaultCodec(Employee.class, new EmployeeCodec());

        //todo адреса вынести в переменные
        vertx.eventBus().consumer("employee.get", this::getEmployee);
        vertx.eventBus().consumer("employee.get.by.id", this::getEmployeeById);
        vertx.eventBus().consumer("employee.add", this::addEmployee);
        vertx.eventBus().consumer("employee.update", this::updateEmployee);
        vertx.eventBus().consumer("employee.delete", this::deleteEmployee);

        log.info("Database Verticle deployed!");
    }

    private void getEmployee(Message<Void> msg) {
        String query = "SELECT * FROM employee";
        pgPool.preparedQuery(query)
            .execute()
            .onSuccess(rows -> {
                List<Employee> result = new ArrayList<>();
                for (Row row : rows) {
                    result.add(buildEmployee(row));
                }
                msg.reply(result);
            })
            .onFailure(failure -> {
                log.error("getEmployee query failed", failure);
                msg.fail(500, failure.getMessage());
            });
    }

    private void getEmployeeById(Message<Long> msg) {
        String query = "SELECT * FROM employee WHERE id = $1";
        pgPool.preparedQuery(query)
            .execute(Tuple.of(msg.body()))
            .onSuccess(rowSet -> {
                RowIterator<Row> iterator = rowSet.iterator();
                if (iterator.hasNext()) {
                    msg.reply(buildEmployee(iterator.next()));
                } else {
                    msg.fail(404, "Employee with id=" + msg.body() + " not found!");
                }
            })
            .onFailure(failure -> {
                log.error("getEmployeeById query failed", failure);
                msg.fail(500, failure.getMessage());
            });
    }

    private void addEmployee(Message<Employee> msg) {
        Employee e = msg.body();
        String query = "INSERT INTO employee(name, email, job_title, phone, image_url, code) "
            + "VALUES ($1, $2, $3, $4, $5, $6)";
        Tuple tuple = Tuple.of(e.getName(), e.getEmail(), e.getJobTitle(), e.getPhone(), e.getImageUrl(), e.getCode());
        pgPool.preparedQuery(query)
            .execute(tuple)
            .onSuccess(rows -> log.info("Saved new employee {}", tuple.deepToString()))
            .onFailure(failure -> log.error("addEmployee query failed", failure));
    }

    private void updateEmployee(Message<Employee> msg) {
    }

    private void deleteEmployee(Message<Long> msg) {
    }

    private Employee buildEmployee(Row row) {
        Employee employee = new Employee();
        employee.setId(row.getLong("id"));
        employee.setName(row.getString("name"));
        employee.setEmail(row.getString("email"));
        employee.setJobTitle(row.getString("job_title"));
        employee.setPhone(row.getString("phone"));
        employee.setImageUrl(row.getString("image_url"));
        employee.setCode(row.getString("code"));
        return employee;
    }
}
