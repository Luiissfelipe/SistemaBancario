package sistema;

import usuarios.Usuario;

import java.util.Scanner;

public class Login {
    //Atributos do Login
    private String nome;
    private String nivelUsuario;
    private boolean loginRealizado;

    //Getter do nome do usuario que fez login
    public String getNome() {
        return nome;
    }

    //Getter do nivel de usuario que fez o login
    public String getNivelUsuario() {
        return nivelUsuario;
    }

    //Metodo para realizar o login
    public void realizarlogin() {
        //Declarando o Scanner
        Scanner input = new Scanner(System.in);

        //Loop para digitar o nome e senha
        //Enquanto o login não for feito, o loop se repete
        while (!loginRealizado) {
            //Mensagem de inicio
            System.out.println("Informe seus dados para realizar o login.\n");
            //Leitura do nome
            System.out.println("Nome de usuário: ");
            String nome = input.nextLine();

            //Erro para caso o nome esteja vazio
            if (nome == null || nome.isEmpty()) {
                System.out.println("***O nome não pode estar vazio. Tente novamente.***\n");
                continue;
            }
            //Verificando se o nome digitado existe no sistema
            Usuario usuarioEncontrado = SistemaBanco.getUsuarios().get(nome);
            if (usuarioEncontrado == null) {
                System.out.println("***Usuário não encontrado. Tente novamente.***\n");
                continue;
            }

            //Loop para digitar a senha
            //Caso erre algo na senha, o sistema retorna pedindo a senha novamente
            //Enquanto o login não for feito, o loop se repete
            while (!loginRealizado) {
                //Leitura da senha
                System.out.println("Senha: ");
                String senha = input.nextLine();
                //Erro para caso a senha esteja vazia ou tenha espaços
                if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                    System.out.println("***Senha não pode estar vazia e/ou conter espaços. Tente novamente.***\n");
                    continue;  //Recomeça o loop para nova tentativa de senha
                }
                //Verificando se a senha digitada esta correta
                if (usuarioEncontrado.autenticar(senha)) {
                    //Se estiver o login é realizado
                    System.out.println("***Login realizado com sucesso.***\n");
                    //Declara o nome do usuario que fez o login
                    this.nome = nome;
                    //Declara o nivel do usuario que fez o login
                    this.nivelUsuario = usuarioEncontrado.getNivelUsuario();
                    //Declara que o login foi realizado
                    this.loginRealizado = true;
                } else {
                    //Se não estiver, pede para digitar novamente
                    System.out.println("***Senha incorreta. Tente novamente.***\n");
                }
            }
        }
    }
}
