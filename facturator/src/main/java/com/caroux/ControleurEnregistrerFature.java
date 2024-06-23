package com.caroux;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ControleurEnregistrerFature implements EventHandler<ActionEvent> {

    private FenetreNouvelleFacture fenetreNouvelleFacture;

    public ControleurEnregistrerFature(FenetreNouvelleFacture fenetreNouvelleFacture) {
        this.fenetreNouvelleFacture = fenetreNouvelleFacture;
    }

    @Override
    public void handle(ActionEvent event) {
        System.out.println("Enregistrer");
        System.out.println(this.fenetreNouvelleFacture.getNomClient());
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la facture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        Stage stage = (Stage) fenetreNouvelleFacture.getScene().getWindow();
        fileChooser.setInitialFileName("facture.pdf");
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                createPdf(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createPdf(String dest) throws IOException {
        // Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        // Initialize PDF document
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // Add fonts
        PdfFont font = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA);
        PdfFont boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);

        // Add title
        document.add(new Paragraph("Facture")
                .setFont(boldFont)
                .setFontSize(24)
                .setMarginBottom(20));

        // Add client information
        document.add(new Paragraph("Client: " + this.fenetreNouvelleFacture.getNomClient())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(10));
        document.add(new Paragraph("Date: " + java.time.LocalDate.now())
                .setFont(font)
                .setFontSize(12)
                .setMarginBottom(20));

        // Add table for products
        float[] columnWidths = {400F, 100F};
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(100));

        // Add table header
        table.addHeaderCell(new Cell().add(new Paragraph("Produit").setFont(boldFont).setFontSize(12)));
        table.addHeaderCell(new Cell().add(new Paragraph("Prix (€)").setFont(boldFont).setFontSize(12)));

        // Add products to table
        List<Produit> produits = fenetreNouvelleFacture.getProduits();
        for (Produit produit : produits) {
            table.addCell(new Cell().add(new Paragraph(produit.getNom()).setFont(font).setFontSize(12)));
            table.addCell(new Cell().add(new Paragraph(String.format("%.2f", produit.getPrix())).setFont(font).setFontSize(12)));
        }

        // Add total row
        table.addCell(new Cell().add(new Paragraph("Total").setFont(boldFont).setFontSize(12)));
        table.addCell(new Cell().add(new Paragraph(String.format("%.2f", produits.stream().mapToDouble(Produit::getPrix).sum())).setFont(boldFont).setFontSize(12)));

        // Add table to document
        document.add(table);

        // Add footer
        document.add(new Paragraph("Merci pour votre achat !")
                .setFont(font)
                .setFontSize(12)
                .setMarginTop(20)
                .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER));

        // Close document
        document.close();

        System.out.println("PDF Created");
    }
}
