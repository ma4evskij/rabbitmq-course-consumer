/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.course.rabbitmq.consumer.model.rabbitmq;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class RabbitmqHeaderXDeath {

    private int count;

    private String exchange;

    private String queue;

    private String reason;

    private List<String> routingKeys;

    private Date date;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<String> getRoutingKeys() {
        return routingKeys;
    }

    public void setRoutingKeys(List<String> routingKeys) {
        this.routingKeys = routingKeys;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RabbitmqHeaderXDeath that = (RabbitmqHeaderXDeath) o;
        return getCount() == that.getCount() &&
                Objects.equals(getExchange(), that.getExchange()) &&
                Objects.equals(getQueue(), that.getQueue()) &&
                Objects.equals(getReason(), that.getReason()) &&
                Objects.equals(getRoutingKeys(), that.getRoutingKeys()) &&
                Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCount(), getExchange(), getQueue(), getReason(), getRoutingKeys(), getDate());
    }
}
