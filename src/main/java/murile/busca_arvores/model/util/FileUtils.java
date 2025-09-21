package murile.busca_arvores.model.util;

import murile.busca_arvores.model.data.structures.AVLTree;
import murile.busca_arvores.model.data.structures.Tree;
import murile.busca_arvores.model.data.structures.ArrayPalavras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileUtils {
    public static String getFileExtension(File file) {
        String name = file.getName();
        int lastDotIndex = name.lastIndexOf('.');

        if (lastDotIndex > 0 && lastDotIndex < name.length() - 1) {
            return name.substring(lastDotIndex + 1);
        } else {
            return "";
        }
    }

    public void lerArquivo(File arquivoSelecionado, ArrayPalavras<String> arrayPalavras, AVLTree<String> avlTree, Tree<String> tree) {
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoSelecionado))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                for (String palavra : linha.split("\\s+")) {
                    adicionarPalavra(palavra, arrayPalavras, avlTree, tree);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void adicionarPalavra(String palavra, ArrayPalavras<String> arrayPalavras, AVLTree<String> avlTree, Tree<String> tree) {
        String newInput = StringUtils.limparPalavra(palavra);

        if (newInput != null && !newInput.isEmpty()) {
            System.out.println(newInput);
            arrayPalavras.adicionarPalavra(newInput);
            avlTree.inserir(newInput);
            tree.inserir(newInput);
        }
    }
}
