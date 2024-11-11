package principal;

import sistema.Login;
import sistema.SistemaBanco;
import usuarios.Admin;
import usuarios.Gerente;
import usuarios.Usuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Login login = new Login();
        Admin user = new Admin("admin", "1234");
        SistemaBanco.adicionarUsuario(user);
        user.cadastrarCorrenista("Samuel", "1234");

        while (!login.isLoginRealizado()) {
            System.out.println("Digite o nome: ");
            String usuario = input.nextLine();
            System.out.println("Digite a senha: ");
            String senha = input.nextLine();
            login.realizarLogin(usuario, senha);
        }

        Usuario usuario = SistemaBanco.getUsuarios().get(login.getNome());

        switch (login.getNivelUsuario()) {
            case "admin":
                Admin admin = (Admin) usuario;
                admin.menuAdmin(input);
                System.out.println("Sistema finalizado.");
                break;
            case "gerente":
                System.out.printf("Usuário %s é um gerente\n", login.getNome());
                break;
            case "bancario":
                System.out.printf("Usuário %s é um bancário\n", login.getNome());
                break;
            case "correntista":
                System.out.printf("Usuário %s é um correntista\n", login.getNome());
                break;
            default:
                System.out.println("Usuário não identificado");
        }

    }
}
