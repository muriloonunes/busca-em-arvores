package murile.busca_arvores.model.data.traversal;

import murile.busca_arvores.model.data.node.AVLNode;

/**
 * Project: busca-em-arvores
 * Package: murile.busca_arvores.model.data.traversal
 * <p>
 * User: MegaD
 * Email: davylopes866@gmail.com
 * Date: 21/09/2025
 * Time: 14:04
 * <p>
 */
public class TreeStatistics {
    public static <T extends Comparable<T>> int totalFrequency(AVLNode<T> root) {
        if (root == null) return 0;
        return root.getFrequency()
                + totalFrequency(root.left)
                + totalFrequency(root.right);
    }

    public static <T extends Comparable<T>> int size(AVLNode<T> root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }
}