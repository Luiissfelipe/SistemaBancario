package usuarios;

import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public class Gerente extends Usuario {

    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    private void cadastrarBancario() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o nome do bancário:");
        String nomeBancario = input.nextLine();
        if (nomeBancario.isEmpty()) {
            System.out.println("O nome do bancário não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (SistemaBanco.getUsuarios().get(nomeBancario) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        System.out.println("Digite a senha do bancário:");
        String senhaBancario = input.nextLine();
        if (senhaBancario.isEmpty() || senhaBancario.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        if (senhaBancario.length() < 4 || senhaBancario.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        Bancario novoBancario = new Bancario(nomeBancario, senhaBancario);
        SistemaBanco.adicionarUsuario(novoBancario);
        SistemaBanco.salvarUsuario(novoBancario);
        System.out.printf("Bancário %s cadastrado com sucesso.\n\n", nomeBancario);
    }

    private void cadastrarCorrentista() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o nome do correntista:");
        String nomeCorrentista = input.nextLine();
        if (nomeCorrentista.isEmpty()) {
            System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (SistemaBanco.getUsuarios().get(nomeCorrentista) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        System.out.println("Digite a senha do correntista:");
        String senhaCorrentista = input.nextLine();
        if (senhaCorrentista.isEmpty() || senhaCorrentista.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        if (senhaCorrentista.length() < 4 || senhaCorrentista.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        Correntista novoCorrentista = new Correntista(nomeCorrentista, senhaCorrentista);
        SistemaBanco.adicionarUsuario(novoCorrentista);
        SistemaBanco.salvarUsuario(novoCorrentista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n\n", nomeCorrentista);
    }

    private void configurarLimiteContasAdicionais() {
        System.out.println("Configurando limites de contas adicionais");
    }

    private void criarContaCorrenteAdicional() {
        System.out.println("Criando conta corrente adicional.");
    }

    public void menuGerente() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Cadastrar bancario
                        [2] Cadastrar correntista
                        [3] Configurar limites de contas adicionais
                        [4] Criar conta corrente adicional
                        [0] Sair
                        """);
            try {
                opcao = input.nextInt();
                input.nextLine();
                switch (opcao) {
                    case 1:
                        cadastrarBancario();
                        break;
                    case 2:
                        cadastrarCorrentista();
                        break;
                    case 3:
                        configurarLimiteContasAdicionais();
                        break;
                    case 4:
                        criarContaCorrenteAdicional();
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
