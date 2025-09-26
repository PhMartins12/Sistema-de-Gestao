package br.com.oracleproject.dao;

import br.com.oracleproject.model.Equipe;
import br.com.oracleproject.model.Projeto;
import br.com.oracleproject.model.Usuario;
import br.com.oracleproject.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetoDAO {

    public void salvar(Projeto projeto) {
        String sql = "INSERT INTO projetos (nome, descricao, data_inicio, data_termino_prevista, status, id_gerente_responsavel) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, projeto.getNome());
            stmt.setString(2, projeto.getDescricao());
            stmt.setDate(3, Date.valueOf(projeto.getDataInicio()));
            stmt.setDate(4, Date.valueOf(projeto.getDataTerminoPrevista()));
            stmt.setString(5, projeto.getStatus().toString());
            stmt.setInt(6, projeto.getGerenteResponsavel().getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar projeto no banco de dados", e);
        }
    }

    public List<Projeto> listar() {
        List<Projeto> projetos = new ArrayList<>();
        String sql = "SELECT p.id as projeto_id, p.nome, p.status, u.id as gerente_id, u.nome_completo as gerente_nome " +
                "FROM projetos p " +
                "JOIN usuarios u ON p.id_gerente_responsavel = u.id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Projeto projeto = new Projeto();
                projeto.setId(rs.getInt("projeto_id"));
                projeto.setNome(rs.getString("nome"));
                projeto.setStatus(Projeto.Status.valueOf(rs.getString("status")));

                Usuario gerente = new Usuario();
                gerente.setId(rs.getInt("gerente_id"));
                gerente.setNomeCompleto(rs.getString("gerente_nome"));
                projeto.setGerenteResponsavel(gerente);

                projeto.setEquipesAlocadas(buscarEquipesPorProjetoId(projeto.getId(), conn));

                projetos.add(projeto);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar projetos", e);
        }
        return projetos;
    }


    private List<Equipe> buscarEquipesPorProjetoId(int idProjeto, Connection conn) throws SQLException {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT e.id, e.nome FROM equipes e " +
                "JOIN projeto_equipes pe ON e.id = pe.id_equipe " +
                "WHERE pe.id_projeto = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProjeto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Equipe equipe = new Equipe();
                    equipe.setId(rs.getInt("id"));
                    equipe.setNome(rs.getString("nome"));
                    equipes.add(equipe);
                }
            }
        }
        return equipes;
    }

    public Usuario buscarGerentePorId(int id) {
        String sql = "SELECT id, nome_completo FROM usuarios WHERE id = ? AND perfil = 'GERENTE'";
        Usuario gerente = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    gerente = new Usuario();
                    gerente.setId(rs.getInt("id"));
                    gerente.setNomeCompleto(rs.getString("nome_completo"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar gerente por ID", e);
        }
        return gerente;
    }

    public void alocarEquipe(int idProjeto, int idEquipe) {
        String sql = "INSERT INTO projeto_equipes (id_projeto, id_equipe) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProjeto);
            stmt.setInt(2, idEquipe);
            stmt.executeUpdate();

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                System.out.println("Aviso: Esta equipe já está alocada neste projeto.");
            } else {
                throw new RuntimeException("Erro ao alocar equipe ao projeto", e);
            }
        }
    }
}