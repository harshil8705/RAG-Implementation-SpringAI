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
      Answer the query strictly referring the provided context:
      {context}
      Query:
      {query}
      In case you don't have any answer from the context provided, just say:
      I'm sorry I don't have the information you are looking for.
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
