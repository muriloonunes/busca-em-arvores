package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.TreeNode;
import murile.busca_arvores.model.metricas.Metricas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe {@code Tree} representa uma árvore binária de busca que armazena itens e suas frequências.
 *
 * @param <T> Tipo da chave, que deve implementar Comparable para permitir ordenação.
 * @author Hartur Sales
 * @author Murilo Nunes
 */
//TODO talvez extrair pra uma classe abstrata/interface?
public class Tree<T extends Comparable<T>> {
    private TreeNode<T> root;

    public void inserir(T key, Metricas metricas) {
        Objects.requireNonNull(key);
        root = inserirRecursivo(root, key, metricas);
    }

    private TreeNode<T> inserirRecursivo(TreeNode<T> node, T key, Metricas metricas) {
        if (node == null) {
            metricas.addAtribuicoes(1);
            return new TreeNode<>(key);
        }

        int cmp = key.compareTo(node.getKey());
        metricas.addComparacoes(1);

        if (cmp < 0) {
            node.left = inserirRecursivo(node.left, key, metricas);
            metricas.addAtribuicoes(1);
        } else if (cmp > 0) {
            node.right = inserirRecursivo(node.right, key, metricas);
            metricas.addAtribuicoes(1);
        } else {
            node.aumentarFrequencia();
        }
        return node;
    }

    //**
    //     * Insere um item na árvore binária de busca.
    //     *
    //     * @param key item a ser inserido
    //     */
    //    public void inserir(T key) {
    //        Objects.requireNonNull(key);
    //        root = inserirRecursivo(root, key);
    //    }
    //
    //    /**
    //     * Métdo recursivo para inserir uma palavra na árvore.
    //     * Se o item já existir, incrementa sua frequência.
    //     *
    //     * @param node nó atual
    //     * @param key  palavra a ser inserida
    //     * @return nó atualizado
    //     */
    //    private TreeNode<T> inserirRecursivo(TreeNode<T> node, T key) {
    //        if (node == null) {
    //            return new TreeNode<>(key);
    //        }
    //        int cmp = key.compareTo(node.getKey());
    //        if (cmp < 0) {
    //            node.left = inserirRecursivo(node.left, key);
    //        } else if (cmp > 0) {
    //            node.right = inserirRecursivo(node.right, key);
    //        }else {
    //            node.aumentarFrequencia();
    //        }
    //
    //        //TODO: aumentar a frequencia caso haja duplicata (ver implementacao do array)
    //        return node;
    //    }

    /**
     * Retorna uma lista ordenada dos itens (percurso em ordem).
     *
     * @return lista de itens ordenados
     */
    public List<T> emOrdem() {
        List<T> resultado = new ArrayList<>();
        emOrdemRecursivo(root, resultado);
        return resultado;
    }

    /**
     * Métdo recursivo para percurso em ordem.
     *
     * @param node      nó atual
     * @param resultado lista de itens
     */
    private void emOrdemRecursivo(TreeNode<T> node, List<T> resultado) {
        if (node == null) return;
        emOrdemRecursivo(node.left, resultado);
        resultado.add(node.getKey());
        emOrdemRecursivo(node.right, resultado);
    }

    public String toStringFrequencias() {
        StringBuilder sb = new StringBuilder();
        emOrdemRecursivoFrequencias(root, sb);
        return sb.toString();
    }

    private void emOrdemRecursivoFrequencias(TreeNode<T> node, StringBuilder sb) {
        if (node == null) return;

        emOrdemRecursivoFrequencias(node.left, sb);

        sb.append(node.getKey()).append(": ").append(node.getFrequencia()).append("\n");

        emOrdemRecursivoFrequencias(node.right, sb);
    }


    @Override
    public String toString() {
        return emOrdem().toString();
    }
}
