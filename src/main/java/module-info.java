module fr.kyo.crkf {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens fr.kyo.crkf to javafx.fxml;
    exports fr.kyo.crkf;
}