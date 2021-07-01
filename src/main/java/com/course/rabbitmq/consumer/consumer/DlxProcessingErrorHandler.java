/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.course.rabbitmq.consumer.consumer;

import com.course.rabbitmq.consumer.model.rabbitmq.RabbitmqHeader;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.time.LocalDateTime;

public class DlxProcessingErrorHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(DlxProcessingErrorHandler.class);

    @NonNull
    private String deadExchangeName;

    private int maxRetryCount = 3;

    public DlxProcessingErrorHandler(@NonNull String deadExchangeName) {
        this.deadExchangeName = deadExchangeName;
    }

    public DlxProcessingErrorHandler(@NonNull String deadExchangeName, int maxRetryCount) {
        this.deadExchangeName = deadExchangeName;
        setMaxRetryCount(maxRetryCount);
    }

    private void setMaxRetryCount(int maxRetryCount) {
        if(maxRetryCount > 1000) {
            throw new IllegalArgumentException("Max retry must between 0-1000");
        }

        this.maxRetryCount = maxRetryCount;
    }

    public boolean handle(Message message, Channel channel, long deliveryTag) {
        var rabbitmqHeader = new RabbitmqHeader(message.getMessageProperties().getHeaders());

        try {
            if(rabbitmqHeader.getFailedRetryCount() >= maxRetryCount) {
                LOGGER.warn(
                        "[DEAD] Error at {} on retry {} for message {}",
                        LocalDateTime.now(),
                        rabbitmqHeader.getFailedRetryCount(),
                        new String(message.getBody())
                );

                channel.basicPublish(
                        getDeadExchangeName(),
                        message.getMessageProperties().getReceivedRoutingKey(),
                        null,
                        message.getBody()
                );

                channel.basicAck(deliveryTag, false);
            } else {
                LOGGER.warn(
                        "[RETRY] Error at {} on retry {} for message {}",
                        LocalDateTime.now(),
                        rabbitmqHeader.getFailedRetryCount(),
                        new String(message.getBody())
                );

                channel.basicReject(deliveryTag, false);
            }

            return true;
        } catch (IOException e) {
            LOGGER.error(
                    "[HANDLER-FAILED] Error at {} on retry {} for message {}",
                    LocalDateTime.now(),
                    rabbitmqHeader.getFailedRetryCount(),
                    new String(message.getBody())
            );
        }

        return false;
    }

    @NonNull
    public String getDeadExchangeName() {
        return deadExchangeName;
    }
}
