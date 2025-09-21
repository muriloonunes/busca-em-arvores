package murile.busca_arvores.model.data.structures;

import java.util.List;

/**
 * Project: busca-em-arvores
 * Package: murile.busca_arvores.model.data.structures
 * <p>
 * User: MegaD
 * Email: davylopes866@gmail.com
 * Date: 21/09/2025
 * Time: 14:00
 * <p>
 */
public interface BinarySearchTree <T extends Comparable<T>> {
    void inserir(T key);
    boolean contem(T key);
    int getFrequency(T key);
    List<T> emOrdem();
}
