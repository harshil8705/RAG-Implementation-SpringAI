package org.customer_support.RagService.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.template.st.StTemplateRenderer;
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
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template("""
                    <query>
        
                    Context information is below.
        
			        ---------------------
			        <question_answer_context>
			        ---------------------
        
			        Given the context information and no prior knowledge, answer the query.
        
			        Follow these rules:
        
			        1. If the answer is not in the context, just say that you don't know.
			        2. Avoid statements like "Based on the context..." or "The provided information...".
                    """)
                .build();

        QuestionAnswerAdvisor qaAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .promptTemplate(customPromptTemplate)
                .build();

        return chatClient.prompt(userQuery)
                .advisors(qaAdvisor)
                .call()
                .content();

    }

}
