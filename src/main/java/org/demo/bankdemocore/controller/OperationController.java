package org.demo.bankdemocore.controller;

import lombok.extern.slf4j.Slf4j;
import org.demo.bankdemocore.domain.ErrorCode;
import org.demo.bankdemocore.exception.TransactionError;
import org.demo.bankdemocore.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/ops")

public class OperationController {

    private void throwValidationException() throws ValidationException {
        throw new ValidationException();
    }

    private void throwTransactionError() throws TransactionError {
        throw new TransactionError(ErrorCode.ERROR_CODE_00_N1);
    }

    @GetMapping("/exception/test")
    public ResponseEntity<String> testExceptionAop(@RequestParam boolean trigger, @RequestParam boolean isValidationException) throws Exception {
        if (trigger) {
            if (isValidationException) {
                throwValidationException();
            } else {
                throwTransactionError();
            }
        }
        return ResponseEntity.ok().body("Process completed");
    }

    @GetMapping("/exception/test-mono")
    public Mono<ResponseEntity<String>> testExceptionAopMono(@RequestParam boolean trigger, @RequestParam boolean isValidationException) {
       return Mono.fromRunnable(() -> {
           if (trigger) {
               if (isValidationException) {
                   try {
                       throwValidationException();
                   } catch (Exception exception) {
                       log.warn("Test1");
                   }
               } else {
                   try {
                       throwTransactionError();
                   } catch (Exception exception) {
                       log.warn("Test2");
                   }
               }
           }
        }).thenReturn(ResponseEntity.ok().body("Process completed"));
    }
}
