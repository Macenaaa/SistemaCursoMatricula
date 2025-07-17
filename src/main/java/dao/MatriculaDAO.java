package dao;

import util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MatriculaDAO {

    public void matricular(int alunoId, int cursoId) {
        String sql = "INSERT INTO matricula (aluno_id, curso_id) VALUES (?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alunoId);
            stmt.setInt(2, cursoId);

            stmt.executeUpdate();
            System.out.println("Aluno matriculado no curso com sucesso.");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                System.out.println("Este aluno já está matriculado nesse curso.");
            } else {
                e.printStackTrace();
            }
        }
    }
}
