package br.com.oracleproject.controller;

import br.com.oracleproject.dao.UsuarioDAO;
import br.com.oracleproject.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu de Gestão de Usuários ---");
            System.out.println("1. Cadastrar Novo Usuário");
            System.out.println("2. Listar Todos os Usuários");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarUsuario(scanner);
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    public void cadastrarUsuario(Scanner scanner) {
        System.out.println("\n--- Cadastro de Novo Usuário ---");
        Usuario usuario = new Usuario();

        System.out.print("Nome Completo: ");
        usuario.setNomeCompleto(scanner.nextLine());
        System.out.print("CPF: ");
        usuario.setCpf(scanner.nextLine());
        System.out.print("Email: ");
        usuario.setEmail(scanner.nextLine());
        System.out.print("Cargo: ");
        usuario.setCargo(scanner.nextLine());
        System.out.print("Login: ");
        usuario.setLogin(scanner.nextLine());
        System.out.print("Senha: ");
        usuario.setSenha(scanner.nextLine());
        System.out.print("Perfil (ADMINISTRADOR, GERENTE, COLABORADOR): ");
        try {
            usuario.setPerfil(Usuario.Perfil.valueOf(scanner.nextLine().toUpperCase()));
            usuarioDAO.salvar(usuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: Perfil inválido. O cadastro foi cancelado.");
        } catch (RuntimeException e) {
            System.out.println("Erro ao salvar no banco de dados: " + e.getMessage());
        }
    }

    private void listarUsuarios() {
        System.out.println("\n--- Lista de Usuários Cadastrados ---");
        List<Usuario> usuarios = usuarioDAO.listar();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado.");
        } else {
            System.out.printf("%-5s | %-30s | %-15s | %-15s\n", "ID", "Nome Completo", "Login", "Perfil");
            System.out.println("----------------------------------------------------------------------");
            for (Usuario u : usuarios) {
                System.out.printf("%-5d | %-30s | %-15s | %-15s\n", u.getId(), u.getNomeCompleto(), u.getLogin(), u.getPerfil());
            }
        }
    }
}