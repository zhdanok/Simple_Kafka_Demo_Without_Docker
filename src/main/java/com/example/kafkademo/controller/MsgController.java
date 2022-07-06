package com.example.kafkademo.controller;

import com.example.kafkademo.dto.AddressDTO;
import com.example.kafkademo.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("msg")
@RequiredArgsConstructor
public class MsgController {

    private final KafkaTemplate<Long, UserDTO> kafkaTemplate;

    @PostMapping
    public void sendOrder(Long msgId, UserDTO msg) {
        msg.setName("Ivan");
        msg.setAge(19L);
        msg.setAddressDTO(new AddressDTO("BY", "Minsk", "Lenina", 1L, 1L));
        ListenableFuture<SendResult<Long, UserDTO>> future = kafkaTemplate.send("msg", msgId, msg);
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }


}
