module com.endava.tmd.soj.schedulematcher {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.endava.tmd.soj.schedulematcher.client to javafx.fxml, javafx.controls;
    exports com.endava.tmd.soj.schedulematcher.client;
}