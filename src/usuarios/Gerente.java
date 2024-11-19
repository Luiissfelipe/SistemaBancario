package usuarios;

import contas.Conta;
import contas.ContaCorrente;
import contas.ContaCorrenteAdicional;
import contas.ContaPoupanca;
import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public class Gerente extends Usuario {
    //Classe Gerente herda atributos e metodos da classe Usuario

    //Construtor do Gerente, ja cria com o nivel de usuario
    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    //Metodo para cadastrar um bancario
    private void cadastrarBancario() throws IOException {
        //Declarando Scanner
        Scanner input = new Scanner(System.in);

        //Leitura do nome do bancario
        System.out.println("Digite o nome do bancário:");
        String nomeBancario = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeBancario.isEmpty()) {
            System.out.println("O nome do bancário não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado já existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeBancario) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        //Leitura da senha
        System.out.println("Digite a senha do bancário:");
        String senhaBancario = input.nextLine();
        //Erro para caso a senha esteja vazia ou tenha espaços
        if (senhaBancario.isEmpty() || senhaBancario.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        //Erro para caso a senha não tenha entre 4 e 7 caracteres
        if (senhaBancario.length() < 4 || senhaBancario.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        //Se não ocorrer nenhum erro, um novo Bancario é criado
        Bancario novoBancario = new Bancario(nomeBancario, senhaBancario);
        //Chamando o metodo para adicionar o Usuario criado
        SistemaBanco.adicionarUsuario(novoBancario);
        System.out.printf("Bancário %s cadastrado com sucesso.\n\n", nomeBancario);
    }

    //Metodo para cadastrar um correntista
    private void cadastrarCorrentista() throws IOException {
        //Declarando Scanner
        Scanner input = new Scanner(System.in);

        //Leitura do nome
        System.out.println("Digite o nome do correntista:");
        String nomeCorrentista = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeCorrentista.isEmpty()) {
            System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado já existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeCorrentista) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        //Leitura da senha
        System.out.println("Digite a senha do correntista:");
        String senhaCorrentista = input.nextLine();
        //Erro para caso a senha esteja vazia ou tenha espaços
        if (senhaCorrentista.isEmpty() || senhaCorrentista.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        //Erro para caso a senha não tenha entre 4 e 7 caracteres
        if (senhaCorrentista.length() < 4 || senhaCorrentista.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        //Se não ocorrer nenhum erro, um novo Correntista é criado
        Correntista correntista = new Correntista(nomeCorrentista, senhaCorrentista);

        System.out.println("""
                Qual conta deseja criar?
                [1] Conta corrente
                [2] Conta poupanca
                """);
        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {
            case 1:
                criarContaCorrente(correntista);
                break;
            case 2:
                criarContaPoupanca(correntista);
                break;
            default:
                System.out.println("Digite uma opção válida.");
        }


        //Chamando o metodo para adicionar o Usuario criado
        SistemaBanco.adicionarUsuario(correntista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n\n", nomeCorrentista);
    }

    private void criarContaCorrente(Correntista correntista) throws IOException {
        Scanner input = new Scanner(System.in);
        correntista.gerarNumContaCorrente();
        String numContaCorrente = correntista.getNumContaCorrente();
        if (SistemaBanco.getContas().get(numContaCorrente) != null) {
            return;
        }
        ContaCorrente contaCorrente = new ContaCorrente(numContaCorrente,correntista.getNome(),correntista.getSenha());
        System.out.println("""
                        Deseja utilizar cheque especial?
                        [1] Sim
                        [2] Não
                        """);
        int opcaoChequeEspecial = input.nextInt();
        input.nextLine();
        switch (opcaoChequeEspecial) {
            case 1:
                contaCorrente.setChequeEspecial(true);
                System.out.println("Digite o valor do limite do cheque especial:");
                double limiteChequeEspecial = input.nextDouble();
                contaCorrente.setLimiteChequeEspecial(limiteChequeEspecial);
                SistemaBanco.adicionarConta(contaCorrente);
                SistemaBanco.adicionarUsuario(correntista);
                System.out.printf("Conta corrente criada com um limite de R$ %.2f para cheque especial.\n", limiteChequeEspecial);
                System.out.printf("Número da conta: %s\n", contaCorrente.getNumeroConta());
                break;
            case 2:
                contaCorrente.setChequeEspecial(false);
                contaCorrente.setLimiteChequeEspecial(0);
                SistemaBanco.adicionarConta(contaCorrente);
                SistemaBanco.adicionarUsuario(correntista);
                System.out.println("Conta corrente criada sem opção de cheque especial.");
                System.out.printf("Número da conta: %s\n", contaCorrente.getNumeroConta());
                break;
            default:
                System.out.println("Digite uma opção válida.");
        }
    }

    private void criarContaPoupanca(Correntista correntista) throws IOException {
        correntista.gerarNumContaPoupanca();
        String numContaPoupanca = correntista.getNumContaPoupanca();
        if (SistemaBanco.getContas().get(numContaPoupanca) != null) {
            return;
        }
        ContaPoupanca contaPoupanca = new ContaPoupanca(numContaPoupanca, correntista.getNome(), correntista.getSenha());
        SistemaBanco.adicionarConta(contaPoupanca);
        SistemaBanco.adicionarUsuario(correntista);
        System.out.println("Conta poupança criada com sucesso.");
        System.out.printf("Número da conta: %s\n", contaPoupanca.getNumeroConta());
    }

    private void criarContaCorrenteAdicional(Correntista correntista) throws IOException {
        Scanner input = new Scanner(System.in);

        correntista.gerarNumContaAdicional();
        String numContaAdicional = correntista.getNumContaAdicional();
        if (SistemaBanco.getContas().get(numContaAdicional) != null) {
            return;
        }
        System.out.println("Digite o valor do limite da Conta Adicional:");
        double limite = input.nextDouble();
        ContaCorrenteAdicional contaCorrenteAdicional = new ContaCorrenteAdicional(numContaAdicional, correntista.getNome(), correntista.getSenha());
        contaCorrenteAdicional.setSaldo(limite);
        SistemaBanco.adicionarConta(contaCorrenteAdicional);
        SistemaBanco.adicionarUsuario(correntista);
        System.out.printf("Conta corrente adicional criada com um limite de R$ %.2f\n", contaCorrenteAdicional.getSaldo());
        System.out.printf("Número da conta: %s\n", contaCorrenteAdicional.getNumeroConta());
    }

    private void criarConta() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Digite o nome do correntista que deseja criar uma conta bancária:");
        String nomeCorrentista = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeCorrentista.isEmpty()) {
            System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeCorrentista) == null) {
            System.out.println("Não existe um usuário com esse nome.\n");
            return;
        }

        if (!(SistemaBanco.getUsuarios().get(nomeCorrentista) instanceof Correntista)) {
            System.out.println("O nome digitado não pertence a um correntista.\n");
            return;
        }

        Correntista correntistaEncontrado = (Correntista) SistemaBanco.getUsuarios().get(nomeCorrentista);

        System.out.println("""
                Qual conta deseja criar?
                [1] Conta Corrente
                [2] Conta Corrente Adicional
                [3] Conta Poupança
                """);
        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {
            case 1:
                if (correntistaEncontrado.getNumContaCorrente().equals("null")) {
                    criarContaCorrente(correntistaEncontrado);
                }else {
                    System.out.println("Esse correntista já possui uma conta corrente.");
                }
                break;
            case 2:
                if (!correntistaEncontrado.getNumContaCorrente().equals("null")) {
                    if (correntistaEncontrado.getNumContaAdicional().equals("null")){
                        criarContaCorrenteAdicional(correntistaEncontrado);
                    } else {
                        System.out.println("Esse correntista já possui uma conta adicional.");
                    }
                }else {
                    System.out.println("Esse correntista não possui uma conta corrente para criar uma conta adicional.");
                }
                break;
            case 3:
                if (correntistaEncontrado.getNumContaPoupanca().equals("null")) {
                    criarContaPoupanca(correntistaEncontrado);
                }else {
                    System.out.println("Esse correntista já possui uma conta poupanca.");
                }
                break;
            default:
                System.out.println("Escolha uma opção válida.");
        }

    }

    private void alterarLimiteContaAdicional() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o número da conta adicional:");
        String numContaAdicional = input.nextLine();
        Conta contaEncontrada = SistemaBanco.getContas().get(numContaAdicional);

        if (numContaAdicional.isEmpty()) {
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (contaEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }
        if (!contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("Essa não é uma conta corrente adicional.");
            return;
        }

        System.out.printf("O limite dessa conta é R$ %.2f\n", contaEncontrada.getSaldo());
        System.out.println("Digite o novo valor de limite.");
        double novoLimite = input.nextDouble();

        contaEncontrada.setSaldo(novoLimite);
        SistemaBanco.adicionarConta(contaEncontrada);
        System.out.printf("Limite alterado. Novo limite é R$ %.2f\n", contaEncontrada.getSaldo());
    }

    //Menu de opçoes para o gerente
    public void menuGerente() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        //Loop para escolher uma opção
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Cadastrar bancario
                        [2] Cadastrar correntista
                        [3] Criar conta para usuario existente
                        [4] Alterar limite de conta corrente adicional
                        [0] Sair
                        """);
            try {
                //Leitura da opção escolhida
                opcao = input.nextInt();
                //Pulando uma linha para não dar erro no scanner
                input.nextLine();
                switch (opcao) {
                    case 1:
                        //Chama o metodo para cadastrar um bancario
                        cadastrarBancario();
                        break;
                    case 2:
                        //Chama o metodo para cadastrar um correntista
                        cadastrarCorrentista();
                        break;
                    case 3:
                        criarConta();
                        break;
                    case 4:
                        alterarLimiteContaAdicional();
                        break;
                    case 0:
                        //Fecha o sistema
                        break;
                    default:
                        //Erro para caso digite uma opção invalida
                        System.out.println("Opção invalida\n");
                }
            }catch (java.util.InputMismatchException e) {
                //Erro para caso não seja digitado um número na opção
                System.out.println("Entrada inválida. Por favor, insira um número.\n");
                input.nextLine();
            }
        } while (opcao != 0);
        //Fecha o Scanner
        input.close();
    }

}
