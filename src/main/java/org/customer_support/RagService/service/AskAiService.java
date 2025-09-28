package org.customer_support.RagService.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class AskAiService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public AskAiService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public String chatResponse(String userQuery) {

        PromptTemplate customPromptTemplate = PromptTemplate.builder()
                .template("""
                Answer the query strictly referring to the provided context:
                
                Context:
                {context}
                
                Query:
                {query}
                
                Rules:
                1. If the answer is not in the context, just say you don't know.
                2. Avoid statements like "Based on the context..." or "The provided information...".
                """).build();

        return chatClient.prompt()
                .user(userQuery)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .call()
                .content();

    }

}
