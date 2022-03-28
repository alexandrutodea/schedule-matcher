module com.endava.tmd.soj.schedulematcher {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.endava.tmd.soj.schedulematcher.ui to javafx.fxml;
    exports com.endava.tmd.soj.schedulematcher.ui;
}