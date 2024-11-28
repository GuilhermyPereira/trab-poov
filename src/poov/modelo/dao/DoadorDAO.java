package poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;


public class DoadorDAO {

    private final Connection conexao;

    public DoadorDAO(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrar(Doador doador) throws SQLException {
        String sql = "INSERT INTO doador(nome, cpf, contato, tipoerhcorretos, rh, tiposanguineo, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, doador.getNome());
        pstmt.setString(2, doador.getCpf());
        pstmt.setString(3, doador.getContato());
        pstmt.setBoolean(4, doador.isTipoERHCorretos());
        pstmt.setString(5, doador.getRh().name());
        pstmt.setString(6, doador.getTipoSanguineo().name());
        pstmt.setString(7, doador.getSituacao().name());
        int inseridos = pstmt.executeUpdate();
        if(inseridos == 1){
            ResultSet rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                doador.setCodigo(rs.getLong(1));
            }else{
                System.out.println("Não foi possível pegar a chave do doador inserido");
            }
            rs.close();
        } else {
            System.out.println("Não foi possível inserir o doador");
        }

        pstmt.close();
    }

    public List<Doador> buscarPorNome(String nome){
        List<Doador> doadores = new ArrayList<>();
        Doador doador;
        String sql = "SELECT * FROM doador WHERE nome ILIKE ? AND situacao = 'ATIVO'";
        try(PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, "%" + nome + "%");
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    doador = new Doador(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), RH.valueOf(rs.getString(6)), TipoSanguineo.valueOf(rs.getString(7)), Situacao.valueOf(rs.getString(8)));
                    doadores.add(doador);
                }
            }
        } catch (SQLException ex){
            System.out.println("Erro ao buscar doador por nome: " + ex.getMessage());
        }
        return doadores;
    }

    public Doador buscarPorCodigo(long codigo){
        Doador doador = null;
        String sql = "SELECT * FROM doador WHERE codigo = ?";
        try(PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setLong(1, codigo);
            try(ResultSet rs = pstmt.executeQuery()){
                if(rs.next()){
                    doador = new Doador(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), RH.valueOf(rs.getString(6)), TipoSanguineo.valueOf(rs.getString(7)), Situacao.valueOf(rs.getString(8)));
                }
            }
        } catch (SQLException ex){
            System.out.println("Erro ao buscar doador por código: " + ex.getMessage());
        }
        return doador;
    }



    public List<Doador> buscaPorCPF (String cpf){
        List<Doador> doadores = new ArrayList<>();
        Doador doador;
        String sql = "SELECT * FROM doador WHERE cpf ILIKE ? AND situacao = 'ATIVO'";
        try(PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setString(1, "%" + cpf + "%");
            try(ResultSet rs = pstmt.executeQuery()){
                while(rs.next()){
                    doador = new Doador(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBoolean(5), RH.valueOf(rs.getString(6)), TipoSanguineo.valueOf(rs.getString(7)), Situacao.valueOf(rs.getString(8)));
                    doadores.add(doador);
                }
            }
        } catch (SQLException ex){
            System.out.println("Erro ao buscar doador por CPF: " + ex.getMessage());
        }
        return doadores;
    }

    public boolean remover (Doador doador){
        boolean doadorRemovido = false;
        String sql = "UPDATE doador SET situacao = 'INATIVO' WHERE codigo = ?";
        try(PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setLong(1, doador.getCodigo());
            int alteradas = pstmt.executeUpdate();
            if(alteradas == 1){
                doadorRemovido = true;
            }
        } catch (SQLException ex){
            System.out.println("Erro ao remover doador: " + ex.getMessage());
        }
        return doadorRemovido;
    }

    public boolean atualizar (Doador doador){
        boolean doadorAtualizado = false;
        String sql = "UPDATE doador SET tipoERHCorretos = ?, rh = ?, tipoSanguineo = ?, nome = ?, contato = ?, cpf = ?, situacao = ? WHERE codigo = ?";
        try(PreparedStatement pstmt = conexao.prepareStatement(sql)){
            pstmt.setBoolean(1, doador.isTipoERHCorretos());
            pstmt.setString(2, doador.getRh().name());
            pstmt.setString(3, doador.getTipoSanguineo().name());
            pstmt.setString(4, doador.getNome());
            pstmt.setString(5, doador.getContato());
            pstmt.setString(6, doador.getCpf());
            pstmt.setString(7, doador.getSituacao().name());
            pstmt.setLong(8, doador.getCodigo());
            int alteradas = pstmt.executeUpdate();
            if(alteradas == 1){
                doadorAtualizado = true;
            }
        } catch (SQLException ex){
            System.out.println("Erro ao atualizar doador: " + ex.getMessage());
        }
        return doadorAtualizado;
    }


}