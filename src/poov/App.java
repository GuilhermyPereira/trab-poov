package poov;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import poov.modelo.dao.DAOFactory;
import poov.modelo.dao.DoadorDAO;
import poov.modelo.dao.DoacaoDAO;
import poov.modelo.Doacao;
import poov.modelo.Doador;
import poov.modelo.RH;
import poov.modelo.Situacao;
import poov.modelo.TipoSanguineo;

public class App {
    public static void main(String[] args) throws Exception {
        int opcaoMenuPrincipal;
        Scanner scanner = new Scanner(System.in);
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
                    menudoador();
                    break;
                case 2:
                    opcdoacao();
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

    public static final void menudoador() {
        int opcaoDoador;
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
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
                    cadastrardoador();
                    break;
                case 2:
                    opcpesquisar();
                    break;
                case 3:
                    alterardoador();
                    break;
                case 4:
                    excluirDoador();
                    break;
                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (opcaoDoador != 5);
    }

    public static final void cadastrardoador() {
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
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

    }

    public static final List<Doador> opcpesquisar() {
        int opcaoPesquisar;
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
        List<Doador> doadores = new ArrayList<>();
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
                        doadores.add(doador);
                        if (doador != null) {
                            System.out.println("Doador encontrado: " + doador);
                            return doadores;
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
                        doadores = dao.buscarPorNome(nome_p);
                        if (!doadores.isEmpty()) {
                            System.out.println(doadores);
                            return doadores;
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
                        doadores = dao.buscaPorCPF(CPF);
                        if (!doadores.isEmpty()) {
                            System.out.println(doadores);
                            return doadores;
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

        return doadores;

    }

    public static void alterardoador() {
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
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
            } else {
                System.out.println("Doador não encontrado");
            }

        } catch (SQLException ex) {
            DAOFactory.mostrarSQLException(ex);
        } finally {
            factory.fecharConexao();
        }
    }

    public static void excluirDoador() {
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
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
    }

    public static void opcdoacao() {
        int opcaoDoacao;
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("\nDoação:");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Pesquisar");
            System.out.println("3 - Alterar");
            System.out.println("4 - Remover");
            System.out.println("5 - Voltar");
            System.out.print("Opção: ");
            opcaoDoacao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha
            switch (opcaoDoacao) {
                case 1:
                    cadastrardoacao();
                    break;
                case 2:
                    pesquisardoacao();

                case 5:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcaoDoacao != 5);
    }

    public static void cadastrardoacao() {
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pesquisa doador p/ doação");
        List<Doador> doadores = opcpesquisar();
        Doador doador = null;
        if (doadores.size() == 0) {
            System.out.println("Doador não encontrado.");
            return;
        } else if (doadores.size() == 1) {
            doador = doadores.get(0);
        } else {
            System.out.println("Digite o código do doador: ");
            long codigo = scanner.nextLong();
            scanner.nextLine();
            doador = null;
            for (Doador d : doadores) {
                if (d.getCodigo() == codigo) {
                    doador = d;
                    break;
                }
            }
            if (doador == null) {
                System.out.println("Doador não encontrado.");
                return;
            }
        }

        if (doador != null && doador.getSituacao() == Situacao.ATIVO) {
            System.out.println("Digite a data da doação (dd/mm/aaaa): ");
            String data = scanner.nextLine();
            System.out.println("Digite a hora da doação (hh:mm): ");
            String hora = scanner.nextLine();
            System.out.println("Digite o volume da doação: ");
            double volume = scanner.nextDouble();
            while (volume <= 0)
            {
                System.out.println("Volume inválido. Digite novamente: ");
                volume = scanner.nextDouble();
            }
            Doacao doacao = new Doacao(
                    LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm")),
                    volume,
                    doador.getSituacao(),
                    doador);
            try {
                factory.abrirConexao();
                DoacaoDAO dao = factory.criarDoacaoDAO();
                dao.cadastrar(doacao);
            } catch (SQLException ex) {
                DAOFactory.mostrarSQLException(ex);
            } finally {
                factory.fecharConexao();
            }
        }

    }

    public static final void pesquisardoacao(){
        int opcpesquisa;
        DAOFactory factory = new DAOFactory();
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.println("\nPesquisar:");
            System.out.println("1 - Pelo código da doação");
            System.out.println("2 - Pelo código do doador");
            System.out.println("3 - Pelo nome do doador");
            System.out.println("4 - Pelo CPF");
            System.out.println("5 - Pela data da doação");
            System.out.println("6 - Voltar");
            System.out.print("Opção: ");
            opcpesquisa = scanner.nextInt();

            switch (opcpesquisa) {
                case 1:
                    
                    break;
            
                default:
                System.out.println("Opção inválida");

            }
        }while (opcpesquisa != 6);
    }
}
