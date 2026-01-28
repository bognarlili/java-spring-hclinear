package com.hcLinear.backendTest.service;

import com.hcLinear.backendTest.exception.NotFoundException;
import com.hcLinear.backendTest.model.TransferDocument;
import com.hcLinear.backendTest.repository.TransferDocumentRepository;


import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class TransferDocumentPdfService {

    private final TransferDocumentRepository repository;

    public TransferDocumentPdfService(TransferDocumentRepository repository) {
        this.repository = repository;
    }

    public byte[] generatePdf(Long documentId) {
        TransferDocument doc = repository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("Transfer document not found: " + documentId));

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Document pdf = new Document();
            PdfWriter.getInstance(pdf, out);
            pdf.open();

            pdf.add(new Paragraph("ÁTIGAZOLÁSI DOKUMENTUM"));
            pdf.add(new Paragraph(" "));

            pdf.add(new Paragraph("Játékos:"));
            pdf.add(new Paragraph("Név: " +
                    doc.getPlayer().getFirstName() + " " + doc.getPlayer().getLastName()));
            pdf.add(new Paragraph(" "));

            if (doc.getFromTeam() != null) {
                pdf.add(new Paragraph("Forrás csapat:"));
                pdf.add(new Paragraph("Név: " + doc.getFromTeam().getName()));
                pdf.add(new Paragraph("Város: " + doc.getFromTeam().getCity()));
                pdf.add(new Paragraph(" "));
            }

            pdf.add(new Paragraph("Cél csapat:"));
            pdf.add(new Paragraph("Név: " + doc.getToTeam().getName()));
            pdf.add(new Paragraph("Város: " + doc.getToTeam().getCity()));
            pdf.add(new Paragraph(" "));

            pdf.add(new Paragraph("Átigazolási összeg:"));
            pdf.add(new Paragraph(doc.getPlayer().getMarketValue().toPlainString()));
            pdf.add(new Paragraph(" "));

            pdf.add(new Paragraph("Létrehozás ideje:"));
            pdf.add(new Paragraph(doc.getCreatedAt().toString()));

            pdf.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }
}
