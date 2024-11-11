package usuarios;

public class Usuario {
    protected String nome;
    protected String senha;
    protected String nivelUsuário;

    public Usuario(String nome, String senha, String nivelUsuário) {
        this.nome = nome;
        this.senha = senha;
        this.nivelUsuário = nivelUsuário;
    }

    public String getNome() {
        return nome;
    }

    public String getNivelUsuário() {
        return nivelUsuário;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return "Usuário: " + this.getNome() + " - " + this.getNivelUsuário();
    }
}
