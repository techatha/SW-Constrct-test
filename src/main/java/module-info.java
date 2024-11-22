module se233.finalProjectII {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;

    opens se233.finalProjectII to javafx.fxml;
    exports se233.finalProjectII;
}