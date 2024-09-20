module com.example.cityofparisroutefinder {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cityofparisroutefinder to javafx.fxml;
    exports com.example.cityofparisroutefinder;
}