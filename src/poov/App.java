package poov;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.List;
import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoadorDAO;
import poov.modelo.dao.DoacaoDAO;
import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int opcaoMenuPrincipal;
        DAOFactory factory = new DAOFactory();

        do {
            System.out.println("Menu Principal:");
            System.out.println("1 - Doador");
            System.out.println("2 - Doação");
            System.out.println("3 - Sair");
            System.out.print("Opção: ");
            opcaoMenuPrincipal = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
            switch (opcaoMenuPrincipal) {
                case 1:
                    int opcaoDoador;
                    do {
                        System.out.println("\nDoador:");
                        System.out.println("1 - Cadastrar");
                        System.out.println("2 - Pesquisar");
                        System.out.println("3 - Alterar");
                        System.out.println("4 - Remover");
                        System.out.println("5 - Voltar");
                        System.out.print("Opção: ");
                        opcaoDoador = scanner.nextInt();
                        scanner.nextLine(); // Consumir a quebra de linha
                        switch (opcaoDoador) {
                            case 1:

                                System.out.println("Digite o nome do doador: ");
                                String nome = scanner.nextLine();
                                System.out.println("Digite o CPF do doador: ");
                                String cpf = scanner.nextLine();
                                System.out.println("Digite o contato do doador: ");
                                String contato = scanner.nextLine();
                                Doador d = new Doador(nome, cpf, contato);
                                try {
                                    factory.abrirConexao();
                                    DoadorDAO dao = factory.criarDoadorDAO();
                                    dao.cadastrar(d);
                                } catch (SQLException ex) {
                                    DAOFactory.mostrarSQLException(ex);
                                } finally {
                                    factory.fecharConexao();
                                }

                                break;
                            case 2:
                                int opcaoPesquisar;
                                do {
                                    System.out.println("\nPesquisar:");
                                    System.out.println("1 - Pelo código");
                                    System.out.println("2 - Pelo nome");
                                    System.out.println("3 - Pelo CPF");
                                    System.out.println("4 - Voltar");
                                    System.out.print("Opção: ");
                                    opcaoPesquisar = scanner.nextInt();
                                    scanner.nextLine(); // Consumir a quebra de linha
                                    switch (opcaoPesquisar) {
                                        case 1:
                                            try {
                                                factory.abrirConexao();
                                                DoadorDAO dao = factory.criarDoadorDAO();
                                                System.out.println("Digite o código:");
                                                long codigo = scanner.nextLong();
                                                Doador doador = dao.buscarPorCodigo(codigo);
                                                if (doador != null) {
                                                    System.out.println("Doador encontrado: " + doador);
                                                } else {
                                                    System.out.println("Doador não encontrado.");
                                                }

                                            } catch (SQLException ex) {
                                                DAOFactory.mostrarSQLException(ex);
                                            } finally {
                                                factory.fecharConexao();
                                            }
                                            break;
                                        case 2:
                                            try {
                                                factory.abrirConexao();
                                                DoadorDAO dao = factory.criarDoadorDAO();
                                                System.out.println("Digite o nome (ou parte):");
                                                String nome_p = scanner.nextLine();
                                                List<Doador> doadores = dao.buscarPorNome(nome_p);
                                                if (!doadores.isEmpty()) {
                                                    System.out.println(doadores);
                                                } else {
                                                    System.out.println("Não existe pessoas com esse nome");
                                                }

                                            } catch (SQLException ex) {
                                                DAOFactory.mostrarSQLException(ex);
                                            } finally {
                                                factory.fecharConexao();
                                            }
                                            break;
                                        case 3:
                                            try {
                                                factory.abrirConexao();
                                                DoadorDAO dao = factory.criarDoadorDAO();
                                                System.out.println("Digite o CPF (ou parte):");
                                                String CPF = scanner.nextLine();
                                                List<Doador> doadores = dao.buscaPorCPF(CPF);
                                                if (!doadores.isEmpty()) {
                                                    System.out.println(doadores);
                                                } else {
                                                    System.out.println("Não existem pessoas com esse CPF");
                                                }
                                            } catch (SQLException ex) {
                                                DAOFactory.mostrarSQLException(ex);
                                            } finally {
                                                factory.fecharConexao();
                                            }
                                            break;
                                        case 4:
                                            System.out.println("Voltando ao menu anterior...");
                                            break;
                                        default:
                                            System.out.println("Opção inválida. Tente novamente.");
                                            break;
                                    }
                                } while (opcaoPesquisar != 4);
                                break;
                            case 3:
                                try {
                                    factory.abrirConexao();
                                    DoadorDAO dao = factory.criarDoadorDAO();
                                    System.out.println("Digite o codigo do doador a ser alterado:");
                                    Long codigo = scanner.nextLong();
                                    scanner.nextLine();
                                    Doador doador = dao.buscarPorCodigo(codigo);
                                    if (doador != null) {
                                        System.out.println("Você tem certeza que quer alterar o doador?");
                                        System.out.println(doador);
                                        System.out.println("Digite S para alterar ou N para não alterar:");
                                        String resposta = scanner.nextLine();
                                        if (resposta.equalsIgnoreCase("s")) {
                                            String opcao;
                                            do {
                                                System.out.println("Alterar");
                                                System.out.println("1 - Nome");
                                                System.out.println("2 - CPF");
                                                System.out.println("3 - Contato");
                                                System.out.println("4 - Tipo Sanguíneo");
                                                System.out.println("5 - RH");
                                                System.out.println("6 - Situação");
                                                System.out.println("7 - Mudar Status Tipo e RH Corretos");
                                                System.out.println("8 - Terminar");
                                                System.out.print("Opção: ");
                                                opcao = scanner.nextLine();
                                                switch (opcao) {
                                                    case "1":
                                                        System.out.print("Digite o novo nome: ");
                                                        doador.setNome(scanner.nextLine());
                                                        break;
                                                    case "2":
                                                        System.out.print("Digite o novo CPF: ");
                                                        doador.setCpf(scanner.nextLine());
                                                        break;
                                                    case "3":
                                                        System.out.print("Digite o novo contato: ");
                                                        doador.setContato(scanner.nextLine());
                                                        break;
                                                    case "4":
                                                        System.out.print("Digite o novo tipo sanguíneo: ");
                                                        String tipo = scanner.nextLine();
                                                        if (!tipo.equalsIgnoreCase("O") && !tipo.equalsIgnoreCase("A")
                                                            && !tipo.equalsIgnoreCase("AB")
                                                                && !tipo.equalsIgnoreCase("B")) {
                                                            System.out.println("Tipo sanguíneo inválido");
                                                        } else {
                                                            doador.setTipoSanguineo(
                                                                    TipoSanguineo.valueOf(tipo.toUpperCase()));
                                                        }
                                                        break;
                                                    case "5":
                                                        System.out.print("Digite o novo RH: ");
                                                        String rh = scanner.nextLine();
                                                        if (!rh.equalsIgnoreCase("positivo")
                                                                && !rh.equalsIgnoreCase("negativo")) {
                                                            System.out.println("RH inválido");
                                                        } else {
                                                            doador.setRh(RH.valueOf(rh.toUpperCase()));
                                                        }
                                                        break;
                                                    case "6": 
                                                        System.out.print("Digite a nova situação: ");
                                                        String situacao = scanner.nextLine();
                                                        if (!situacao.equalsIgnoreCase("ativo")
                                                                && !situacao.equalsIgnoreCase("inativo")) {
                                                            System.out.println("Situação inválida");
                                                        } else {
                                                            doador.setSituacao(
                                                                    Situacao.valueOf(situacao.toUpperCase()));
                                                        }
                                                        break;
                                                    case "7":
                                                        doador.setTipoERHCorretos(!doador.isTipoERHCorretos());
                                                        break;
                                                    case "8":
                                                        break;
                                                    default:
                                                        System.out.println("Opção inválida");
                                                }
                                            } while (!opcao.equals("8"));
                                            if (dao.atualizar(doador)) {
                                                System.out.println("Alteração efetuada com sucesso");
                                            } else {
                                                System.out.println("Problema ao alterar o doador");
                                            }
                                        }
                                    }else{
                                        System.out.println("Doador não encontrado");
                                    }

                                } catch (SQLException ex) {
                                    DAOFactory.mostrarSQLException(ex);
                                } finally {
                                    factory.fecharConexao();
                                }
                                break;
                            case 4:
                            try {
                                factory.abrirConexao();
                                DoadorDAO dao = factory.criarDoadorDAO();
                                System.out.println("Digite codigo do doador que será excluido:");
                                long codigo = scanner.nextLong();
                                Doador doador = dao.buscarPorCodigo(codigo);
                                if (doador != null) {
                                    System.out.println("Você tem certeza que quer excluir o doador?");
                                    System.out.println(doador);
                                    System.out.println("Digite S para excluir ou N para não excluir:");
                                    String resposta = scanner.nextLine();
                                    resposta = scanner.nextLine();
                                    if (resposta.equalsIgnoreCase("s")) {
                                        if (dao.remover(doador)) {
                                            System.out.println("Doador excluído com sucesso");
                                        } else {
                                            System.out.println("Problema ao excluir o doador");
                                        }
                                    }
                                } else {
                                    System.out.println("Doador não encontrado");
                                }
                            } catch (SQLException ex) {
                                DAOFactory.mostrarSQLException(ex);
                            } finally {
                                factory.fecharConexao();
                            }
                                break;
                            case 5:
                                System.out.println("Voltando ao menu principal...");
                                break;
                            default:
                                System.out.println("Opção inválida. Tente novamente.");
                                break;
                        }
                    } while (opcaoDoador != 5);
                    break;

                case 2:
                    System.out.println("Menu de Doação...");
                    break;

                case 3:
                    System.out.println("Saindo do programa...");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcaoMenuPrincipal != 3);

        scanner.close();

    }
}
