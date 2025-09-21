package murile.busca_arvores.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import murile.busca_arvores.model.data.structures.AVLTree;
import murile.busca_arvores.model.data.structures.Tree;
import murile.busca_arvores.model.data.structures.ArrayPalavras;
import murile.busca_arvores.model.metricas.EstruturaDeDados;
import murile.busca_arvores.model.metricas.Metricas;
import murile.busca_arvores.model.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
    private Stage stage;
    private File arquivoSelecionado;

    private ArrayPalavras<String> arrayPalavras = new ArrayPalavras<>();
    private AVLTree<String> avlTree = new AVLTree<>();
    private Tree<String> tree = new Tree<>();

    private Metricas metricasArray = new Metricas();
    private Metricas metricasAVL = new Metricas();
    private Metricas metricasTree = new Metricas();

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
            // Limpe as métricas para uma nova execução
            metricasArray = new Metricas();
            metricasAVL = new Metricas();
            metricasTree = new Metricas();

            fileUtils.lerArquivo(arquivoSelecionado, arrayPalavras, avlTree, tree, metricasArray, metricasAVL, metricasTree);
            exibirResultados();
        }
    }

    //    @FXML
    //    private void onProcessFile() {
    //        if (arquivoSelecionado == null) {
    //            arquivoSelecionadoLabel.setStyle("-fx-text-fill: red");
    //        } else if (!getFileExtension(arquivoSelecionado).equals("txt")) {
    //            selecioneArquivoLabel.setStyle("-fx-text-fill: red");
    //        } else {
    //            fileUtils.lerArquivo(arquivoSelecionado, arrayPalavras, avlTree, tree);
    //            exibirResultados();
    //        }
    //    }

    private void exibirResultados() {
        // Crie uma lista para as métricas
        List<EstruturaDeDados> listaDeEstruturas = new ArrayList<>();
        listaDeEstruturas.add(new EstruturaDeDados("Vetor Dinâmico", metricasArray));
        listaDeEstruturas.add(new EstruturaDeDados("Árvore AVL", metricasAVL));
        listaDeEstruturas.add(new EstruturaDeDados("Árvore de Pesquisa", metricasTree));

        // Ordene a lista com base no tempo total
        listaDeEstruturas.sort(Comparator.comparingLong(e -> e.getMetricas().getTempoTotalMili()));

        // Construa a string de saída
        StringBuilder resultado = new StringBuilder();
        resultado.append("RESUMO DA EXECUÇÃO\n\n");

        for (EstruturaDeDados estrutura : listaDeEstruturas) {
            resultado.append("Estrutura: ").append(estrutura.getNome()).append("\n");
            resultado.append(estrutura.getMetricas().toString()).append("\n\n");
        }

        // Adiciona as frequências das palavras no final
        resultado.append("--------------------\n");
        resultado.append("FREQUÊNCIA DAS PALAVRAS\n\n");

        // Imprime as frequências do Array
        resultado.append("Utilizando o Vetor Dinâmico:\n");
        // Você precisará de um métodoo para formatar a saída do ArrayPalavras
        resultado.append(arrayPalavras.toStringFrequencias());
        resultado.append("\n");

        // Imprime as frequências da Árvore de Pesquisa
        resultado.append("Utilizando a Árvore de Pesquisa:\n");
        resultado.append(tree.toStringFrequencias());
        resultado.append("\n");

        // Imprime as frequências da Árvore AVL
        resultado.append("Utilizando a Árvore AVL:\n");
        resultado.append(avlTree.toStringFrequencias());

        outputArea.setText(resultado.toString());

    }

    //private void exibirResultados() {
    //       outputArea.setText(arrayPalavras.toString());
    //    }

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