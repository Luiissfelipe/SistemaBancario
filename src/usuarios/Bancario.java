package usuarios;

import java.io.IOException;
import java.util.Scanner;

public class Bancario extends Usuario{
    //Classe Bancario herda atributos e metodos da classe Usuario

    //Construtor do Bancario, ja cria com o nivel de usuario
    public Bancario(String nome, String senha) {
        super(nome, senha, "bancario");
    }

    //Metodo para realizar saque
    private void realizarSaque() {
        System.out.println("Realizando saque.");
    }

    //Metodo para realizar deposito
    private void realizarDeposito() {
        System.out.println("Realizando deposito.");
    }

    //Metodo para realizar transferencia
    private void realizarTransferencia() {
        System.out.println("Realizando transferencia.");
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
