package murile.busca_arvores.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import murile.busca_arvores.model.data.structures.AVLTree;
import murile.busca_arvores.model.data.structures.Tree;
import murile.busca_arvores.model.data.structures.ArrayPalavras;
import murile.busca_arvores.model.util.FileUtils;

import java.io.File;

import static murile.busca_arvores.model.util.FileUtils.getFileExtension;

public class MainController {
    @FXML
    private Label arquivoSelecionadoLabel;
    @FXML
    private Label selecioneArquivoLabel;
    @FXML
    private TextArea outputArea;
    private Stage stage;
    private File arquivoSelecionado;

    private ArrayPalavras<String> arrayPalavras = new ArrayPalavras<>();
    private AVLTree<String> avlTree = new AVLTree<>();
    private Tree<String> tree = new Tree<>();

    FileUtils fileUtils = new FileUtils();

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onChooseFile() {
        limparLabels();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        arquivoSelecionado = fileChooser.showOpenDialog(stage);
        if (arquivoSelecionado != null) {
            System.out.println(arquivoSelecionado.getName());
            definirArquivo();
        }
    }

    @FXML
    private void onProcessFile() {
        if (arquivoSelecionado == null) {
            arquivoSelecionadoLabel.setStyle("-fx-text-fill: red");
        } else if (!getFileExtension(arquivoSelecionado).equals("txt")) {
            selecioneArquivoLabel.setStyle("-fx-text-fill: red");
        } else {
            fileUtils.lerArquivo(arquivoSelecionado, arrayPalavras, avlTree, tree);
            exibirResultados();
        }
    }

    private void exibirResultados() {
       outputArea.setText(arrayPalavras.toString());
    }

    private void definirArquivo() {
        arquivoSelecionadoLabel.setText(arquivoSelecionado.getName());
        arquivoSelecionadoLabel.setStyle("-fx-font-weight: bold");
    }

    private void limparLabels() {
        arquivoSelecionadoLabel.setText("Nenhum arquivo selecionado");
        arquivoSelecionadoLabel.setStyle("");
        selecioneArquivoLabel.setStyle("");
    }

//    long inicioTempoAVL = System.currentTimeMillis();
//    long inicioTempoASB = System.currentTimeMillis();

//    long tempoAVL = inicioTempoAVL - System.currentTimeMillis();
//    long tempoASB = inicioTempoASB - System.currentTimeMillis();

}