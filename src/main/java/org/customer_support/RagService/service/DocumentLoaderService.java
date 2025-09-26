package org.customer_support.RagService.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentLoaderService {

    private final VectorStore vectorStore;

    public DocumentLoaderService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> getDocsFromPdf() throws IOException {

        List<Document> allDocs = new ArrayList<>();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:/pdfs/*.pdf");


        for (Resource resource : resources){
            PagePdfDocumentReader pdfReader = new PagePdfDocumentReader(
                    (Resource) resource.getInputStream(),
                    PdfDocumentReaderConfig.builder()
                            .withPageTopMargin(0)
                            .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                                    .withNumberOfTopTextLinesToDelete(0)
                                    .build())
                            .withPagesPerDocument(1)
                            .build()
            );
            allDocs.addAll(pdfReader.read());
        }

        return allDocs;

    }

    public void loadDocument() throws IOException {

        vectorStore.add(getDocsFromPdf());

    }

}
