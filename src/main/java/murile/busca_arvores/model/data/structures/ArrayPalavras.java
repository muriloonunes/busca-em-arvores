package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.Node;
import murile.busca_arvores.model.metricas.Metricas;

import java.util.ArrayList;
import java.util.List;

public class ArrayPalavras<T extends Comparable<T>> {
    public List<Node<T>> palavras;
    private final Metricas metricas;

    public ArrayPalavras() {
        this.palavras = new ArrayList<>();
        this.metricas = new Metricas();
    }

    public void adicionarPalavra(T palavra) {
        int index = buscaBinaria(palavra, 0, palavras.size() - 1);
        if (index >= 0) {
            palavras.get(index).aumentarFrequencia();
        } else {
            int indexInserir = -index - 1;
            Node<T> novaPalavra = new Node<>(palavra);
            palavras.add(indexInserir, novaPalavra);
            metricas.addAtribuicoes(1); // inserção na lista
        }
    }

    private int buscaBinaria(T novaPalavra, int min, int max) { // usa métricas internas
        if (max < min) {
            return -(min) - 1;
        }

        int index = min + ((max - min) / 2);
        T palavraExistente = palavras.get(index).getKey();

        int comparacao = palavraExistente.compareTo(novaPalavra);
        metricas.addComparacoes(1); // Contando a comparação de chave

        if (comparacao == 0) {
            return index;
        } else if (comparacao < 0) {
            return buscaBinaria(novaPalavra, index + 1, max);
        } else {
            return buscaBinaria(novaPalavra, min, index - 1);
        }
    }

    public Metricas getMetricas() {
        return metricas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<T> node : palavras) {
            sb.append(node.getKey()).append(": ").append(node.getFrequencia()).append("\n");
        }
        return sb.toString();
    }
}