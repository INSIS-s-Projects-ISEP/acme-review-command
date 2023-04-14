package com.isep.acme.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RabbitmqService {

    private final String instanceId;
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, Object message){
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public Object sendRpc(String exchange){
        return rabbitTemplate.convertSendAndReceive(exchange, "", instanceId);
    }
}
