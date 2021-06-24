/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.course.rabbitmq.consumer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Employee {

    @JsonProperty("employee_id")
    private String empoyeeId;

    private String name;

    @JsonProperty("bith_date")
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate bithDate;

    public Employee(String empoyeeId, String name, LocalDate bithDate) {
        this.empoyeeId = empoyeeId;
        this.name = name;
        this.bithDate = bithDate;
    }

    public String getEmpoyeeId() {
        return empoyeeId;
    }

    public void setEmpoyeeId(String empoyeeId) {
        this.empoyeeId = empoyeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBithDate() {
        return bithDate;
    }

    public void setBithDate(LocalDate bithDate) {
        this.bithDate = bithDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empoyeeId='" + empoyeeId + '\'' +
                ", name='" + name + '\'' +
                ", bithDate=" + bithDate +
                '}';
    }
}
