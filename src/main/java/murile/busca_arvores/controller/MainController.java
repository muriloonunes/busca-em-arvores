package murile.busca_arvores.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import murile.busca_arvores.model.data.node.AVLNode;
import murile.busca_arvores.model.data.node.TreeNode;
import murile.busca_arvores.model.data.structures.AVLTree;
import murile.busca_arvores.model.data.structures.ArrayPalavras;
import murile.busca_arvores.model.data.structures.Tree;
import murile.busca_arvores.model.metricas.EstruturaDeDados;
import murile.busca_arvores.model.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static murile.busca_arvores.model.util.FileUtils.getFileExtension;

public class MainController {
    @FXML
    private Label arquivoSelecionadoLabel;
    @FXML
    private Label selecioneArquivoLabel;
    @FXML
    private TextArea outputArea;
    @FXML
    private TreeView<String> treeView;
    @FXML
    private TreeView<String> avlTreeView;
    private Stage stage;
    private File arquivoSelecionado;
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
            ArrayPalavras<String> arrayPalavras = new ArrayPalavras<>();
            AVLTree<String> avlTree = new AVLTree<>();
            Tree<String> tree = new Tree<>();

            fileUtils.lerArquivo(arquivoSelecionado, arrayPalavras, avlTree, tree);
            exibirResultados(arrayPalavras, avlTree, tree);
        }
    }

    private void exibirResultados(ArrayPalavras<String> arrayPalavras, AVLTree<String> avlTree, Tree<String> tree) {
        outputArea.setText("");
        // Crie uma lista para as métricas
        List<EstruturaDeDados> listaDeEstruturas = new ArrayList<>();
        listaDeEstruturas.add(new EstruturaDeDados("ArrayList", arrayPalavras.getMetricas()));
        listaDeEstruturas.add(new EstruturaDeDados("Árvore AVL", avlTree.getMetricas()));
        listaDeEstruturas.add(new EstruturaDeDados("Árvore Binaria", tree.getMetricas()));

        // Ordene a lista com base no tempo total
        listaDeEstruturas.sort(Comparator.comparingLong(e -> e.getMetricas().getTempoTotalMili()));

        // Construa a string de saída
        StringBuilder resultado = new StringBuilder();
        resultado.append("RESUMO DA EXECUÇÃO\n\n");

        for (EstruturaDeDados estrutura : listaDeEstruturas) {
            resultado.append(estrutura.getNome()).append("\n");
            resultado.append(estrutura.getMetricas().toString()).append("\n\n");
        }

        // Adiciona as frequências das palavras no final
        resultado.append("--------------------\n");
        resultado.append("FREQUÊNCIA DAS PALAVRAS\n\n");
        resultado.append(arrayPalavras);

        TreeItem<String> treeRootItem = construirTreeView(tree.getRoot());
        treeView.setRoot(treeRootItem);

        TreeItem<String> avlRootItem = construirTreeView(avlTree.getRoot());
        avlTreeView.setRoot(avlRootItem);

        outputArea.setText(resultado.toString());
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

    private TreeItem<String> construirTreeView(TreeNode<String> node) {
        if (node == null) {
            return null;
        }

        String texto = node.getKey() + " - Frequência: " + node.getFrequencia();
        TreeItem<String> item = new TreeItem<>(texto);

        TreeItem<String> leftChild = construirTreeView(node.left);
        TreeItem<String> rightChild = construirTreeView(node.right);

        if (leftChild != null) {
            item.getChildren().add(leftChild);
        }
        if (rightChild != null) {
            item.getChildren().add(rightChild);
        }

        return item;
    }

    private TreeItem<String> construirTreeView(AVLNode<String> node) {
        if (node == null) {
            return null;
        }

        String texto = node.getKey() + " - Frequência: " + node.getFrequencia();
        TreeItem<String> item = new TreeItem<>(texto);

        TreeItem<String> leftChild = construirTreeView(node.left);
        TreeItem<String> rightChild = construirTreeView(node.right);

        if (leftChild != null) {
            item.getChildren().add(leftChild);
        }
        if (rightChild != null) {
            item.getChildren().add(rightChild);
        }

        return item;
    }

}