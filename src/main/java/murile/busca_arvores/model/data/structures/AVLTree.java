package murile.busca_arvores.model.data.structures;

import murile.busca_arvores.model.data.node.AVLNode;

import java.util.ArrayList;
import java.util.List;
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

    /**
     * Insere um item na árvore AVL.
     *
     * @param key item a ser inserido
     */
    public void inserir(T key) {
        Objects.requireNonNull(key);
        root = inserirRecursivo(root, key);
    }

    /**
     * Métdo recursivo para inserir um item na árvore AVL.
     * Se o item já existir, incrementa sua frequência.
     *
     * @param node nó atual
     * @param key  item a ser inserido
     * @return nó atualizado
     */
    private AVLNode<T> inserirRecursivo(AVLNode<T> node, T key) {
        if (node == null) {
            return new AVLNode<>(key);
        }

        System.out.println("Comparando (key): '" + key + "' com (node.getKey()): '" + node.getKey() + "'");

        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.left = inserirRecursivo(node.left, key);
        } else if (cmp > 0) {
            node.right = inserirRecursivo(node.right, key);
        } else {
            node.incrementFrequency(1);
            return node; // duplicata: ignorar <- TODO: aumentar a frequencia caso haja duplicata (ver implementacao do array)
        }

        atualizarAltura(node);
        return rebalancar(node);
    }

    /**
     * Verifica se um item existe na árvore AVL.
     *
     * @param key item a ser buscado
     * @return true se existir, false caso contrário
     */
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

        // Caso à esquerda
        if (balanceamento > 1) {
            if (fatorBalanceamento(node.left) < 0) {
                node.left = rotacaoEsquerda(node.left);
            }
            return rotacaoDireita(node);
        }

        // Caso à direita
        if (balanceamento < -1) {
            if (fatorBalanceamento(node.right) > 0) {
                node.right = rotacaoDireita(node.right);
            }
            return rotacaoEsquerda(node);
        }

        return node; // já balanceado
    }

    /**
     * Retorna uma lista ordenada dos itens (percurso em ordem).
     *
     * @return lista de itens ordenados
     */
    public List<T> emOrdem() {
        List<T> res = new ArrayList<>();
        emOrdemRecursivo(root, res);
        return res;
    }

    /**
     * Métdo recursivo para percurso em ordem.
     *
     * @param node      nó atual
     * @param resultado lista de itens
     */
    private void emOrdemRecursivo(AVLNode<T> node, List<T> resultado) {
        if (node == null) return;
        emOrdemRecursivo(node.left, resultado);
        resultado.add(node.getKey());
        emOrdemRecursivo(node.right, resultado);
    }

    @Override
    public String toString() {
        return emOrdem().toString();
    }
}
