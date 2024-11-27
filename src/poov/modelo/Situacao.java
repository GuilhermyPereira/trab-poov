package poov.modelo;

public enum Situacao {

    ATIVO("Ativo(a)"),
    INATIVO("Inativo(a)");

    private String descricao;

    private Situacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
