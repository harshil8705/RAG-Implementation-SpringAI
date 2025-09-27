package org.customer_support.RagService.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
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
            Answer the question below using only the provided context.

            Question: <query>

            Context:
            <question_answer_context>

            Rules:
            1. If the answer is not in the context, say "I don't know."
            2. Do not mention the context in your answer.
            """).build();

        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(customPromptTemplate)
                .searchRequest(SearchRequest.builder()
                        .similarityThreshold(0.6d)
                        .topK(3)
                        .build()
                )
                .build();

        return chatClient.prompt(userQuery)
                .advisors(qaAdvisor)
                .call()
                .content();

    }

}
