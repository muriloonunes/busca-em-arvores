package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ArrayPalavras<T extends Comparable<T>> {
    public List<Node<T>> palavras;

    public ArrayPalavras() {
        this.palavras = new ArrayList<>();
    }

    public void adicionarPalavra(T palavra) {
        int index = buscaBinaria(palavra, 0, palavras.size() - 1);
        if (index >= 0) {
            //a palavra ja ta na lista, entao incrementamos a frequencia apenas
            palavras.get(index).aumentarFrequencia();
        } else {
            //nao ta lista, calculamos onde inserir
            //inverte a formula do retorno pra inserir, e manter a lista ordenada
            int indexInserir = -index - 1;
            Node<T> novaPalavra = new Node<>(palavra);
            palavras.add(indexInserir, novaPalavra);
        }
    }

    private int buscaBinaria(T novaPalavra, int min, int max) {
        int index = min + ((max - min) / 2);

        if (max < min) {
            //posição de inserção
            return -(min) - 1;
        }

        T palavraExistente = palavras.get(index).getKey();
        //o metodo compareTo retorna 0 se as palavras forem iguais
        //retorna negativo se a palavraExistente vier antes
        //e positivo se vier depois
        int comparacao = palavraExistente.compareTo(novaPalavra);

        if (comparacao == 0) {
            //encontrou de primeira
            return index;
        } else if (comparacao < 0) {
            //se é negativo, a palavra comparada vem ANTES da que queremos adicionar
            //vai pra direita
            return buscaBinaria(novaPalavra, index + 1, max);
        } else {
            //vai pra esquerda
            return buscaBinaria(novaPalavra, min, index - 1);
        }
    }

    @Override
    public String toString() {
        return palavras.toString();
    }
}