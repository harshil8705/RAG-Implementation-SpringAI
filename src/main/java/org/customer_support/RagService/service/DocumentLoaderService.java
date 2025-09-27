package org.customer_support.RagService.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DocumentLoaderService {

    private final VectorStore vectorStore;

    public DocumentLoaderService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> getDocsFromPdf() throws IOException {

        List<Document> allDocs = new ArrayList<>();

        List<String> extractedDocs = List.of(
                        """
                        LG AC Customer Support Guide\s
                        Introduction\s
                        Welcome to the LG Air Conditioner (AC) Customer Support Guide.\s
                        This guide is designed to provide complete, step-by-step instructions for using,\s
                        troubleshooting, and maintaining your LG AC.\s
                        By following this guide, customers can resolve common queries regarding installation,\s
                        operation, and service of their air conditioner efficiently.
                    """,
                """
                        Table of Contents\s
                        1. Getting Started\s
                        2. Operating the AC\s
                        3. Remote Control Functions\s
                        4. Common Features Explained\s
                        5. Troubleshooting\s
                        6. Cleaning and Maintenance\s
                        7. Safety Precautions\s
                        8. Warranty and Service Information\s
                        9. Customer Care Contact Details
                        """,
                """
                        1. Getting Started\s
                        • Unpack your LG AC carefully and ensure that all accessories (remote, batteries,\s
                        manual, installation kit) are included.\s
                        • Installation should always be done by a certified technician. Avoid self
                        installation to prevent damage or performance issues.\s
                        • Place the AC in a location with proper airflow and electrical supply.
                        """,
                """
                        2. Operating the AC\s
                        Steps to start and operate your LG AC:\s
                        1. Connect the AC to a proper power supply.\s
                        2. Turn on the AC using the power button on the unit or the remote control.
                        3. Select the desired mode:\s
                        o Cool\s
                        o Heat\s
                        o Fan\s
                        o Dry\s
                        o Auto\s
                        4. Set the desired temperature using the remote.\s
                        5. Adjust fan speed and airflow direction as required.
                        """,
                """
                        3. Remote Control Functions\s
                        • Power: Turns the unit ON/OFF.\s
                        • Mode: Switch between Cooling, Heating, Fan, Dry, and Auto modes.\s
                        • Fan Speed: Adjust airflow (Low, Medium, High, Auto).\s
                        • Temperature +/-: Increase or decrease the set temperature.\s
                        • Swing: Adjust air direction automatically or manually.\s
                        • Timer: Schedule AC ON/OFF at preferred times.
                        """,
                """
                        4. Common Features Explained\s
                        • Jet Cool: Quickly cools the room to the set temperature.\s
                        • Low Noise Mode: Reduces operational sound for quieter environment.\s
                        • Smart Diagnosis: Detects issues and helps troubleshoot via LG app.\s
                        • Energy Saver Mode: Optimizes electricity consumption.\s
                        • Auto Restart: AC resumes previous settings after a power outage.\s
                        """,
                """
                        5. Troubleshooting\s
                        Problem\s
                        AC not starting\s
                        Solution\s
                        Verify power supply and remote batteries.\s
                        Problem\s
                        Solution\s
                        Weak cooling/heating Check filters, doors/windows, and temperature settings.\s
                        Noise issues\s
                        Water leakage\s
                        Ensure stable installation and remove obstructions.\s
                        Inspect drainage pipe or contact service center.\s
                        Remote not responding Replace batteries or reset the remote.\s
                        """,
                """
                        6. Cleaning and Maintenance\s
                        • Clean air filters every 2–3 weeks to maintain performance.\s
                        • Wipe AC surface with a soft, dry cloth; avoid harsh chemicals.\s
                        • Schedule annual maintenance with an authorized LG technician.
                        """,
                """
                        7. Safety Precautions\s
                        • Do not touch the AC with wet hands.\s
                        • Keep flammable items away from the AC.\s
                        • Do not insert objects into the air vents.\s
                        • Disconnect power before cleaning or servicing.
                        """,
                """
                        8. Warranty and Service Information\s
                        • LG AC comes with a 1-year standard product warranty.\s
                        • The compressor warranty can be up to 10 years depending on the model.\s
                        • Warranty covers manufacturing defects only. Damage caused by misuse,\s
                        unauthorized service, or installation errors is not included.
                        """,
                """
                        9. Customer Care Contact Details\s
                        • Toll-Free: 1800-180-9999\s
                        • Email: support@lge.com\s
                        • Website: www.lg.com/in\s
                        Support is available 24/7 for installation, troubleshooting, and service queries.
                        """
        );

        int pageNumber = 1;
        for (String text : extractedDocs) {
            Document document = Document.builder()
                    .text(text)
                    .metadata(Map.of(
                            "file_name", "LG AC Customer Support Guide",
                            "page_number", String.valueOf(pageNumber)
                    ))
                    .build();

            allDocs.add(document);
            pageNumber++;
        }

        return allDocs;

    }

    public void loadDocument() throws IOException {

        vectorStore.add(getDocsFromPdf());
        System.out.println("All documents loaded into VectorStore.");

    }

}
