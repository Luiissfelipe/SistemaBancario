package sistema;

import java.util.Scanner;

public class Login {
    private String usuario;
    private String senha;
    private boolean loginRealizado;

    public boolean realizarLogin(String usuario, String senha) {
        try {
            if (usuario == null || usuario.trim().isEmpty() || usuario.contains(" ")) {
                System.out.println("Usuário não pode ser vazio e não deve conter espaços.");
                return false;
            }
            if (senha == null || senha.trim().isEmpty() || senha.contains(" ")) {
                System.out.println("Senha não pode ser vazia e não deve conter espaços.");
                return false;
            }
            this.usuario = usuario;
            this.senha = senha;
            var usuarioEncontrado = SistemaBanco.getUsuarios().get(this.usuario);

            if (usuarioEncontrado != null) {
                if (usuarioEncontrado.autenticar(this.senha)) {
                    System.out.println("Login realizado.");
                    this.loginRealizado = true;
                    return true;
                } else {
                    System.out.println("Senha incorreta.");
                    this.loginRealizado = false;
                    return false;
                }
            } else {
                System.out.println("Usuário não encotrado.");
                this.loginRealizado = false;
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro durante o processo de login. Tente novamente.");
        }
        return false;
    }

    public boolean isLoginRealizado() {
        return loginRealizado;
    }
}
