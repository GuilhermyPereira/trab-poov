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
        if(inseridos == 1){
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                doacao.setCodigo(rs.getLong(1));
            }else{
                System.out.println("Não foi possível pegar a chave da doação inserida");
            }
            rs.close();
        } else {
            System.out.println("Não foi possível inserir a doação");
        }

        pstmt.close();
    }
    
}
