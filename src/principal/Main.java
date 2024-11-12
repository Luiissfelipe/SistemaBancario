package principal;

import sistema.Login;
import sistema.SistemaBanco;
import usuarios.Admin;
import usuarios.Gerente;
import usuarios.Usuario;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        Login login = new Login();
        SistemaBanco.inicializarArquivo();
        SistemaBanco.carregarUsuarios();

/*
        int escolha = 0;
        do {
            login.escolhaLogin();
            escolha = input.nextInt();
            switch (escolha) {
                case 1:
                    System.out.println("Você é um funcionário do banco.");

                    break;
                case 2:
                    System.out.println("Você é cliente do banco.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Escolha uma opção válida.");
            }
        } while (escolha != 0);
*/
        while (!login.isLoginRealizado()) {
            System.out.println("Digite o nome: ");
            String nome = input.nextLine();
            System.out.println("Digite a senha: ");
            String senha = input.nextLine();
            login.realizarLogin(nome, senha);
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
