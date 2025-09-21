package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.Node;
import murile.busca_arvores.model.metricas.Metricas;

import java.util.ArrayList;
import java.util.List;

public class ArrayPalavras<T extends Comparable<T>> {
    public List<Node<T>> palavras;

    public ArrayPalavras() {
        this.palavras = new ArrayList<>();
    }

    public void adicionarPalavra(T palavra, Metricas metricas) {
        int index = buscaBinaria(palavra, 0, palavras.size() - 1, metricas); // Passe as métricas aqui
        if (index >= 0) {
            palavras.get(index).aumentarFrequencia();
        } else {
            int indexInserir = -index - 1;
            Node<T> novaPalavra = new Node<>(palavra);
            metricas.addAtribuicoes(1); // Nova atribuição (criação do nó)
            palavras.add(indexInserir, novaPalavra);
            metricas.addAtribuicoes(1); // Nova atribuição (inserção na lista)
        }
    }

    //public void adicionarPalavra(T palavra) {
    //        int index = buscaBinaria(palavra, 0, palavras.size() - 1);
    //        if (index >= 0) {
    //            //a palavra ja ta na lista, entao incrementamos a frequencia apenas
    //            palavras.get(index).aumentarFrequencia();
    //        } else {
    //            //nao ta lista, calculamos onde inserir
    //            //inverte a formula do retorno pra inserir, e manter a lista ordenada
    //            int indexInserir = -index - 1;
    //            Node<T> novaPalavra = new Node<>(palavra);
    //            palavras.add(indexInserir, novaPalavra);
    //        }
    //    }

    private int buscaBinaria(T novaPalavra, int min, int max, Metricas metricas) { // Receba as métricas
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
            return buscaBinaria(novaPalavra, index + 1, max, metricas);
        } else {
            return buscaBinaria(novaPalavra, min, index - 1, metricas);
        }
    }

    //private int buscaBinaria(T novaPalavra, int min, int max) {
    //        int index = min + ((max - min) / 2);
    //
    //        if (max < min) {
    //            //posição de inserção
    //            return -(min) - 1;
    //        }
    //
    //        T palavraExistente = palavras.get(index).getKey();
    //        //o metodo compareTo retorna 0 se as palavras forem iguais
    //        //retorna negativo se a palavraExistente vier antes
    //        //e positivo se vier depois
    //        int comparacao = palavraExistente.compareTo(novaPalavra);
    //
    //        if (comparacao == 0) {
    //            //encontrou de primeira
    //            return index;
    //        } else if (comparacao < 0) {
    //            //se é negativo, a palavra comparada vem ANTES da que queremos adicionar
    //            //vai pra direita
    //            return buscaBinaria(novaPalavra, index + 1, max);
    //        } else {
    //            //vai pra esquerda
    //            return buscaBinaria(novaPalavra, min, index - 1);
    //        }
    //    }

    public String toStringFrequencias() {
        StringBuilder sb = new StringBuilder();
        for (Node<T> node : palavras) {
            sb.append(node.getKey()).append(": ").append(node.getFrequencia()).append("\n");
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return palavras.toString();
    }
}