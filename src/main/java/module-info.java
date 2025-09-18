module murile.busca_arvores {
    requires javafx.controls;
    requires javafx.fxml;


    opens murile.busca_arvores to javafx.fxml;
    exports murile.busca_arvores;
    exports murile.busca_arvores.controller;
    opens murile.busca_arvores.controller to javafx.fxml;
}