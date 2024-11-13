package usuarios;

import java.io.IOException;
import java.util.Scanner;

public class Bancario extends Usuario{

    public Bancario(String nome, String senha) {
        super(nome, senha, "bancario");
    }

    private void realizarSaque() {
        System.out.println("Realizando saque.");
    }

    private void realizarDeposito() {
        System.out.println("Realizando deposito.");
    }

    private void realizarTransferencia() {
        System.out.println("Realizando transferencia.");
    }

    private void escolherConta() {
        System.out.println("Insira o número da conta que deseja realizar uma operação:");
    }

    public void menuBancario() throws IOException {
        Scanner input = new Scanner(System.in);
        escolherConta();
        int opcao = 0;
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Realizar saque
                        [2] Realizar depósito
                        [3] Realizar transferência
                        [0] Sair
                        """);
            try {
                opcao = input.nextInt();
                input.nextLine();
                switch (opcao) {
                    case 1:
                        realizarSaque();
                        break;
                    case 2:
                        realizarDeposito();
                        break;
                    case 3:
                        realizarTransferencia();
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
