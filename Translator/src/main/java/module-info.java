module com.example.translator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
     requires com.google.gson;

    opens com.example.translator to javafx.fxml, com.google.gson;

    exports com.example.translator;
}
