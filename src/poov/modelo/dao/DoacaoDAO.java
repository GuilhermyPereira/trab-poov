package poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import poov.modelo.Doacao;
import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;

public class DoacaoDAO {

    private final Connection conexao;

    public DoacaoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrar(Doacao doacao) throws SQLException {
        String sql = "INSERT INTO doacao(data, hora, volume, situacao, cod_doador) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setDate(1, java.sql.Date.valueOf(doacao.getData()));
        pstmt.setTime(2, java.sql.Time.valueOf(doacao.getHora()));
        pstmt.setDouble(3, doacao.getVolume());
        pstmt.setString(4, doacao.getSituacao().name());
        pstmt.setLong(5, doacao.getDoador().getCodigo());
        int inseridos = pstmt.executeUpdate();
        if (inseridos == 1) {
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                doacao.setCodigo(rs.getLong(1));
            } else {
                System.out.println("Não foi possível pegar a chave da doação inserida");
            }
            rs.close();
        } else {
            System.out.println("Não foi possível inserir a doação");
        }

        pstmt.close();
    }

    public void buscarPorCodigo(long codigo) {
        String sql = "SELECT * FROM doacao WHERE codigo = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setLong(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setCodigo(rs.getLong("codigo"));
                doacao.setData(rs.getDate("data").toLocalDate());
                doacao.setHora(rs.getTime("hora").toLocalTime());
                doacao.setVolume(rs.getDouble("volume"));
                doacao.setSituacao(Situacao.valueOf(rs.getString("situacao")));
                Doador doadorDAO = new DoadorDAO(conexao).buscarPorCodigo(rs.getLong("cod_doador"));
                doacao.setDoador(doadorDAO);
                System.out.println(doacao);
            } else {
                System.out.println("Doação não encontrada");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar doação por código: " + e.getMessage());
        }
    }

    public void buscarPorCodigoDoador(long codigo) {
        String sql = "SELECT * FROM doacao WHERE cod_doador = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setLong(1, codigo);
            List<Doacao> doacoes = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setCodigo(rs.getLong("codigo"));
                doacao.setData(rs.getDate("data").toLocalDate());
                doacao.setHora(rs.getTime("hora").toLocalTime());
                doacao.setVolume(rs.getDouble("volume"));
                doacao.setSituacao(Situacao.valueOf(rs.getString("situacao")));
                Doador doadorDAO = new DoadorDAO(conexao).buscarPorCodigo(rs.getLong("cod_doador"));
                doacao.setDoador(doadorDAO);
                System.out.println(doacao);
                doacoes.add(doacao);
            }
            if (doacoes.isEmpty()) {
                System.out.println("Doações não encontradas");
            } else {
                for (Doacao doacao : doacoes) {
                    System.out.println(doacao);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar doação por código do doador: " + e.getMessage());
        }

    }
}
