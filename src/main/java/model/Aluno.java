
package model;

public class Aluno {
    private int id;
    private String nome;
    private String email;

    public Aluno(int id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public Aluno(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }


    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s | Email: %s", id, nome, email);
    }
}
