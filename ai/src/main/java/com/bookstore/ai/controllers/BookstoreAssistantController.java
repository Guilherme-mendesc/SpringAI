package com.bookstore.ai.controllers;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/bookstore")
public class BookstoreAssistantController {

    @Autowired
    OpenAiChatClient chatClient;

    public BookstoreAssistantController(OpenAiChatClient chatClient){
        this.chatClient = chatClient;
    }

    @GetMapping("/informations")
    public String bookstoreChat(@RequestParam(value = "message",
    defaultValue = "Quais livros sao consideradores best sellets nos ultimos anos?") String message){
        return chatClient.call(message);
    }

//    @GetMapping("/informations")
//    public ChatResponse bookstoreChatEx2(@RequestParam(value = "message",
//            defaultValue = "Quais livros sao consideradores best sellets nos ultimos anos?") String message){
//        return chatClient.call(new Prompt(message));
//    }

    @GetMapping("/reviews")
    public String bookstoreReview(@RequestParam(value = "book",defaultValue = "The GodFather")String book){
        PromptTemplate promptTemplate = new PromptTemplate("""
            Por favor, me forneca
            Um breve resumo do livro{book}
            e tambem a biografia de seu autor
            """);
        promptTemplate.add("book", book);
        return this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();

    }

    @GetMapping("/stream/informations")
    public Flux<String> bookstoreChatStream(@RequestParam(value = "message",
            defaultValue = "Quais livros sao consideradores best sellets nos ultimos anos?") String message){
        return chatClient.stream(message);
    }


}
