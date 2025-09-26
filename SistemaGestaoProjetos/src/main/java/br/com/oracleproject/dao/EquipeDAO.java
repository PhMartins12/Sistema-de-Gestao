package br.com.oracleproject.dao;

import br.com.oracleproject.model.Equipe;
import br.com.oracleproject.model.Usuario;
import br.com.oracleproject.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeDAO {

    public void salvar(Equipe equipe) {
        String sql = "INSERT INTO equipes (nome, descricao) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getDescricao());
            stmt.executeUpdate();


            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    equipe.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar equipe", e);
        }
    }

    public void adicionarMembro(int idEquipe, int idUsuario) {
        String sql = "INSERT INTO equipe_membros (id_equipe, id_usuario) VALUES (?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEquipe);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();

        } catch (SQLException e) {

            if (e.getSQLState().equals("23505")) {
                System.out.println("Aviso: Este usuário já é membro desta equipe.");
            } else {
                throw new RuntimeException("Erro ao adicionar membro à equipe", e);
            }
        }
    }

    public List<Equipe> listar() {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT id, nome, descricao FROM equipes";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(rs.getInt("id"));
                equipe.setNome(rs.getString("nome"));
                equipe.setDescricao(rs.getString("descricao"));

                equipe.setMembros(buscarMembrosPorEquipeId(equipe.getId(), conn));
                equipes.add(equipe);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar equipes", e);
        }
        return equipes;
    }


    private List<Usuario> buscarMembrosPorEquipeId(int idEquipe, Connection conn) throws SQLException {
        List<Usuario> membros = new ArrayList<>();
        String sql = "SELECT u.id, u.nome_completo FROM usuarios u " +
                "JOIN equipe_membros em ON u.id = em.id_usuario " +
                "WHERE em.id_equipe = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEquipe);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario membro = new Usuario();
                    membro.setId(rs.getInt("id"));
                    membro.setNomeCompleto(rs.getString("nome_completo"));
                    membros.add(membro);
                }
            }
        }
        return membros;
    }
}