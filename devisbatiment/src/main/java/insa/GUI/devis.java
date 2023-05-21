package insa.GUI;

import java.util.ArrayList;

import java.io.FileOutputStream;
import java.util.Date;

/*
import com.itextpdf.kernel.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.Path;
*/
public class devis {
    /*
    double total = 0;
    ArrayList<String> revs = new ArrayList<String>();
    String Path = "Devis";
    devis(ArrayList<String> revs, double total){
        this.revs = revs;
        this.total = total;

    }

    devis(ArrayList<String> revs, double total, String Path){
        this.revs = revs;
        this.total = total;
        this.Path = Path;

    }

    private static Font TitleFont = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD); //police personalisée
    private static Font BoldFont = new Font(Font.FontFamily.HELVETICA, 11,Font.BOLD); //police personalisée
    Document document = new Document();

    public void generate(){
        try{
        PdfWriter.getInstance(document, new FileOutputStream(Path + ".pdf"));
        document.open();
        document.addTitle("Devis"); //Metadonnées
        document.addSubject("Devis du batiment designé par notre logiciel");
        document.addKeywords("architecture, devis, easter-egg");
        document.addAuthor("Timéo SITRUK, SINKOVICS Adam");

        Paragraph titre = new Paragraph("Devis batiment", TitleFont);
        titre.setAlignment(Element.ALIGN_CENTER);
        Paragraph sousTitre = new Paragraph("Le document qui suit permet de dresser une première estimation des coûts à prévoir lors de la pose des revêtetements du batiment.", new Font(Font.FontFamily.HELVETICA, 11,2));
        Paragraph infos = new Paragraph("Projet info. S2 INSA 2023", new Font(FontFamily.HELVETICA, 9, 2, BaseColor.GRAY));
        infos.setAlignment(Element.ALIGN_RIGHT);
        Paragraph signature = new Paragraph("Devis établi le : " + new Date()+ " par : " + System.getProperty("user.name") );
        signature.setAlignment(Element.ALIGN_RIGHT);

        PdfPTable table = new PdfPTable(3); //création du tableau

   
        BaseColor gris = new BaseColor(228, 228, 228, 255);

        PdfPCell c1 = new PdfPCell(new Phrase("Revêtement", BoldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(gris);
        
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Surface", BoldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(gris);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Côut", BoldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        c1.setBackgroundColor(gris);
        table.addCell(c1);
        //table.setHeaderRows(2);
        
        
        for (int i = 0; i < revs.size(); i++) { // on ajoute le contenu
            if(i%3 == 2 ){table.addCell(revs.get(i) + " €");}
            if(i%3 == 0 ){table.addCell(revs.get(i));}
            if(i%3 == 1 ){table.addCell(revs.get(i) + " m^2");}
            
            
        }
        c1 = new PdfPCell();
        
        c1.setBorderWidth(0);

        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Total : ", BoldFont));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(gris);
        
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(Double.toString(total) + " €"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        c1.setBackgroundColor(gris);
        table.addCell(c1);




        
        document.add(titre);
        addEmptyLine(infos, 1);
        document.add(infos);
        addEmptyLine(sousTitre, 2);
        document.add(sousTitre);
        
        document.add(table);
        document.add(signature);















        document.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    */
}
