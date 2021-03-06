package io.tral909.employeemanager_vertx.model;

import lombok.Data;

@Data
public class Employee {
    Long id;
    String name;
    String email;
    String jobTitle;
    String phone;
    String imageUrl;
    String code;
}
