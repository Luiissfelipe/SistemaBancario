package usuarios;

import sistema.SistemaBanco;

import java.util.Scanner;

public class Admin extends Usuario {

    public Admin(String nome, String senha) {
        super(nome, senha, "admin");
    }

    private void cadastrarGerente(String nome, String senha) {
        Gerente novoGerente = new Gerente(nome, senha);
        SistemaBanco.adicionarUsuario(novoGerente);
        System.out.printf("Gerente %s cadastrado com sucesso.\n\n", nome);
    }

    private void cadastrarBancario(String nome, String senha) {
        Bancario novoBancario = new Bancario(nome, senha);
        SistemaBanco.adicionarUsuario(novoBancario);
        System.out.printf("Bancário %s cadastrado com sucesso.\n\n", nome);
    }

    public void cadastrarCorrenista(String nome, String senha) {
        Correntista novoCorrentista = new Correntista(nome, senha);
        SistemaBanco.adicionarUsuario(novoCorrentista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n\n", nome);
    }

    public void menuAdmin(Scanner input) {
        int opcao = 0;
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Cadastrar gerente
                        [2] Cadastrar bancario
                        [3] Cadastrar correntista
                        [0] Sair
                        """);
            opcao = input.nextInt();
            input.nextLine();
            switch (opcao) {
                case 1:
                    System.out.println("Digite o nome do gerente:");
                    String nomeGerente = input.nextLine();
                    if (nomeGerente.isEmpty()) {
                        System.out.println("O nome do gerente não pode ser vazio. Tente novamente.\n");
                        break;
                    }
                    System.out.println("Digite a senha do gerente:");
                    String senhaGerente = input.nextLine();
                    if (senhaGerente.isEmpty() || senhaGerente.contains(" ")) {
                        System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
                        break;
                    }
                    cadastrarGerente(nomeGerente, senhaGerente);
                    break;
                case 2:
                    System.out.println("Digite o nome do bancário:");
                    String nomeBancario = input.nextLine();
                    if (nomeBancario.isEmpty()) {
                        System.out.println("O nome do bancário não pode ser vazio. Tente novamente.\n");
                        break;
                    }
                    System.out.println("Digite a senha do bancário:");
                    String senhaBancario = input.nextLine();
                    if (senhaBancario.isEmpty() || senhaBancario.contains(" ")) {
                        System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
                        break;
                    }
                    cadastrarBancario(nomeBancario, senhaBancario);
                    break;
                case 3:
                    System.out.println("Digite o nome do correntista:");
                    String nomeCorrentista = input.nextLine();
                    if (nomeCorrentista.isEmpty()) {
                        System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
                        break;
                    }
                    System.out.println("Digite a senha do correntista:");
                    String senhaCorrentista = input.nextLine();
                    if (senhaCorrentista.isEmpty() || senhaCorrentista.contains(" ")) {
                        System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
                        break;
                    }
                    cadastrarBancario(nomeCorrentista, senhaCorrentista);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção invalida\n");
            }
        } while (opcao != 0);

    }

}
