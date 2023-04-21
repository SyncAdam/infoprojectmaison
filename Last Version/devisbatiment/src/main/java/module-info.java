module insa {
    requires javafx.controls;
    requires javafx.fxml;

    opens insa to javafx.fxml;
    exports insa;
}
