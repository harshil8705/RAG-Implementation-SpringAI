package org.customer_support.RagService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class VectorStoreInitializer implements CommandLineRunner {

    private final VectorStore vectorStore;

    public VectorStoreInitializer(VectorStore vectorStore) {

        this.vectorStore = vectorStore;

    }

    @Value("classpath:pdfs/LG AC Customer Support Guide.pdf")
    private Resource customerSupportGuide;

    Logger logger = LoggerFactory.getLogger(VectorStoreInitializer.class);

    @Override
    public void run(String... args) {

        var pdfReader = new PagePdfDocumentReader(customerSupportGuide);
        TextSplitter textSplitter = new TokenTextSplitter();

        logger.info("Loading documents into VectorStore...");
        vectorStore.accept(textSplitter.apply(pdfReader.get()));
        logger.info("Documents loaded successfully.");

    }
}
