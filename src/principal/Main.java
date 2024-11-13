package principal;

import sistema.Login;
import sistema.SistemaBanco;
import usuarios.Admin;
import usuarios.Bancario;
import usuarios.Gerente;
import usuarios.Usuario;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Login login = new Login();
        SistemaBanco.inicializarArquivo();
        SistemaBanco.carregarUsuarios();

        login.realizarlogin();
        Usuario usuario = SistemaBanco.getUsuarios().get(login.getNome());

        switch (login.getNivelUsuario()) {
            case "admin":
                Admin admin = (Admin) usuario;
                admin.menuAdmin();
                System.out.println("Sistema finalizado.");
                break;
            case "gerente":
                Gerente gerente = (Gerente) usuario;
                gerente.menuGerente();
                System.out.println("Sistema finalizado.");
                break;
            case "bancario":
                Bancario bancario = (Bancario) usuario;
                bancario.menuBancario();
                System.out.println("Sistema finalizado.");
                break;
            case "correntista":
                System.out.printf("Usuário %s é um correntista\n", login.getNome());
                break;
            default:
                System.out.println("Usuário não identificado");
        }

    }
}
