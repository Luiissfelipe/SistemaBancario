package usuarios;

public class Usuario {
    //Atributos do usuario
    private String nome;
    private String senha;
    private String nivelUsuario;

    //Construtor do usuario
    public Usuario(String nome, String senha, String nivelUsuario) {
        this.nome = nome;
        this.senha = senha;
        this.nivelUsuario = nivelUsuario;
    }

    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    //Metodo para conferir se a senha informada é igual a do usuario
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return this.getNome() + " [" + this.getNivelUsuario() + "]";
    }
}
