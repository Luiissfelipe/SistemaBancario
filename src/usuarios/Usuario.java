package usuarios;

import java.util.Scanner;

public class Usuario {
    //Atributos do usuario
    protected String nome;
    protected String senha;
    protected String nivelUsuario;

    //Construtor do usuario
    public Usuario(String nome, String senha, String nivelUsuario) {
        this.nome = nome;
        this.senha = senha;
        this.nivelUsuario = nivelUsuario;
    }

    //Getter do nome do usuario
    public String getNome() {
        return nome;
    }

    //Getter da senha do usuario
    public String getSenha() {
        return senha;
    }

    //Getter do nivel do usuario
    public String getNivelUsuario() {
        return nivelUsuario;
    }

    //Metodo para conferir se a senha informada é igual a do usuario
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return "Usuário: " + this.getNome() + " - " + this.getNivelUsuario();
    }
}
