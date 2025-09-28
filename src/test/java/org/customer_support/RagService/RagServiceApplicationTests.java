package org.customer_support.RagService;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.reader.pdf.ParagraphPdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

@SpringBootTest
class RagServiceApplicationTests {

	@Autowired
	private VectorStore vectorStore;

	@Value("classpath:pdfs/LG AC Customer Support Guide.pdf")
	private Resource customerSupportGuide;

	Logger logger = LoggerFactory.getLogger(VectorStoreInitializer.class);

    @Test
	void contextLoads() {

		var pdfReader = new ParagraphPdfDocumentReader(customerSupportGuide);
		TextSplitter textSplitter = new TokenTextSplitter();

		logger.info("Loading documents into VectorStore...");
		vectorStore.accept(textSplitter.apply(pdfReader.get()));
		logger.info("Documents loaded successfully." + pdfReader.get());

	}

}
