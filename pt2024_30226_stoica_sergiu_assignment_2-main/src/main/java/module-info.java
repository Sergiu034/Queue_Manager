module org.example.pt2024_30226_stoica_sergiu_assignment_2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.pt2024_30226_stoica_sergiu_assignment_2 to javafx.fxml;

    exports org.example.pt2024_30226_stoica_sergiu_assignment_2.View;
    opens org.example.pt2024_30226_stoica_sergiu_assignment_2.View to javafx.fxml;
    exports org.example.pt2024_30226_stoica_sergiu_assignment_2.Controller;
    opens org.example.pt2024_30226_stoica_sergiu_assignment_2.Controller to javafx.fxml;
}