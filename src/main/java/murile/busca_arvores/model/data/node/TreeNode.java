package murile.busca_arvores.model.data.node;

/**
 * TreeNode representa um nó de árvore binária para armazenar palavras e suas frequências.
 * Estende a classe Node e adiciona referências para os filhos esquerdo e direito.
 *
 * @author Murilo Nunes
 * @param <T> Tipo da chave, que deve implementar Comparable para permitir ordenação.
 */
public class TreeNode<T extends Comparable<T>> extends Node<T> {
    public TreeNode<T> left, right;

    public TreeNode(T key) {
        super(key);
    }
}
