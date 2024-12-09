package usuarios;

import contas.Conta;
import contas.ContaCorrente;
import contas.ContaCorrenteAdicional;
import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Correntista extends Usuario{
    //Classe Correntista herda atributos e metodos da classe Usuario

    //Atributos do correntista
    private String numContaCorrente;
    private String numContaPoupanca;
    private String numContaAdicional;

    //Construtor do Correntista, ja cria com o nivel de usuario
    public Correntista(String nome, String senha) {
        super(nome, senha, "correntista");
    }

    //Getters e Setters
    public String getNumContaCorrente() {
        return numContaCorrente;
    }

    public void setNumContaCorrente(String numContaCorrente) {
        this.numContaCorrente = numContaCorrente;
    }

    public String getNumContaPoupanca() {
        return numContaPoupanca;
    }

    public void setNumContaPoupanca(String numContaPoupanca) {
        this.numContaPoupanca = numContaPoupanca;
    }

    public String getNumContaAdicional() {
        return numContaAdicional;
    }

    public void setNumContaAdicional(String numContaAdicional) {
        this.numContaAdicional = numContaAdicional;
    }

    //Metodo para gerar um número de conta corrente
    public void gerarNumContaCorrente() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaCorrente = "CC" + numero;
    }

    //Metodo para gerar um número de conta poupança
    public void gerarNumContaPoupanca() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaPoupanca = "CP" + numero;
    }

    //Metodo para gerar um número de conta corrente adicional
    public void gerarNumContaAdicional() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaAdicional = "CA" + numero;
    }

    //Metodo para mostrar o saldo
    private void mostrarSaldo(Conta contaEncontrada) {
        //Verificando se é uma conta adicional
        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.printf("***O limite da sua conta adicional é R$ %.2f***\n", contaEncontrada.getSaldo());
        } else {
            System.out.printf("***O saldo da conta é R$ %.2f***\n", contaEncontrada.getSaldo());
            //Verificando se é conta corrente
            if (contaEncontrada instanceof ContaCorrente contaCorrente) {
                //Verificando se possui cheque especial
                if (contaCorrente.isChequeEspecial()) {
                    //Mostrando o limite do cheque especial
                    System.out.printf("***Limite de cheque especial R$ %.2f***\n", contaCorrente.getLimiteChequeEspecial());
                }else {
                    System.out.println("***Essa conta não possui cheque especial.***");
                }
            }
        }
    }

    //Metodo para realizar saque
    private void realizarSaque(Conta contaEncontrada) throws IOException {
        //Declarando scanner
        Scanner input = new Scanner(System.in);

        //Verificando se é uma conta adicional
        if(contaEncontrada instanceof ContaCorrenteAdicional) {
            //Mostrando limite da conta adicional
            System.out.printf("***Limite da Conta Adicional: R$ %.2f***\n", contaEncontrada.getSaldo());
        } else {
            //Mostrando saldo das outras contas
            System.out.printf("***Saldo disponivel: R$ %.2f***\n", contaEncontrada.getSaldo());
        }

        //Verificando se é conta corrente
        if (contaEncontrada instanceof ContaCorrente contaCorrente) {
            //Verificando se possui cheque especial
            if (contaCorrente.isChequeEspecial()) {
                //Mostrando o limite do cheque especial
                System.out.printf("***Limite de cheque especial R$ %.2f***\n", contaCorrente.getLimiteChequeEspecial());
            }else {
                System.out.println("***Essa conta não possui cheque especial.***");
            }
        }

        //Lendo o valor do saque
        System.out.println("Informe o valor do saque:");
        double valorSaque = input.nextDouble();
        input.nextLine();

        //Erro para caso o valor do saque seja menor ou igual a 0
        if (valorSaque < 0) {
            System.out.println("***Valor de saque deve ser maior que 0.***");
        } else {
            //Declarando senha como falsa
            boolean senhaCorreta = false;
            //Loop para digitar a senha
            while (!senhaCorreta) {
                //Lendo senha
                System.out.println("Informe sua senha:");
                String senha = input.nextLine();

                //Erro para caso a senha esteja vazia ou tenha espaços
                if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                    System.out.println("***Senha não pode estar vazia e/ou conter espaços. Tente novamente.***\n");
                    return;
                }
                //Verificando se a senha esta correta
                if (contaEncontrada.autenticar(senha)) {
                    //Chamando o metodo para sacar
                    contaEncontrada.sacar(valorSaque);
                    //Declarando senha como verdadeira para encerrar o loop
                    senhaCorreta = true;
                } else {
                    //Erro caso a senha estiver incorreta
                    System.out.println("***Senha incorreta. Tente novamente.***\n");
                }
            }
        }
    }

    //Metodo para realizar deposito
    private void realizarDeposito(Conta contaEncontrada) throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        //Verificando se é uma conta corrente adicional
        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("***Você não pode depositar em uma conta adicional.***");
            return;
        }
        //Lendo o valor do deposito
        System.out.println("Informe o valor do depósito:");
        double valorDeposito = input.nextDouble();

        //Erro caso o valor seja menor ou igual a 0
        if (valorDeposito <= 0) {
            System.out.println("***O valor do deposito deve ser maior que 0.***");
        } else {
            contaEncontrada.depositar(valorDeposito);
            System.out.printf("***Deposito de R$ %.2f realizado com sucesso.***\n", valorDeposito);
        }
    }

    //Metodo para realizar transferencia
    private void realizarTransferencia(Conta contaEncontrada) throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        //Verificando se é uma conta corrente adicional
        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("***Não é possível realizar transferência de uma conta corrente adicional.***");
            return;
        }

        //Lendo o numero da conta de destino
        System.out.println("Informe o número da conta de destino:");
        String numContaDestino = input.nextLine();
        //Declarando conta de destino conforme o numero lido
        Conta contaDestinoEncontrada = SistemaBanco.getContas().get(numContaDestino);

        //Erro para caso o numero esteja vazio
        if (numContaDestino.isEmpty()) {
            System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
            return;
        }
        //Verificando se a conta existe no sistema
        if (contaDestinoEncontrada == null){
            System.out.println("***Conta inexistente.***");
            return;
        }
        //Verificando se é uma conta corrente adicional
        if (contaDestinoEncontrada.getTipo().equals("adicional")){
            System.out.println("***Não é possivel transferir para uma conta corrente adicional.***");
            return;
        }
        //Verificando se a conta de destino é a mesma da de origem
        if (numContaDestino.equals(contaEncontrada.getNumeroConta())) {
            System.out.println("***Não é possivel transferir para a mesma conta.***");
            return;
        }

        //Mostrando saldo disponivel da conta de origem
        System.out.printf("***Saldo disponivel da conta de origem: R$ %.2f***\n", contaEncontrada.getSaldo());

        //Verificando se é conta corrente
        if (contaEncontrada instanceof ContaCorrente contaCorrente) {
            //Verificando se possui cheque especial
            if (contaCorrente.isChequeEspecial()) {
                //Mostrando o limite do cheque especial
                System.out.printf("***Limite de cheque especial R$ %.2f***\n", contaCorrente.getLimiteChequeEspecial());
            }else {
                System.out.println("***Essa conta não possui cheque especial.***");
            }
        }

        //Lendo o valor da transferencia
        System.out.println("Informe o valor da tranferência:");
        double valorTransferencia = input.nextDouble();
        input.nextLine();
        //Erro caso o valor seja menor ou igual a 0
        if (valorTransferencia <= 0) {
            System.out.println("***Valor da tranferência deve ser maior que 0.***");
            return;
        }
        boolean senhaCorreta = false;
        //Loop para verificar se a senha esta correta
        while (!senhaCorreta) {
            //Lendo a senha
            System.out.println("Informe sua senha:");
            String senha = input.nextLine();
            //Erro para caso a senha esteja vazia ou tenha espaços
            if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                System.out.println("***Senha não pode estar vazia e/ou conter espaços. Tente novamente.***\n");
                return;
            }
            //Verificando se a senha informada esta correta
            if (contaEncontrada.autenticar(senha)) {
                //Chamando o metodo para transferir
                contaEncontrada.transferir(contaDestinoEncontrada, valorTransferencia);
                //Declarando a senha como verdadeira para encerrar o loop
                senhaCorreta = true;
            } else {
                System.out.println("***Senha incorreta. Tente novamente.***\n");
            }
        }
    }

    //Menu de opçoes para o correntista
    public void menuCorrentista() throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        boolean autenticado = false;
        //Loop para entrar na conta desejada
        while (!autenticado) {
            //Lendo o numero da conta
            System.out.println("Digite o número da sua conta:");
            String numConta = input.nextLine();
            //Declarando conta encontrada conforme numero lido
            Conta contaEncontrada = SistemaBanco.getContas().get(numConta);

            //Erro para caso o numero esteja vazio
            if (numConta == null || numConta.isEmpty()) {
                System.out.println("***O número da conta não pode estar vazio. Tente novamente.***\n");
                continue;
            }
            //Verificando se a conta existe no sistema
            if (contaEncontrada == null){
                System.out.println("***Conta inexistente.***");
                continue;
            }
            //Verificando se a conta pertence ao correntista
            if (!contaEncontrada.getTitular().equals(this.getNome())) {
                System.out.println("***Conta não encontrada ou não pertence a esse usuário.***");
                continue;
            }
            autenticado = true;

            int opcao = 0;
            //Loop para escolher uma opção
            do {
                System.out.print("""
                        Escolha a operação que deseja realizar:
                        [1] Ver saldo
                        [2] Realizar saque
                        [3] Realizar depósito
                        [4] Realizar transferência
                        [0] Sair
                        """);
                try {
                    //Leitura da opção escolhida
                    opcao = input.nextInt();
                    //Pulando uma linha para não dar erro no scanner
                    input.nextLine();
                    switch (opcao) {
                        case 1:
                            //Chama o metodo para mostrar o saldo da conta
                            mostrarSaldo(contaEncontrada);
                            break;
                        case 2:
                            //Chama o metodo para realizar saque
                            realizarSaque(contaEncontrada);
                            break;
                        case 3:
                            //Chama o metodo para cadastrar um correntista
                            realizarDeposito(contaEncontrada);
                            break;
                        case 4:
                            realizarTransferencia(contaEncontrada);
                            break;
                        case 0:
                            //Fecha o sistema
                            break;
                        default:
                            //Erro para caso digite uma opção invalida
                            System.out.println("***Opção invalida***\n");
                    }
                }catch (java.util.InputMismatchException e) {
                    //Erro para caso não seja digitado um número na opção
                    System.out.println("***Entrada inválida. Por favor, insira um número.***\n");
                    input.nextLine();
                }
            } while (opcao != 0);
        }
        //Fecha o Scanner
        input.close();
    }

    @Override
    public String toString() {
        return super.toString() + " - " + this.getNumContaCorrente() + " - " + this.getNumContaPoupanca() + " - "
                + this.getNumContaAdicional();
    }
}
