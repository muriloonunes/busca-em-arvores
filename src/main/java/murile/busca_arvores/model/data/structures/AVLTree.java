package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.AVLNode;
import murile.busca_arvores.model.metricas.Metricas;

import java.util.Objects;

/**
 * Classe {@code AVLTree} representa uma árvore binária de busca balanceada (AVL) para armazenar itens e suas frequências.
 * Utilizada para indexação e contagem eficiente de itens, garantindo balanceamento automático.
 *
 * @param <T> Tipo da chave, que deve implementar Comparable para permitir ordenação.
 * @author Hartur Sales
 * @author Murilo Nunes
 */
//TODO talvez extrair pra uma classe abstrata/interface?
public class AVLTree<T extends Comparable<T>> {
    private AVLNode<T> root;
    private final Metricas metricas = new Metricas();

    public Metricas getMetricas() {
        return metricas;
    }

    public void inserir(T key) {
        Objects.requireNonNull(key);
        root = inserirRecursivo(root, key);
    }

    private AVLNode<T> inserirRecursivo(AVLNode<T> node, T key) {
        if (node == null) {
            metricas.addAtribuicoes(1);
            return new AVLNode<>(key);
        }

        int cmp = key.compareTo(node.getKey());
        metricas.addComparacoes(1);

        if (cmp < 0) {
            node.left = inserirRecursivo(node.left, key);
        } else if (cmp > 0) {
            node.right = inserirRecursivo(node.right, key);
        } else {
            node.aumentarFrequencia();
            return node;
        }

        atualizarAltura(node);
        return rebalancar(node);
    }

    // ---------- Métodos auxiliares ----------
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
        metricas.addAtribuicoes(2); // Duas atribuições na rotação

        atualizarAltura(y);
        atualizarAltura(x);

        return x;
    }


    private AVLNode<T> rotacaoEsquerda(AVLNode<T> x) {
        AVLNode<T> y = x.right;
        AVLNode<T> T2 = y.left;

        // Realiza a rotação
        y.left = x;
        x.right = T2;
        metricas.addAtribuicoes(2); // Contagem das duas atribuições

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

    /**
     * Realiza o percurso em ordem na árvore AVL, adicionando ao StringBuilder
     * cada chavSe e sua respectiva frequência no formato "chave: frequência".
     *
     * @param node nó atual da árvore
     * @param sb StringBuilder para acumular o resultado
     */
    private void emOrdemRecursivo(AVLNode<T> node, StringBuilder sb) {
        if (node == null) return;
        emOrdemRecursivo(node.left, sb);
        sb.append(node.getKey()).append(": ").append(node.getFrequencia()).append("\n");
        emOrdemRecursivo(node.right, sb);
    }

    public AVLNode<T> getRoot() {
        return root;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        emOrdemRecursivo(root, sb);
        return sb.toString();
    }
}
