module com.sad.gruppo05.drawingapplicationsad {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.swing;


    opens com.sad.gruppo05.drawingapplicationsad to javafx.fxml;
    exports com.sad.gruppo05.drawingapplicationsad;
}