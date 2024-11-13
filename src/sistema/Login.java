package sistema;

import java.util.Scanner;

public class Login {
    private String nome;
    private String nivelUsuario;
    private boolean loginRealizado;

    public String getNome() {
        return nome;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void realizarlogin() {
        Scanner input = new Scanner(System.in);
        while (!loginRealizado) {
            System.out.println("Digite o nome: ");
            String nome = input.nextLine();
            if (nome == null || nome.isEmpty()) {
                System.out.println("O nome não pode estar vazio. Tente novamente.\n");
                continue;
            }
            var usuarioEncontrado = SistemaBanco.getUsuarios().get(nome);
            if (usuarioEncontrado == null) {
                System.out.println("Usuário não encotrado. Tente novamente.\n");
                continue;
            }

            while (!loginRealizado) {
                System.out.println("Digite a senha: ");
                String senha = input.nextLine();

                if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                    System.out.println("Senha não pode estar vazia e/ou conter espaços. Tente novamente.\n");
                    continue;  // Recomeça o loop para nova tentativa de senha
                }

                if (usuarioEncontrado.autenticar(senha)) {
                    System.out.println("Login realizado com sucesso.\n");
                    this.nome = nome;
                    this.nivelUsuario = usuarioEncontrado.getNivelUsuario();
                    this.loginRealizado = true;
                } else {
                    System.out.println("Senha incorreta. Tente novamente.\n");
                }
            }
        }
    }
}
