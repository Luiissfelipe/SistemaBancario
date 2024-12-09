package usuarios;

import contas.Conta;
import contas.ContaCorrente;
import contas.ContaCorrenteAdicional;
import contas.ContaPoupanca;
import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public class Bancario extends Usuario{
    //Classe Bancario herda atributos e metodos da classe Usuario

    //Construtor do Bancario, ja cria com o nivel de usuario
    public Bancario(String nome, String senha) {
        super(nome, senha, "bancario");
    }

    //Metodo para mostrar o saldo de uma conta
    private void mostrarSaldo() {
        //Declarando scanner
        Scanner input = new Scanner(System.in);

        //Lendo numero da conta
        System.out.println("Informe o número da conta:");
        String numConta = input.nextLine();

        //Declarando conta conforme numero lido
        Conta contaEncontrada = SistemaBanco.getContas().get(numConta);

        //Erro para caso o numero esteja vazio
        if (numConta.isEmpty()) {
            System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
            return;
        }
        //Verificando se a conta existe
        if (contaEncontrada == null){
            System.out.println("***Conta inexistente.***");
            return;
        }
        //Verificando se é uma conta adicional
        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.printf("***O limite da conta adicional é R$ %.2f***\n", contaEncontrada.getSaldo());
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
    private void realizarSaque() throws IOException {
        //Declarando scanner
        Scanner input = new Scanner(System.in);

        //Lendo numero da conta
        System.out.println("Informe o número da conta:");
        String numConta = input.nextLine();

        //Declarando conta conforme numero lido
        Conta contaEncontrada = SistemaBanco.getContas().get(numConta);

        //Erro para caso o numero esteja vazio
        if (numConta.isEmpty()) {
            System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
            return;
        }
        //Verificando se a conta existe
        if (contaEncontrada == null){
            System.out.println("***Conta inexistente.***");
            return;
        }

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
        if (valorSaque <= 0) {
            System.out.println("***Valor de saque deve ser maior que 0.***");
        } else {
            //Declarando senha como falsa
            boolean senhaCorreta = false;
            //Loop para digitar a senha
            while (!senhaCorreta) {
                //Lendo senha
                System.out.println("Informe a senha da conta:");
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
    private void realizarDeposito() throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        //Lendo o numero da conta
        System.out.println("Informe o número da conta:");
        String numConta = input.nextLine();
        //Declarando conta conforme o numero lido
        Conta contaEncontrada = SistemaBanco.getContas().get(numConta);

        //Erro para caso o numero esteja vazio
        if (numConta.isEmpty()) {
            System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
            return;
        }
        //Verificando se a conta existe no sistema
        if (contaEncontrada == null){
            System.out.println("***Conta inexistente.***");
            return;
        }
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
    private void realizarTransferencia() throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        //Lendo o numero da conta de origem
        System.out.println("Informe o número da conta de origem:");
        String numContaOrigem = input.nextLine();
        //Declarando conta de origem conforme o numero lido
        Conta contaOrigemEncontrada = SistemaBanco.getContas().get(numContaOrigem);

        //Erro para caso o numero esteja vazio
        if (numContaOrigem.isEmpty()) {
            System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
            return;
        }
        //Verificando se a conta existe no sistema
        if (contaOrigemEncontrada == null){
            System.out.println("***Conta inexistente.***");
            return;
        }
        //Verificando se é uma conta corrente adicional
        if (contaOrigemEncontrada.getTipo().equals("adicional")) {
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
        if (numContaDestino.equals(numContaOrigem)) {
            System.out.println("***Não é possivel transferir para a mesma conta.***");
            return;
        }

        //Mostrando saldo disponivel da conta de origem
        System.out.printf("***Saldo disponivel da conta de origem: R$ %.2f***\n", contaOrigemEncontrada.getSaldo());

        //Verificando se é conta corrente
        if (contaOrigemEncontrada instanceof ContaCorrente contaCorrente) {
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
        //Loop para verificar se a senha esta correta
        boolean senhaCorreta = false;
        while (!senhaCorreta) {
            //Lendo a senha
            System.out.println("Informe a senha da conta de origem:");
            String senha = input.nextLine();
            //Erro para caso a senha esteja vazia ou tenha espaços
            if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                System.out.println("***Senha não pode estar vazia e/ou conter espaços. Tente novamente.***\n");
                return;
            }
            //Verificando se a senha informada esta correta
            if (contaOrigemEncontrada.autenticar(senha)) {
                //Chamando o metodo para transferir
                contaOrigemEncontrada.transferir(contaDestinoEncontrada, valorTransferencia);
                //Declarando a senha como verdadeira para encerrar o loop
                senhaCorreta = true;
            } else {
                System.out.println("***Senha incorreta. Tente novamente.***\n");
            }
        }


    }

    //Metodo para calcular rendimento da conta poupança
    private void calcularRendimento() {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        boolean autenticado = false;

        //Loop para não voltar ao menu
        while (!autenticado) {
            //Lendo o numero da conta
            System.out.println("Informe o número da Conta Poupança:");
            String numContaPoupanca = input.nextLine();
            //Declarando conta conforme o numero lido
            Conta contaEncontrada = SistemaBanco.getContas().get(numContaPoupanca);

            //Erro para caso o numero esteja vazio
            if (numContaPoupanca.isEmpty()) {
                System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
                continue;
            }
            //Verificando se a conta existe no sistema
            if (contaEncontrada == null){
                System.out.println("***Conta inexistente.***");
                continue;
            }
            //Verificando se é uma conta poupança
            if (!contaEncontrada.getTipo().equals("poupanca")) {
                System.out.println("***O número informado não pertence a uma Conta Poupança***");
                continue;
            }

            //Loop para informar a taxa e a quantidade de meses
            while (!autenticado) {
                //Lendo o valor da taxa
                System.out.println("Informe a taxa de rendimento mensal: [%]");
                double taxaRendimento = input.nextDouble();
                //Erro caso a taxa seja menor que 0
                if (taxaRendimento < 0) {
                    System.out.println("***A taxa de rendimento não pode ser menor que 0.***");
                    continue;
                }
                //Lendo a quantidade de meses
                System.out.println("Informe a quantidade de meses de incidência");
                int meses = input.nextInt();
                //Erro caso seja menor ou igual a 0
                if (meses <= 0) {
                    System.out.println("*** quantidade de meses deve ser maior que 0.***");
                    continue;
                }

                //Declarando como true para fechar o loop
                autenticado = true;
                //Declarando a conta poupança
                ContaPoupanca contaPoupanca = (ContaPoupanca) contaEncontrada;
                //Informando o saldo da conta
                System.out.printf("***Saldo atual: R$ %.2f***\n", contaPoupanca.getSaldo());
                //Chamando o metodo para calcular o rendimento
                contaPoupanca.calcularRendimento(taxaRendimento, meses);
            }
        }
    }

    //Metodo para calcular a divida do cheque especial
    private void calcularDividaChequeEspecial() {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        boolean autenticado = false;
        //Loop para não voltar ao menu
        while (!autenticado) {
            //Lendo o numero da conta
            System.out.println("Informe o número da Conta Corrente:");
            String numContaCorrente = input.nextLine();
            //Declarando conta conforme o numero lido
            Conta contaEncontrada = SistemaBanco.getContas().get(numContaCorrente);

            //Erro para caso o numero esteja vazio
            if (numContaCorrente.isEmpty()) {
                System.out.println("***O número da conta não pode ser vazio. Tente novamente.***\n");
                continue;
            }
            //Verificando se a conta existe no sistema
            if (contaEncontrada == null){
                System.out.println("***Conta inexistente.***");
                continue;
            }
            //Verificando se é uma conta poupança
            if (!contaEncontrada.getTipo().equals("corrente")) {
                System.out.println("***Esse número não pertence a uma conta corrente.***");
                continue;
            }
            //Verificando se a conta possui cheque especial
            ContaCorrente contaCorrente = (ContaCorrente) contaEncontrada;
            if (!contaCorrente.isChequeEspecial()) {
                System.out.println("***Essa conta não possui opção de cheque especial.***");
                continue;
            }
            //Verificando se a conta está utilizando o cheque especial
            if (contaCorrente.getSaldo() >= 0) {
                System.out.println("***Essa conta não está utilizando o cheque especial.***");
                continue;
            }

            //Loop para informar a taxa e a quantidade de meses
            while (!autenticado) {
                //Lendo o valor da taxa
                System.out.println("Informe a taxa de juros mensal: [%]");
                double taxaJuros = input.nextDouble();
                //Erro caso a taxa seja menor que 0
                if (taxaJuros < 0) {
                    System.out.println("***A taxa de juros não pode ser menor que 0.***");
                    continue;
                }
                //Lendo a quantidade de meses
                System.out.println("Informe a quantidade de meses de incidência");
                int meses = input.nextInt();
                //Erro caso seja menor ou igual a 0
                if (meses <= 0) {
                    System.out.println("***A quantidade de meses deve ser maior que 0.***");
                    continue;
                }

                //Declarando como true para fechar o loop
                autenticado = true;
                //Informando o saldo da conta
                System.out.printf("***Essa conta está utilizando R$ %.2f de cheque especial.***\n", Math.abs(contaCorrente.getSaldo()));
                //Chamando o metodo para calcular a dívida
                contaCorrente.calcularDividaChequeEspecial(taxaJuros, meses);
            }
        }



    }

    //Menu de opçoes para o bancario
    public void menuBancario() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        //Loop para escolher uma opção
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Ver saldo de conta
                        [2] Realizar saque
                        [3] Realizar depósito
                        [4] Realizar transferência
                        [5] Calcular rendimento de conta poupança
                        [6] Calcular dívida do cheque especial
                        [0] Sair
                        """);
            try {
                //Leitura da opção escolhida
                opcao = input.nextInt();
                //Pulando uma linha para não dar erro no scanner
                input.nextLine();
                switch (opcao) {
                    case 1:
                        //Chama o metodo para mostrar o saldo
                        mostrarSaldo();
                        break;
                    case 2:
                        //Chama o metodo para realizar saque
                        realizarSaque();
                        break;
                    case 3:
                        //Chama o metodo para realizar deposito
                        realizarDeposito();
                        break;
                    case 4:
                        //Chama o metodo para realizar transferencia
                        realizarTransferencia();
                        break;
                    case 5:
                        //Chama o metoto para calcular o rendimento
                        calcularRendimento();
                        break;
                    case 6:
                        //Chama o metodo para calcular a divida
                        calcularDividaChequeEspecial();
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
        //Fecha o Scanner
        input.close();
    }
}
