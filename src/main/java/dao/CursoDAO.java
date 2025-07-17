package dao;

import model.Curso;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public void inserir(Curso curso) {
        String sql = "INSERT INTO curso (nome, descricao) VALUES (?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.executeUpdate();
            System.out.println("Curso inserido com sucesso.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Curso> listarTodos() {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM curso ORDER BY nome ASC";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cursos.add(new Curso(rs.getInt("id"), rs.getString("nome"), rs.getString("descricao")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    public void deletar(int id) {
        String sql = "DELETE FROM curso WHERE id = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Curso deletado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listarCursosComAlunos() {
        String sql = """
        SELECT c.id AS curso_id, c.nome AS curso_nome, c.descricao,
               a.nome AS aluno_nome, a.email
        FROM curso c
        LEFT JOIN matricula m ON c.id = m.curso_id
        LEFT JOIN aluno a ON m.aluno_id = a.id
        ORDER BY c.nome, a.nome
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int cursoIdAnterior = -1;

            while (rs.next()) {
                int cursoIdAtual = rs.getInt("curso_id");

                if (cursoIdAtual != cursoIdAnterior) {
                    if (cursoIdAnterior != -1) System.out.println();
                    System.out.println("Curso: " + rs.getString("curso_nome") + " - " + rs.getString("descricao"));
                    System.out.println("Alunos matriculados:");
                    cursoIdAnterior = cursoIdAtual;
                }

                String alunoNome = rs.getString("aluno_nome");
                String email = rs.getString("email");

                if (alunoNome != null) {
                    System.out.println("  * " + alunoNome + " (" + email + ")");
                } else {
                    System.out.println("  Nenhum aluno matriculado.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void listarCursosSemAlunos() {
        String sql = "SELECT id, nome, descricao FROM cursos_sem_alunos";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\nCursos sem alunos matriculados:");
            while (rs.next()) {
                System.out.printf("ID: %d | Curso: %s | Descrição: %s%n",
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
