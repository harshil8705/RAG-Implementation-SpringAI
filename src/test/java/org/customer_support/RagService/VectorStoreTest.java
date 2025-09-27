package org.customer_support.RagService;

import org.customer_support.RagService.service.DocumentLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class VectorStoreTest {

    @Autowired
    private VectorStore vectorStore;

    @Autowired
    private DocumentLoaderService documentLoaderService;

    @Test
    void testSimilaritySearch() throws IOException {

        documentLoaderService.loadDocument();

        vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query("How can I operate my LG AC?")
                        .topK(3)
                        .build()
        ).forEach(doc -> {
            System.out.println("🔎 Retrieved: " + doc.getFormattedContent());
        });
    }

}
