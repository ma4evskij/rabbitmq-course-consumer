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
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PictureDeathLetterConsumer {

    public static final Logger LOGGER = LoggerFactory.getLogger(PictureDeathLetterConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = {"q.guideline.image.dead", "q.guideline.vector.dead"})
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        var p = objectMapper.readValue(message.getBody(), Picture.class);

        LOGGER.error("[ERROR] message error : {}", p);

        channel.basicAck(deliveryTag, false);
    }
}
