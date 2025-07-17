import dao.AlunoDAO;
import dao.CursoDAO;
import dao.MatriculaDAO;
import model.Aluno;
import model.Curso;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        CursoDAO cursoDAO = new CursoDAO();
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        Scanner sc = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("\n--- Sistema de Cursos e Matrículas ---");
            System.out.println("1 - Listar todos os alunos");
            System.out.println("2 - Cadastrar novo aluno");
            System.out.println("3 - Cadastrar novo curso");
            System.out.println("4 - Matricular aluno em curso");
            System.out.println("5 - Listar todos os cursos com seus alunos");
            System.out.println("6 - Listar alunos não matriculados em nenhum curso");
            System.out.println("7 - Excluir aluno");
            System.out.println("8 - Excluir curso");
            System.out.println("9 - Listar cursos sem alunos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1 -> {
                    List<Aluno> alunos = alunoDAO.listarTodos();
                    alunos.forEach(System.out::println);
                }
                case 2 -> {
                    System.out.print("Nome do aluno: ");
                    String nome = sc.nextLine();
                    System.out.print("Email do aluno: ");
                    String email = sc.nextLine();
                    alunoDAO.inserir(new Aluno(nome, email));
                }
                case 3 -> {
                    System.out.print("Nome do curso: ");
                    String nomeCurso = sc.nextLine();
                    System.out.print("Descrição do curso: ");
                    String descricao = sc.nextLine();
                    cursoDAO.inserir(new Curso(nomeCurso, descricao));
                }
                case 4 -> {
                    System.out.println("Alunos disponíveis:");
                    List<Aluno> alunos = alunoDAO.listarTodos();
                    alunos.forEach(System.out::println);

                    System.out.print("Informe o ID do aluno para matricular: ");
                    int idAluno = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Cursos disponíveis:");
                    List<Curso> cursos = cursoDAO.listarTodos();
                    cursos.forEach(System.out::println);

                    System.out.print("Informe o ID do curso para matricular o aluno: ");
                    int idCurso = sc.nextInt();
                    sc.nextLine();

                    matriculaDAO.matricular(idAluno, idCurso);
                }
                case 5 -> cursoDAO.listarCursosComAlunos();

                case 6 -> {
                    List<Aluno> naoMatriculados = alunoDAO.listarNaoMatriculados();
                    System.out.println("Alunos não matriculados:");
                    naoMatriculados.forEach(System.out::println);
                }
                case 7 -> {
                    System.out.println("Alunos disponíveis:");
                    List<Aluno> alunos = alunoDAO.listarTodos();
                    alunos.forEach(System.out::println);

                    System.out.print("Informe o ID do aluno para excluir: ");
                    int idExcluirAluno = sc.nextInt();
                    sc.nextLine();
                    alunoDAO.deletar(idExcluirAluno);

                }
                case 8 -> {
                    System.out.println("Cursos disponíveis:");
                    List<Curso> cursos = cursoDAO.listarTodos();
                    cursos.forEach(System.out::println);

                    System.out.print("Informe o ID do curso para excluir: ");
                    int idExcluirCurso = sc.nextInt();
                    sc.nextLine();
                    cursoDAO.deletar(idExcluirCurso);
                }
                case 9 -> {
                    cursoDAO.listarCursosSemAlunos();
                }

                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        sc.close();
    }
}

