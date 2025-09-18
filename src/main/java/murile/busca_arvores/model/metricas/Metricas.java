package murile.busca_arvores.model.metricas;

public class Metricas {
    private long tempoTotalMili;
    private int comparacoes;
    private long atribuicoes;

    public Metricas() {
        this.tempoTotalMili = 0;
        this.comparacoes = 0;
        this.atribuicoes = 0;
    }

    public void addTempo(long tempo) {
        this.tempoTotalMili += tempo;
    }

    public void addComparacoes(long comparacoes) {
        this.comparacoes += comparacoes;
    }

    public void addAtribuicoes(long atribuicoes) {
        this.atribuicoes += atribuicoes;
    }


    public void setComparacoes(int comparacoes) {
        this.comparacoes = comparacoes;
    }

    public void setTempoTotalMili(long tempoTotalMili) {
        this.tempoTotalMili = tempoTotalMili;
    }

    public void setAtribuicoes(long atribuicoes) {
        this.atribuicoes = atribuicoes;
    }

    public long getTempoTotalMili() {
        return tempoTotalMili;
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public long getAtribuicoes() {
        return atribuicoes;
    }

    @Override
    public String toString() {
        return "Tempo total: " + (double) tempoTotalMili / 1_000_000.0 + " ms\n" +
                "Comparações de chaves: " + comparacoes + "\n" +
                "Atribuições de registros: " + atribuicoes;
    }
}
