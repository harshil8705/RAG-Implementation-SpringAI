package org.customer_support.RagService;

import org.customer_support.RagService.service.DocumentLoaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

public class VectorStoreInitializer implements CommandLineRunner {

    private final DocumentLoaderService documentLoaderService;

    public VectorStoreInitializer(DocumentLoaderService documentLoaderService) {

        this.documentLoaderService = documentLoaderService;

    }

    Logger logger = LoggerFactory.getLogger(VectorStoreInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        logger.info("Loading documents into VectorStore...");
        documentLoaderService.loadDocument();
        logger.info("Documents loaded successfully.");

    }
}
