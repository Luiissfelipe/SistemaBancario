package contas;

import sistema.SistemaBanco;

import java.io.IOException;

public class ContaPoupanca extends Conta{

    public ContaPoupanca(String numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "poupanca");
    }

    @Override
    public void sacar(Conta conta, double valorSaque) throws IOException {
        ContaPoupanca contaPoupanca = (ContaPoupanca) conta;
        double saldo = contaPoupanca.getSaldo();

        if (saldo >= valorSaque) {
            saldo -= valorSaque;
            contaPoupanca.setSaldo(saldo);
            SistemaBanco.adicionarConta(contaPoupanca);
            System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valorSaque);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    @Override
    public void transferir(Conta contaOrigemEncontrada, Conta contaDestinoEncontrada, Double valorTransferencia) throws IOException {
        ContaPoupanca contaPoupanca = (ContaPoupanca) contaOrigemEncontrada;
        double saldo = contaPoupanca.getSaldo();

        if (saldo >= valorTransferencia) {
            saldo -= valorTransferencia;
            contaPoupanca.setSaldo(saldo);
            SistemaBanco.adicionarConta(contaPoupanca);
            depositar(contaDestinoEncontrada, valorTransferencia);
            System.out.printf("Transferência de R$ %.2f realizada com sucesso.\n", valorTransferencia);
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void calcularRendimento(ContaPoupanca contaPoupanca, double taxaRendimento, int meses) {
        double saldoRendimento = contaPoupanca.getSaldo();

        for (int i = 0; i < meses; i++) {
            saldoRendimento = saldoRendimento + ((taxaRendimento / 100) * saldoRendimento);
        }

        System.out.printf("O valor do saldo será de R$ %.2f, após %d meses \n", saldoRendimento, meses);

    }
}
