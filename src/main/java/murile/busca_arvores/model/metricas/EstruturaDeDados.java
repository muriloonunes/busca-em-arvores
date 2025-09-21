package murile.busca_arvores.model.metricas;

public class EstruturaDeDados {
    // Crie esta classe em um novo arquivo
    private String nome;
    private Metricas metricas;

    public EstruturaDeDados(String nome, Metricas metricas) {
        this.nome = nome;
        this.metricas = metricas;
    }

    public String getNome() {
        return nome;
    }

    public Metricas getMetricas() {
        return metricas;
    }
}
