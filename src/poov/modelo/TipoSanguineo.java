package poov.modelo;

public enum TipoSanguineo {

    A("A"),
    B("B"),
    AB("AB"),
    O("O"),
    DESCONHECIDO("Desconhecido");

    private String descricao;

    private TipoSanguineo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
