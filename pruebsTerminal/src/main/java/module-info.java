module com.example.pruebsterminal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pruebsterminal to javafx.fxml;
    exports com.example.pruebsterminal;
}