module com.sad.gruppo05.drawingapplicationsad {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.sad.gruppo05.drawingapplicationsad to javafx.fxml;
    exports com.sad.gruppo05.drawingapplicationsad;
}