package usuarios;

import contas.Conta;
import contas.ContaCorrente;
import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Correntista extends Usuario{

    private String numContaCorrente;
    private String numContaPoupanca;
    private String numContaAdicional;

    public Correntista(String nome, String senha) {
        super(nome, senha, "correntista");
    }

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

    public void gerarNumContaCorrente() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaCorrente = "CC" + numero;
    }

    public void gerarNumContaPoupanca() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaPoupanca = "CP" + numero;
    }

    public void gerarNumContaAdicional() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaAdicional = "CA" + numero;
    }

    private void mostrarSaldo(Conta contaEncontrada) {
        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.printf("O limite da sua conta adicional é R$ %.2f\n", contaEncontrada.getSaldo());
        } else {
            System.out.printf("O saldo da conta é R$ %.2f\n", contaEncontrada.getSaldo());
        }
    }

    private void realizarSaque(Conta contaEncontrada) throws IOException {
        Scanner input = new Scanner(System.in);

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
                    contaEncontrada.sacar(valorSaque);
                    senhaCorreta = true;
                } else {
                    System.out.println("Senha incorreta. Tente novamente.\n");
                }
            }
        }
    }

    private void realizarDeposito(Conta contaEncontrada) throws IOException {
        Scanner input = new Scanner(System.in);

        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("Você não pode depositar em uma conta adicional.");
            return;
        }
        System.out.println("Informe o valor do depósito:");
        double valorDeposito = input.nextDouble();

        if (valorDeposito <= 0) {
            System.out.println("O valor do deposito deve ser maior que 0.");
        } else {
            contaEncontrada.depositar(valorDeposito);
            System.out.printf("Deposito de R$ %.2f realizado com sucesso.\n", valorDeposito);
        }
    }

    private void realizarTransferencia(Conta contaEncontrada) throws IOException {
        Scanner input = new Scanner(System.in);

        if (contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("Não é possível realizar transferência de uma conta corrente adicional.");
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
        if (numContaDestino.equals(contaEncontrada.getNumeroConta())) {
            System.out.println("Não é possivel transferir para a mesma conta.");
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
            System.out.println("Informe sua senha:");
            String senha = input.nextLine();

            if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                System.out.println("Senha não pode estar vazia e/ou conter espaços. Tente novamente.\n");
                return;
            }
            if (contaEncontrada.autenticar(senha)) {
                contaEncontrada.transferir(contaDestinoEncontrada, valorTransferencia);
                senhaCorreta = true;
            } else {
                System.out.println("Senha incorreta. Tente novamente.\n");
            }
        }
    }

    //Menu de opçoes para o correntista
    public void menuCorrentista() throws IOException {
        Scanner input = new Scanner(System.in);



        boolean autenticado = false;
        while (!autenticado) {
            System.out.println("Digite o número da sua conta:");
            String numConta = input.nextLine();
            Conta contaEncontrada = SistemaBanco.getContas().get(numConta);

            if (numConta == null || numConta.isEmpty()) {
                System.out.println("O número da conta não pode estar vazio. Tente novamente.\n");
                continue;
            }
            if (contaEncontrada == null){
                System.out.println("Conta inexistente.");
                continue;
            }

            if (!contaEncontrada.getTitular().equals(this.getNome())) {
                System.out.println("Conta não encontrada ou não pertence a esse usuário.");
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
                            System.out.println("Opção invalida\n");
                    }
                }catch (java.util.InputMismatchException e) {
                    //Erro para caso não seja digitado um número na opção
                    System.out.println("Entrada inválida. Por favor, insira um número.\n");
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
