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
            System.out.println("Saque realizado com sucesso.");
            System.out.printf("Seu novo saldo será de R$ %.2f\n", contaPoupanca.getSaldo());
        } else {
            System.out.println("Saldo insuficiente.");
        }
    }

    public void calcularRendimento(ContaPoupanca contaPoupanca, double taxaRendimento, int meses) {
        double saldoRendimento = contaPoupanca.getSaldo();

        for (int i = 0; i < meses; i++) {
            saldoRendimento = saldoRendimento + ((taxaRendimento / 100) * saldoRendimento);
        }

        System.out.printf("O valor do saldo após %d meses será de R$ %.2f\n", meses, saldoRendimento);

    }
}
