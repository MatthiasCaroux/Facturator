package com.caroux;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.util.List;

public class ControleurEnregistrerFature implements EventHandler<ActionEvent> {

    private FenetreNouvelleFacture fenetreNouvelleFacture;

    public ControleurEnregistrerFature(FenetreNouvelleFacture fenetreNouvelleFacture) {
        this.fenetreNouvelleFacture = fenetreNouvelleFacture;
    }

    @Override
    public void handle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer la facture");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        Stage stage = (Stage) fenetreNouvelleFacture.getScene().getWindow();
        fileChooser.setInitialFileName("facture.pdf");
        java.io.File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                createPdf(file.getAbsolutePath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void createPdf(String dest) throws FileNotFoundException {
        // Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        // Initialize PDF document
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // Add title
        document.add(new Paragraph("Facture").setBold().setFontSize(20));

        // Add table
        float[] pointColumnWidths = {200F, 200F};
        Table table = new Table(UnitValue.createPercentArray(pointColumnWidths));
        table.addHeaderCell("Produit");
        table.addHeaderCell("Prix (€)");

        // Add products to table
        List<Produit> produits = fenetreNouvelleFacture.getProduits();
        for (Produit produit : produits) {
            table.addCell(produit.getNom());
            table.addCell(String.format("%.2f", produit.getPrix()));
        }

        // Add total row
        table.addCell(new Paragraph("Total").setBold());
        table.addCell(new Paragraph(String.format("%.2f", produits.stream().mapToDouble(Produit::getPrix).sum())).setBold());

        // Add table to document
        document.add(table);

        // Close document
        document.close();

        System.out.println("PDF Created");
    }
}
