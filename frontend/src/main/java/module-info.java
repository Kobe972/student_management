module com.weichen.stumanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.weichen.stumanager to javafx.fxml;
    exports com.weichen.stumanager;
}