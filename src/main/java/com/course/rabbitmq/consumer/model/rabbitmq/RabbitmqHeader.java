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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RabbitmqHeader {

    public static final String QUEUE_WAIT = "wait";

    private List<RabbitmqHeaderXDeath> xDeaths = new ArrayList<>();

    private String xFirstDeathExchange = "";

    private String xFirstDeathQueue = "";

    private String xFirstDeathReason = "";

    @SuppressWarnings("unchecked")
    public RabbitmqHeader(Map<String, Object> headers) {
        if(Objects.nonNull(headers)) {
            var exchange = Optional.ofNullable(headers.get("x-first-death-exchange"));

            var queue = Optional.ofNullable(headers.get("x-first-death-queue"));

            var reason = Optional.ofNullable(headers.get("x-first-death-reason"));

            if(exchange.isPresent()) {
                this.setxFirstDeathExchange(exchange.toString());
            }

            if(queue.isPresent()) {
                this.setxFirstDeathQueue(queue.toString());
            }

            if(reason.isPresent()) {
                this.setxFirstDeathReason(reason.toString());
            }

            var xDeathHeader = (List<Map<String, Object>>) headers.get("x-death");

            if(!Objects.isNull(xDeathHeader)) {
                for(Map<String, Object> xHeaderMap : xDeathHeader) {
                    var xHeader = new RabbitmqHeaderXDeath();
                    var headerExchange = Optional.ofNullable(xHeaderMap.get("exchange"));
                    var headerQueue = Optional.ofNullable(xHeaderMap.get("queue"));
                    var headerReason = Optional.ofNullable(xHeaderMap.get("reason"));
                    var headerCount = Optional.ofNullable(xHeaderMap.get("count"));
                    var headerDate = Optional.ofNullable(xHeaderMap.get("date"));
                    var headerRoutingKeys = Optional.ofNullable(xHeaderMap.get("routing-keys"));

                    headerExchange.ifPresent(h -> xHeader.setExchange(h.toString()));
                    headerQueue.ifPresent(h -> xHeader.setQueue(h.toString()));
                    headerReason.ifPresent(h -> xHeader.setReason(h.toString()));
                    headerCount.ifPresent(h -> xHeader.setCount(Integer.parseInt(h.toString())));
                    headerDate.ifPresent(h -> xHeader.setDate((Date) h));
                    headerRoutingKeys.ifPresent(h -> xHeader.setRoutingKeys((List<String>) h));

                    xDeaths.add(xHeader);
                }
            }
        }
    }

    public int getFailedRetryCount() {
        for(var xDeath : xDeaths) {
            if (xDeath.getExchange().toLowerCase().endsWith(QUEUE_WAIT) &&
                    xDeath.getQueue().toLowerCase().endsWith(QUEUE_WAIT)) {
                return xDeath.getCount();
            }
        }

        return 0;
    }

    public String getxFirstDeathExchange() {
        return xFirstDeathExchange;
    }

    public void setxFirstDeathExchange(String xFirstDeathExchange) {
        this.xFirstDeathExchange = xFirstDeathExchange;
    }

    public String getxFirstDeathQueue() {
        return xFirstDeathQueue;
    }

    public void setxFirstDeathQueue(String xFirstDeathQueue) {
        this.xFirstDeathQueue = xFirstDeathQueue;
    }

    public String getxFirstDeathReason() {
        return xFirstDeathReason;
    }

    public void setxFirstDeathReason(String xFirstDeathReason) {
        this.xFirstDeathReason = xFirstDeathReason;
    }
}
