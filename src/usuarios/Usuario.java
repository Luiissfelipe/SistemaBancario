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

    public void autenticar() {

    }

}
