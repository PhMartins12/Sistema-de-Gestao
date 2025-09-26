package br.com.oracleproject.controller;

import br.com.oracleproject.dao.EquipeDAO;
import br.com.oracleproject.dao.ProjetoDAO;
import br.com.oracleproject.dao.UsuarioDAO;
import br.com.oracleproject.model.Equipe;
import br.com.oracleproject.model.Projeto;
import br.com.oracleproject.model.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ProjetoController {

    private final ProjetoDAO projetoDAO = new ProjetoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final EquipeDAO equipeDAO = new EquipeDAO();

    public void exibirMenu(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n--- Menu de Gestão de Projetos ---");
            System.out.println("1. Cadastrar Novo Projeto");
            System.out.println("2. Listar Todos os Projetos");
            System.out.println("3. Alocar Equipe a um Projeto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProjeto(scanner);
                    break;
                case 2:
                    listarProjetos();
                    break;
                case 3:
                    alocarEquipeAProjeto(scanner);
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void listarProjetos() {
        System.out.println("\n--- Lista de Projetos Cadastrados ---");
        List<Projeto> projetos = projetoDAO.listar();
        if (projetos.isEmpty()) {
            System.out.println("Nenhum projeto encontrado.");
        } else {
            for (Projeto p : projetos) {
                System.out.println("---------------------------------------------------------------------------------------------");
                System.out.printf("ID: %d | Nome do Projeto: %s\n", p.getId(), p.getNome());
                System.out.printf("Status: %s | Gerente Responsável: %s\n", p.getStatus(), p.getGerenteResponsavel().getNomeCompleto());
                System.out.println("Equipes Alocadas:");
                if (p.getEquipesAlocadas().isEmpty()) {
                    System.out.println("  - Nenhuma equipe alocada a este projeto.");
                } else {
                    for (Equipe equipe : p.getEquipesAlocadas()) {
                        System.out.printf("  - ID: %d, Nome: %s\n", equipe.getId(), equipe.getNome());
                    }
                }
            }
            System.out.println("---------------------------------------------------------------------------------------------");
        }
    }


    private void cadastrarProjeto(Scanner scanner) {
        System.out.println("\n--- Cadastro de Novo Projeto ---");
        Projeto projeto = new Projeto();

        System.out.print("Nome do Projeto: ");
        projeto.setNome(scanner.nextLine());
        System.out.print("Descrição: ");
        projeto.setDescricao(scanner.nextLine());

        try {
            System.out.print("Data de Início (AAAA-MM-DD): ");
            projeto.setDataInicio(LocalDate.parse(scanner.nextLine()));
            System.out.print("Data de Término Prevista (AAAA-MM-DD): ");
            projeto.setDataTerminoPrevista(LocalDate.parse(scanner.nextLine()));
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use AAAA-MM-DD. O cadastro foi cancelado.");
            return;
        }

        System.out.print("Status (PLANEJADO, EM_ANDAMENTO, CONCLUIDO, CANCELADO): ");
        try {
            projeto.setStatus(Projeto.Status.valueOf(scanner.nextLine().toUpperCase()));
        } catch (IllegalArgumentException e) {
            System.out.println("Status inválido. O cadastro foi cancelado.");
            return;
        }

        System.out.println("\n--- Gerentes Disponíveis ---");
        List<Usuario> gerentes = usuarioDAO.listarPorPerfil(Usuario.Perfil.GERENTE);

        if (gerentes.isEmpty()) {
            System.out.println("Nenhum gerente encontrado. Cadastre um usuário com o perfil 'GERENTE' primeiro.");
            return;
        }

        for (Usuario g : gerentes) {
            System.out.printf("ID: %d - Nome: %s\n", g.getId(), g.getNomeCompleto());
        }

        System.out.print("\nDigite o ID do Gerente Responsável: ");
        int gerenteId = scanner.nextInt();
        scanner.nextLine();

        Usuario gerenteSelecionado = null;
        for (Usuario g : gerentes) {
            if (g.getId() == gerenteId) {
                gerenteSelecionado = g;
                break;
            }
        }

        if(gerenteSelecionado == null) {
            System.out.println("ID de gerente inválido. Cadastro cancelado.");
            return;
        }
        projeto.setGerenteResponsavel(gerenteSelecionado);

        projetoDAO.salvar(projeto);
        System.out.println("Projeto cadastrado com sucesso!");
    }


    private void alocarEquipeAProjeto(Scanner scanner) {
        System.out.println("\n--- Alocar Equipe a um Projeto ---");

        System.out.println("\nProjetos disponíveis:");
        listarProjetos();
        System.out.print("\nDigite o ID do projeto: ");
        int idProjeto = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\nEquipes disponíveis:");
        EquipeController equipeController = new EquipeController();
        equipeController.listarEquipes();
        System.out.print("\nDigite o ID da equipe que deseja alocar ao projeto: ");
        int idEquipe = scanner.nextInt();
        scanner.nextLine();

        projetoDAO.alocarEquipe(idProjeto, idEquipe);
        System.out.println("Equipe alocada com sucesso ao projeto!");
    }
}