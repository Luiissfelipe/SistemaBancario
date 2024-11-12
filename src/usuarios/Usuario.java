package usuarios;

import java.util.Scanner;

public class Usuario {
    protected String nome;
    protected String senha;
    protected String nivelUsuario;

    public Usuario(String nome, String senha, String nivelUsuario) {
        this.nome = nome;
        this.senha = senha;
        this.nivelUsuario = nivelUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    @Override
    public String toString() {
        return "Usuário: " + this.getNome() + " - " + this.getNivelUsuario();
    }
}
