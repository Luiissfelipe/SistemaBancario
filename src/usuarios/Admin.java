package usuarios;

import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public class Admin extends Usuario {

    public Admin(String nome, String senha) {
        super(nome, senha, "admin");
    }

    private void cadastrarGerente() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o nome do gerente:");
        String nomeGerente = input.nextLine();
        if (nomeGerente.isEmpty()) {
            System.out.println("O nome do gerente não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (SistemaBanco.getUsuarios().get(nomeGerente) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        System.out.println("Digite a senha do gerente:");
        String senhaGerente = input.nextLine();
        if (senhaGerente.isEmpty() || senhaGerente.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        if (senhaGerente.length() < 4 || senhaGerente.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        Gerente novoGerente = new Gerente(nomeGerente, senhaGerente);
        SistemaBanco.adicionarUsuario(novoGerente);
        SistemaBanco.salvarUsuario(novoGerente);
        System.out.printf("Gerente %s cadastrado com sucesso.\n\n", nomeGerente);
    }

    public void menuAdmin() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Cadastrar gerente
                        [2] Funções de gerente
                        [3] Funções de bancário
                        [4] Funções de correntista
                        [0] Sair
                        """);
            try {
                opcao = input.nextInt();
                input.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarGerente();
                        break;
                    case 2:
                        Gerente gerente = new Gerente("admin", "1234");
                        gerente.menuGerente();
                        opcao = 0;
                        break;
                    case 3:
                        Bancario bancario = new Bancario("admin", "1234");
                        bancario.menuBancario();
                        opcao = 0;
                        break;
                    case 4:
                        Correntista correntista = new Correntista("admin", "1234");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção invalida\n");
                }
            }catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.\n");
                input.nextLine();
            }
        } while (opcao != 0);
        input.close();
    }

}
