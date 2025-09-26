package org.customer_support.RagService.controller;

import org.customer_support.RagService.service.AskAiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AskAiController {

    private final AskAiService askAiService;

    public AskAiController(AskAiService askAiService) {
        this.askAiService = askAiService;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> ask(@RequestParam(value = "question") String question) {

        return new ResponseEntity<>(askAiService.chatResponse(question), HttpStatus.OK);

    }

}
