module com.sad {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    opens com.sad to javafx.fxml;
    exports com.sad;
}
