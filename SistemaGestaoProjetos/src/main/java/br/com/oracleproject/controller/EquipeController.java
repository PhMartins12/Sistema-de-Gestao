package br.com.oracleproject.controller;

import br.com.oracleproject.dao.EquipeDAO;
import br.com.oracleproject.dao.UsuarioDAO;
import br.com.oracleproject.model.Equipe;
import br.com.oracleproject.model.Usuario;

import java.util.List;
import java.util.Scanner;

public class EquipeController {

    private final EquipeDAO equipeDAO = new EquipeDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu de Gestão de Equipes ---");
            System.out.println("1. Cadastrar Nova Equipe");
            System.out.println("2. Adicionar Membro a uma Equipe");
            System.out.println("3. Listar Todas as Equipes e seus Membros");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarEquipe(scanner);
                    break;
                case 2:
                    adicionarMembroAEquipe(scanner);
                    break;
                case 3:
                    listarEquipes();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarEquipe(Scanner scanner) {
        System.out.println("\n--- Cadastro de Nova Equipe ---");
        Equipe equipe = new Equipe();

        System.out.print("Nome da Equipe: ");
        equipe.setNome(scanner.nextLine());
        System.out.print("Descrição da Equipe: ");
        equipe.setDescricao(scanner.nextLine());

        equipeDAO.salvar(equipe);
        System.out.println("Equipe cadastrada com sucesso!");
    }

    private void adicionarMembroAEquipe(Scanner scanner) {
        System.out.println("\n--- Adicionar Membro a uma Equipe ---");
        listarEquipes();

        System.out.print("\nDigite o ID da equipe para adicionar o membro: ");
        int idEquipe = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n--- Usuários Disponíveis ---");
        List<Usuario> usuarios = usuarioDAO.listar();
        System.out.printf("%-5s | %-30s | %-15s\n", "ID", "Nome Completo", "Login");
        System.out.println("---------------------------------------------------------");
        for (Usuario u : usuarios) {
            System.out.printf("%-5d | %-30s | %-15s\n", u.getId(), u.getNomeCompleto(), u.getLogin());
        }

        System.out.print("\nDigite o ID do usuário para adicionar à equipe: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        equipeDAO.adicionarMembro(idEquipe, idUsuario);
        System.out.println("Membro adicionado com sucesso!");
    }

    public void listarEquipes() {
        System.out.println("\n--- Lista de Equipes Cadastradas ---");
        List<Equipe> equipes = equipeDAO.listar();

        if (equipes.isEmpty()) {
            System.out.println("Nenhuma equipe encontrada.");
        } else {
            for (Equipe equipe : equipes) {
                System.out.println("-------------------------------------------");
                System.out.printf("ID: %d | Nome da Equipe: %s\n", equipe.getId(), equipe.getNome());
                System.out.println("Descrição: " + equipe.getDescricao());
                System.out.println("Membros:");
                if (equipe.getMembros().isEmpty()) {
                    System.out.println("  - Esta equipe ainda não possui membros.");
                } else {
                    for (Usuario membro : equipe.getMembros()) {
                        System.out.printf("  - ID: %d, Nome: %s\n", membro.getId(), membro.getNomeCompleto());
                    }
                }
            }
            System.out.println("-------------------------------------------");
        }
    }
}