package usuarios;

import contas.Conta;
import contas.ContaCorrente;
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
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se a conta existe
        if (contaEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }

        //Mostrando saldo disponivel
        System.out.printf("Saldo disponivel: R$ %.2f\n", contaEncontrada.getSaldo());

        //Lendo o valor do saque
        System.out.println("Informe o valor do saque:");
        double valorSaque = input.nextDouble();
        input.nextLine();

        //Erro para caso o valor do saque seja menor ou igual a 0
        if (valorSaque <= 0) {
            System.out.println("Valor de saque deve ser maior que 0.");
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
                    System.out.println("Senha não pode estar vazia e/ou conter espaços. Tente novamente.\n");
                    return;
                }
                //Verificando se a senha esta correta
                if (contaEncontrada.autenticar(senha)) {
                    //Chamando o metodo para sacar
                    contaEncontrada.sacar(contaEncontrada, valorSaque);
                    //Declarando senha como verdadeira para encerrar o loop
                    senhaCorreta = true;
                } else {
                    //Erro caso a senha estiver incorreta
                    System.out.println("Senha incorreta. Tente novamente.\n");
            }
        }
        }
    }

    //Metodo para realizar deposito
    private void realizarDeposito() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o número da conta:");
        String numConta = input.nextLine();
        Conta contaEncontrada = SistemaBanco.getContas().get(numConta);

        if (numConta.isEmpty()) {
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (contaEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }
        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("Você não pode depositar em uma conta adicional.");
            return;
        }

        System.out.println("Informe o valor do depósito:");
        double valorDeposito = input.nextDouble();

        if (valorDeposito <= 0) {
            System.out.println("O valor do deposito deve ser maior que 0.");
        } else {
            contaEncontrada.depositar(contaEncontrada, valorDeposito);
            System.out.println("Deposito realizado com sucesso.");
            System.out.printf("Seu novo saldo será de R$ %.2f\n", contaEncontrada.getSaldo());
        }

    }

    //Metodo para realizar transferencia
    private void realizarTransferencia() throws IOException {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o número da conta de origem:");
        String numContaOrigem = input.nextLine();
        Conta contaOrigemEncontrada = SistemaBanco.getContas().get(numContaOrigem);

        if (numContaOrigem.isEmpty()) {
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (contaOrigemEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }
        if (!contaOrigemEncontrada.getTipo().equals("corrente")) {
            System.out.println("Só é possível realizar transferência de uma conta corrente.");
            return;
        }

        System.out.println("Informe o número da conta de destino:");
        String numContaDestino = input.nextLine();
        Conta contaDestinoEncontrada = SistemaBanco.getContas().get(numContaDestino);

        if (numContaDestino.isEmpty()) {
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (contaDestinoEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }
        if (contaDestinoEncontrada.getTipo().equals("adicional")){
            System.out.println("Não é possivel transferir para uma conta corrente adicional.");
            return;
        }

        System.out.println("Informe o valor da tranferência:");
        double valorTransferencia = input.nextDouble();
        input.nextLine();
        if (valorTransferencia <= 0) {
            System.out.println("Valor da tranferência deve ser maior que 0.");
        }
        boolean senhaCorreta = false;
        while (!senhaCorreta) {
            System.out.println("Informe a senha da conta de origem:");
            String senha = input.nextLine();

            if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                System.out.println("Senha não pode estar vazia e/ou conter espaços. Tente novamente.\n");
                return;
            }
            if (contaOrigemEncontrada.autenticar(senha)) {
                ContaCorrente contaCorrente = (ContaCorrente) contaOrigemEncontrada;
                contaCorrente.transferir(contaOrigemEncontrada, contaDestinoEncontrada, valorTransferencia);
                senhaCorreta = true;
            } else {
                System.out.println("Senha incorreta. Tente novamente.\n");
            }
        }


    }

    //Metodo para calcular rendimento da conta poupança
    private void calcularRendimento() {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o número da Conta Poupança:");
        String numContaPoupanca = input.nextLine();
        Conta contaEncontrada = SistemaBanco.getContas().get(numContaPoupanca);

        if (numContaPoupanca.isEmpty()) {
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        if (contaEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }
        if (!contaEncontrada.getTipo().equals("poupanca")) {
            System.out.println("Informe uma Conta Poupança");
            return;
        }

        System.out.println("Informe a taxa de rendimento mensal: [%]");
        double taxaRendimento = input.nextDouble();
        if (taxaRendimento < 0) {
            System.out.println("A taxa de rendimento não pode ser menor que 0.");
            return;
        }
        System.out.println("Informe a quantidade de meses incindência");
        int meses = input.nextInt();

        ContaPoupanca contaPoupanca = (ContaPoupanca) contaEncontrada;
        contaPoupanca.calcularRendimento(contaPoupanca, taxaRendimento, meses);

    }

    //Menu de opçoes para o bancario
    public void menuBancario() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        //Loop para escolher uma opção
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Realizar saque
                        [2] Realizar depósito
                        [3] Realizar transferência
                        [4] Calcular rendimento de conta poupança
                        [0] Sair
                        """);
            try {
                //Leitura da opção escolhida
                opcao = input.nextInt();
                //Pulando uma linha para não dar erro no scanner
                input.nextLine();
                switch (opcao) {
                    case 1:
                        //Chama o metodo para realizar saque
                        realizarSaque();
                        break;
                    case 2:
                        //Chama o metodo para realizar deposito
                        realizarDeposito();
                        break;
                    case 3:
                        //Chama o metodo para realizar transferencia
                        realizarTransferencia();
                        break;
                    case 4:
                        calcularRendimento();
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
