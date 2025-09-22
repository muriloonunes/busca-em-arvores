package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.TreeNode;
import murile.busca_arvores.model.metricas.Metricas;

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
    private final Metricas metricas = new Metricas();

    public void inserir(T key) {
        Objects.requireNonNull(key);
        root = inserirRecursivo(root, key);
    }

    private TreeNode<T> inserirRecursivo(TreeNode<T> node, T key) {
        if (node == null) {
            metricas.addAtribuicoes(1);
            return new TreeNode<>(key);
        }

        int cmp = key.compareTo(node.getKey());
        metricas.addComparacoes(1);

        if (cmp < 0) {
            node.left = inserirRecursivo(node.left, key);
            metricas.addAtribuicoes(1);
        } else if (cmp > 0) {
            node.right = inserirRecursivo(node.right, key);
            metricas.addAtribuicoes(1);
        } else {
            node.aumentarFrequencia();
        }
        return node;
    }

    /**
     * Métdo recursivo para percurso em ordem.
     *
     * @param node      nó atual
     * @param sb        StringBuilder para acumular o resultado
     */
    private void emOrdemRecursivo(TreeNode<T> node, StringBuilder sb) {
        if (node == null) return;
        emOrdemRecursivo(node.left, sb);
        sb.append(node.getKey()).append(": ").append(node.getFrequencia()).append("\n");
        emOrdemRecursivo(node.right, sb);
    }

    public Metricas getMetricas() {
        return metricas;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        emOrdemRecursivo(root, sb);
        return sb.toString();
    }
}
