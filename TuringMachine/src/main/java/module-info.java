module viewFx {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.opencsv;

    /*
    opens com.example.altg3turningmachine61692 to javafx.fxml;
    exports com.example.altg3turningmachine61692;*/

    opens viewFx to javafx.fxml;
    exports viewFx;

}