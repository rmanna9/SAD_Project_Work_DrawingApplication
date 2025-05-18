module com.sad {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sad to javafx.fxml;
    exports com.sad;
}
