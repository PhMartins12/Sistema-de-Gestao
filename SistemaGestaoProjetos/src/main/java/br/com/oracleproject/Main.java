package br.com.oracleproject;

import br.com.oracleproject.controller.EquipeController;
import br.com.oracleproject.controller.ProjetoController;
import br.com.oracleproject.controller.UsuarioController;
import br.com.oracleproject.dao.UsuarioDAO;
import br.com.oracleproject.model.Usuario;

import java.util.Scanner;

public class Main {


    private static final UsuarioController usuarioController = new UsuarioController();
    private static final ProjetoController projetoController = new ProjetoController();
    private static final EquipeController equipeController = new EquipeController();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuarioLogado = null;

        System.out.println("===== BEM-VINDO AO SISTEMA DE GESTÃO DE PROJETOS =====");

        while (true) {
            if (usuarioLogado == null) {

                System.out.println("\n--- MENU INICIAL ---");
                System.out.println("1. Fazer Login");
                System.out.println("2. Cadastrar Novo Usuário");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                int opcaoInicial = scanner.nextInt();
                scanner.nextLine();

                switch (opcaoInicial) {
                    case 1:
                        System.out.print("Login: ");
                        String login = scanner.nextLine();
                        System.out.print("Senha: ");
                        String senha = scanner.nextLine();
                        usuarioLogado = usuarioDAO.buscarPorLoginESenha(login, senha);

                        if (usuarioLogado != null) {
                            System.out.println("\nLogin bem-sucedido! Bem-vindo(a), " + usuarioLogado.getNomeCompleto() + ".");
                        } else {
                            System.out.println("Login ou senha inválidos.");
                        }
                        break;
                    case 2:

                        usuarioController.cadastrarUsuario(scanner);
                        System.out.println("Agora tente fazer o login com seu novo usuário.");
                        break;
                    case 0:
                        System.out.println("Encerrando o sistema...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } else {

                exibirMenuPrincipal(scanner, usuarioLogado);

                usuarioLogado = null;
                System.out.println("Você foi desconectado.");
            }
        }
    }

    public static void exibirMenuPrincipal(Scanner scanner, Usuario usuarioLogado) {
        int opcao;
        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("Usuário Logado: " + usuarioLogado.getNomeCompleto());
            System.out.println("-------------------------");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Projetos");
            System.out.println("3. Gerenciar Equipes");
            System.out.println("0. Desconectar (Logout)");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    usuarioController.exibirMenu(scanner);
                    break;
                case 2:
                    projetoController.exibirMenu(scanner);
                    break;
                case 3:
                    equipeController.exibirMenu(scanner);
                    break;
                case 0:

                    break;
                default:
                    System.out.println("Opção inválida. Por favor, tente novamente.");
            }
        } while (opcao != 0);
    }
}