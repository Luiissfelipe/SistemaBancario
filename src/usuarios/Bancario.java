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

        System.out.println("Informe o valor do saque:");
        double valorSaque = input.nextDouble();
        input.nextLine();
        if (valorSaque < 0) {
            System.out.println("Valor de saque deve ser maior que 0.");
        } else {
            boolean senhaCorreta = false;
            while (!senhaCorreta) {
                System.out.println("Informe sua senha:");
                String senha = input.nextLine();

                if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                    System.out.println("Senha não pode estar vazia e/ou conter espaços. Tente novamente.\n");
                    return;
                }
                if (contaEncontrada.autenticar(senha)) {
                    contaEncontrada.sacar(contaEncontrada, valorSaque);
                    senhaCorreta = true;
                } else {
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

        System.out.println("Informe o valor do depósito:");
        double valorDeposito = input.nextDouble();

        if (valorDeposito < 0) {
            System.out.println("O valor do deposito deve ser maior que 0.");
        } else {
            contaEncontrada.depositar(contaEncontrada, valorDeposito);
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

        System.out.println("Informe o valor da tranferência:");
        double valorTransferencia = input.nextDouble();
        input.nextLine();
        if (valorTransferencia < 0) {
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
                contaOrigemEncontrada.transferir(contaOrigemEncontrada, contaDestinoEncontrada, valorTransferencia);
                senhaCorreta = true;
            } else {
                System.out.println("Senha incorreta. Tente novamente.\n");
            }
        }


    }

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
        if (contaEncontrada.getTipo() != "poupanca") {
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

    //Metodo para escolher a conta que vai ser realizada as operações
    private void escolherConta() {
        System.out.println("Insira o número da conta que deseja realizar uma operação:");
    }

    //Menu de opçoes para o bancario
    public void menuBancario() throws IOException {
        Scanner input = new Scanner(System.in);
        escolherConta();
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
