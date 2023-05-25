module insa {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires itextpdf;


    opens insa to javafx.fxml;
    exports insa.GUI to javafx.graphics;
    exports insa.Batiment;
    exports insa.Batiment.Revetements;
}
