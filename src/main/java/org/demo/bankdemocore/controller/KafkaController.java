package org.demo.bankdemocore.controller;

import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.kafka.KafkaProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class KafkaController {

    KafkaProducer kafkaProducer;

    KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
        log.info("Kafka Controller created.");
    }

    @GetMapping
    public void getTransactionById() {
    }

    @PostMapping("/test/simple")
    public void requestTransaction() {
        log.info("Sending kafka message - test");
        kafkaProducer.sendMessage("Test message from producer");
    }

}
