package io.tral909.employeemanager_vertx.codec;

import io.tral909.employeemanager_vertx.model.Employee;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

public class EmployeeCodec implements MessageCodec<Employee, Employee> {
    @Override
    public void encodeToWire(Buffer buffer, Employee employee) {

    }

    @Override
    public Employee decodeFromWire(int pos, Buffer buffer) {
        return null;
    }

    @Override
    public Employee transform(Employee employee) {
        return employee;
    }

    @Override
    public String name() {
        return "EmployeeCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}