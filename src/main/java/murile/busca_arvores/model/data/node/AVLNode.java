package murile.busca_arvores.model.data.node;

/**
 * AVLNode representa um nó de árvore AVL, utilizada para armazenar palavras e suas frequências.
 * Estende TreeNode, adicionando controle de altura para balanceamento automático da árvore.
 *
 * @author Murilo Nunes
 * @param <T> Tipo da chave, que deve implementar Comparable para permitir ordenação.
 */
public class AVLNode<T extends Comparable<T>> extends TreeNode<T> {
    public AVLNode<T> left, right;
    private int height;

    public AVLNode(T key) {
        super(key);
        this.height = 1;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
