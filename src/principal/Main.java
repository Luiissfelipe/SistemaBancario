package principal;

import sistema.Login;
import sistema.SistemaBanco;
import usuarios.Admin;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var admin = new Admin("admin", "1234");
        SistemaBanco.adicionarUsuario(admin);

        var input = new Scanner(System.in);

        Login login = new Login();
        while (!login.isLoginRealizado()) {
            System.out.println("Digite o usuário: ");
            String usuario = input.nextLine();
            System.out.println("Digite a senha: ");
            String senha = input.nextLine();
            login.realizarLogin(usuario, senha);

        }

    }
}
