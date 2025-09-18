package murile.busca_arvores.model.data.node;

/**
 * Class Node representa um nó genérico para estruturas binárias.
 * Cada nó armazena uma chave (key) do tipo T e a frequência de ocorrência dessa chave.
 * Utilizada para armazenar palavras e suas frequências no contexto do processamento de textos.
 *
 * @param <T> Tipo da chave, que deve implementar Comparable para permitir ordenação.
 * @author Murilo Nunes
 */
public class Node<T extends Comparable<T>> {
    private final T key;
    private int frequencia;

    public Node(T key) {
        this.key = key;
        this.frequencia = 1;
    }

    @Override
    public String toString() {
        return "Palavra: " + this.key + " - "
                + "Frequência: " + this.frequencia + "\n";
    }

    public void aumentarFrequencia() {
        this.frequencia++;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public T getKey() {
        return key;
    }
}