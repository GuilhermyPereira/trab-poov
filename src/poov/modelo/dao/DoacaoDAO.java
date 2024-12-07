package poov.modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import poov.modelo.Doacao;
import poov.modelo.Doador;
import poov.modelo.Situacao;

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

    public Doacao buscarPorCodigo(long codigo) {
        String sql = "SELECT * FROM doacao WHERE codigo = ?";
        Doacao doacao = new Doacao();
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setLong(1, codigo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
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
                doacao = null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar doação por código: " + e.getMessage());
        }
        return doacao;
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

    public void buscarPorNomeDoador(String nome) {
        String sql = "SELECT * FROM doacao WHERE cod_doador = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            List<Doador> doadorDAO = new DoadorDAO(conexao).buscarPorNome(nome);
            if (doadorDAO.isEmpty()) {
                System.out.println("Doador não encontrado");
            } else {
                if (doadorDAO.size() == 1) {
                    pstmt.setLong(1, doadorDAO.get(0).getCodigo());
                } else {
                    System.out.println("Mais de um doador encontrado");
                    System.out.println("Escolha um doador:");
                    for (Doador doador : doadorDAO) {
                        System.out.println(doador);
                    }
                    System.out.println("Digite o código do doador:");
                    long codigo = Long.parseLong(System.console().readLine());
                    pstmt.setLong(1, codigo);
                }
                List<Doacao> doacoes = new ArrayList<>();
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Doacao doacao = new Doacao();
                    doacao.setCodigo(rs.getLong("codigo"));
                    doacao.setData(rs.getDate("data").toLocalDate());
                    doacao.setHora(rs.getTime("hora").toLocalTime());
                    doacao.setVolume(rs.getDouble("volume"));
                    doacao.setSituacao(Situacao.valueOf(rs.getString("situacao")));
                    Doador doador = new DoadorDAO(conexao).buscarPorCodigo(rs.getLong("cod_doador"));
                    doacao.setDoador(doador);
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
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar doação por nome do doador: " + e.getMessage());
        }
    }

    public void buscarPorCPFDoador(String cpf) {
        String sql = "SELECT * FROM doacao WHERE cod_doador = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            List<Doador> doadorDAO = new DoadorDAO(conexao).buscaPorCPF(cpf);
            if (doadorDAO.isEmpty()) {
                System.out.println("Doador não encontrado");
            } else {
                if (doadorDAO.size() == 1) {
                    pstmt.setLong(1, doadorDAO.get(0).getCodigo());
                } else {
                    System.out.println("Mais de um doador encontrado");
                    System.out.println("Escolha um doador:");
                    for (Doador doador : doadorDAO) {
                        System.out.println(doador);
                    }
                    System.out.println("Digite o código do doador:");
                    long codigo = Long.parseLong(System.console().readLine());
                    pstmt.setLong(1, codigo);
                }
                List<Doacao> doacoes = new ArrayList<>();
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    Doacao doacao = new Doacao();
                    doacao.setCodigo(rs.getLong("codigo"));
                    doacao.setData(rs.getDate("data").toLocalDate());
                    doacao.setHora(rs.getTime("hora").toLocalTime());
                    doacao.setVolume(rs.getDouble("volume"));
                    doacao.setSituacao(Situacao.valueOf(rs.getString("situacao")));
                    Doador doador = new DoadorDAO(conexao).buscarPorCodigo(rs.getLong("cod_doador"));
                    doacao.setDoador(doador);
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
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar doação por nome do doador: " + e.getMessage());
        }

    }

    public void buscarPorData(LocalDate dataI, LocalDate dataF) {
        String sql = "SELECT * FROM doacao WHERE data BETWEEN ? AND ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(dataI));
            pstmt.setDate(2, java.sql.Date.valueOf(dataF));
            List<Doacao> doacoes = new ArrayList<>();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Doacao doacao = new Doacao();
                doacao.setCodigo(rs.getLong("codigo"));
                doacao.setData(rs.getDate("data").toLocalDate());
                doacao.setHora(rs.getTime("hora").toLocalTime());
                doacao.setVolume(rs.getDouble("volume"));
                doacao.setSituacao(Situacao.valueOf(rs.getString("situacao")));
                Doador doador = new DoadorDAO(conexao).buscarPorCodigo(rs.getLong("cod_doador"));
                doacao.setDoador(doador);
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
            System.out.println("Erro ao buscar doação por data: " + e.getMessage());
        }
    }

    public boolean atualizar(Doacao doacao) {
        String sql = "UPDATE doacao SET data = ?, hora = ?, volume = ?, situacao = ?, cod_doador = ? WHERE codigo = ?";
        try (PreparedStatement pstmt = conexao.prepareStatement(sql)) {
            pstmt.setDate(1, java.sql.Date.valueOf(doacao.getData()));
            pstmt.setTime(2, java.sql.Time.valueOf(doacao.getHora()));
            pstmt.setDouble(3, doacao.getVolume());
            pstmt.setString(4, doacao.getSituacao().name());
            pstmt.setLong(5, doacao.getDoador().getCodigo());
            pstmt.setLong(6, doacao.getCodigo());
            if (doacao.getSituacao() == Situacao.INATIVO) {
                Doador doador = new DoadorDAO(conexao).buscarPorCodigo((doacao.getDoador().getCodigo()));
                if (doador != null) {
                    doador.setSituacao(Situacao.INATIVO);
                    new DoadorDAO(conexao).atualizar(doador);
                }
                
    
            }
            int atualizados = pstmt.executeUpdate();
            if (atualizados == 1) {
                System.out.println("Doação atualizada com sucesso");
                return true;
            } else {
                System.out.println("Doação não atualizada");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar doação: " + e.getMessage());
        }
        return false;
    }
}
