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

    //**
    //     * Insere um item na árvore AVL.
    //     *
    //     * @param key item a ser inserido
    //     */
    //    public void inserir(T key) {
    //        Objects.requireNonNull(key);
    //        root = inserirRecursivo(root, key);
    //    }
    //
    //    /**
    //     * Métdo recursivo para inserir um item na árvore AVL.
    //     * Se o item já existir, incrementa sua frequência.
    //     *
    //     * @param node nó atual
    //     * @param key  item a ser inserido
    //     * @return nó atualizado
    //     */
    //    private AVLNode<T> inserirRecursivo(AVLNode<T> node, T key) {
    //        if (node == null) {
    //            return new AVLNode<>(key);
    //        }
    //
    //        System.out.println("Comparando (key): '" + key + "' com (node.getKey()): '" + node.getKey() + "'");
    //
    //        int cmp = key.compareTo(node.getKey());
    //        if (cmp < 0) {
    //            node.left = inserirRecursivo(node.left, key);
    //        } else if (cmp > 0) {
    //            node.right = inserirRecursivo(node.right, key);
    //        } else {
    //            node.incrementFrequency(1);
    //            return node; // duplicata: ignorar <- TODO: aumentar a frequencia caso haja duplicata (ver implementacao do array)
    //        }
    //
    //        atualizarAltura(node);
    //        return rebalancar(node);
    //    }

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

        // Atualiza as alturas
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

    //private AVLNode<T> rotacaoDireita(AVLNode<T> y) {
    //        AVLNode<T> x = y.left;
    //        AVLNode<T> T2 = x.right;
    //
    //        x.right = y;
    //        y.left = T2;
    //
    //        atualizarAltura(y);
    //        atualizarAltura(x);
    //
    //        return x;
    //    }
    //
    //    private AVLNode<T> rotacaoEsquerda(AVLNode<T> x) {
    //        AVLNode<T> y = x.right;
    //        AVLNode<T> T2 = y.left;
    //
    //        y.left = x;
    //        x.right = T2;
    //
    //        atualizarAltura(x);
    //        atualizarAltura(y);
    //
    //        return y;
    //    }
    //
    //    private AVLNode<T> rebalancar(AVLNode<T> node) {
    //        int balanceamento = fatorBalanceamento(node);
    //
    //        // Caso à esquerda
    //        if (balanceamento > 1) {
    //            if (fatorBalanceamento(node.left) < 0) {
    //                node.left = rotacaoEsquerda(node.left);
    //            }
    //            return rotacaoDireita(node);
    //        }
    //
    //        // Caso à direita
    //        if (balanceamento < -1) {
    //            if (fatorBalanceamento(node.right) > 0) {
    //                node.right = rotacaoDireita(node.right);
    //            }
    //            return rotacaoEsquerda(node);
    //        }
    //
    //        return node; // já balanceado
    //    }

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

    /**
     * Metodo de debug - remover depois
     */
    public void imprimirArvore() {
        imprimirArvoreRecursiva(root, "", true);
    }

    private void imprimirArvoreRecursiva(AVLNode<T> node, String prefixo, boolean ehUltimo) {
        if (node != null) {
            System.out.println(prefixo + (ehUltimo ? "└── " : "├── ") + node.getKey());

            String novoPrefixo = prefixo + (ehUltimo ? "    " : "│   ");

            boolean temFilhoEsquerdo = node.left != null;
            boolean temFilhoDireito = node.right != null;

            if (temFilhoEsquerdo || temFilhoDireito) {
                imprimirArvoreRecursiva(node.left, novoPrefixo, !temFilhoDireito);
                imprimirArvoreRecursiva(node.right, novoPrefixo, true);
            }
        }
    }
}
