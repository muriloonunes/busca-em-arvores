package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.AVLNode;

import java.util.List;
import java.util.Objects;

public class AVLTree<T extends Comparable<T>> implements BinarySearchTree<T> {
    private AVLNode<T> root;



    private AVLNode<T> inserirRecursivo(AVLNode<T> node, T key) {
        if (node == null) return new AVLNode<>(key);

        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.left = inserirRecursivo(node.left, key);
        } else if (cmp > 0) {
            node.right = inserirRecursivo(node.right, key);
        } else {
            node.incrementFrequency();
            return node;
        }

        atualizarAltura(node);
        return rebalancar(node);
    }


    @Override
    public void inserir(T key) {
        Objects.requireNonNull(key);
        root = inserirRecursivo(root, key);
    }

    @Override
    public boolean contem(T key) {
        Objects.requireNonNull(key);
        AVLNode<T> cur = root;
        while (cur != null) {
            int cmp = key.compareTo(cur.getKey());
            if (cmp == 0) return true;
            cur = (cmp < 0) ? cur.left : cur.right;
        }
        return false;
    }

    @Override
    public int getFrequency(T key) {
        Objects.requireNonNull(key);
        AVLNode<T> cur = root;
        while (cur != null) {
            int cmp = key.compareTo(cur.getKey());
            if (cmp == 0) return cur.getFrequency();
            cur = (cmp < 0) ? cur.left : cur.right;
        }
        return 0;
    }

    @Override
    public List<T> emOrdem() {
        return List.of();
    }

    // ---------- Métodos auxiliares (internos da AVL) ----------
    private int altura(AVLNode<T> n) {
        return n == null ? 0 : n.getHeight();
    }

    private void atualizarAltura(AVLNode<T> n) {
        n.setHeight(1 + Math.max(altura(n.left), altura(n.right)));
    }

    private int fatorBalanceamento(AVLNode<T> n) {
        return n == null ? 0 : altura(n.left) - altura(n.right);
    }

    private AVLNode<T> rotacaoDireita(AVLNode<T> y) {
        AVLNode<T> x = y.left;
        AVLNode<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }

    private AVLNode<T> rotacaoEsquerda(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        atualizarAltura(x);
        atualizarAltura(y);

        return y;
    }

    private AVLNode<T> rebalancar(AVLNode<T> node) {
        int balanceamento = fatorBalanceamento(node);

        if (balanceamento > 1) {
            if (fatorBalanceamento(node.left) < 0) {
                node.left = rotacaoEsquerda(node.left);
            }
            return rotacaoDireita(node);
        }

        if (balanceamento < -1) {
            if (fatorBalanceamento(node.right) > 0) {
                node.right = rotacaoDireita(node.right);
            }
            return rotacaoEsquerda(node);
        }

        return node;
    }

    // Expor raiz só se necessário
    public AVLNode<T> getRoot() {
        return root;
    }
}
