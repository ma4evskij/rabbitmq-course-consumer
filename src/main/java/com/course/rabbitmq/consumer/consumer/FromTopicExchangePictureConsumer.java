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

import com.course.rabbitmq.consumer.entity.Picture;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FromTopicExchangePictureConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(FromTopicExchangePictureConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = {"q.picture.image", "q.picture.vector", "q.picture.filter", "q.picture.log"})
    public void listen(Message messageAmqp) throws IOException {
        var picture = objectMapper.readValue(messageAmqp.getBody(), Picture.class);

        var key = messageAmqp.getMessageProperties().getReceivedRoutingKey();

        LOGGER.info("On picture {} with routing key {}", picture, key);
    }
}
