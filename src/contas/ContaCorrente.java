package contas;

import sistema.SistemaBanco;

import java.io.IOException;

public class ContaCorrente extends Conta{

    private boolean chequeEspecial;
    private double limiteChequeEspecial;

    public ContaCorrente(String numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "corrente");
    }

    public boolean isChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(boolean chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }

    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        this.limiteChequeEspecial = limiteChequeEspecial;
    }

    @Override
    public void sacar(Conta conta, double valorSaque) throws IOException {
        ContaCorrente contaCorrente = (ContaCorrente) conta;
        double saldo = contaCorrente.getSaldo();
        boolean isChequeEspecial = contaCorrente.isChequeEspecial();
        double limiteChequeEspecial = contaCorrente.getLimiteChequeEspecial();

        if (isChequeEspecial) {
            if (saldo + limiteChequeEspecial >= valorSaque) {
                saldo -= valorSaque;
                contaCorrente.setSaldo(saldo);
                SistemaBanco.adicionarConta(contaCorrente);
                System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valorSaque);
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }else {
            if (saldo >= valorSaque) {
                saldo -= valorSaque;
                contaCorrente.setSaldo(saldo);
                SistemaBanco.adicionarConta(contaCorrente);
                System.out.printf("Saque de R$ %.2f realizado com sucesso.\n", valorSaque);
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }
    }

    @Override
    public void transferir(Conta contaOrigemEncontrada, Conta contaDestinoEncontrada, Double valorTransferencia) throws IOException {
        ContaCorrente contaCorrente = (ContaCorrente) contaOrigemEncontrada;
        double saldo = contaCorrente.getSaldo();
        boolean isChequeEspecial = contaCorrente.isChequeEspecial();
        double limiteChequeEspecial = contaCorrente.getLimiteChequeEspecial();

        if (isChequeEspecial) {
            if (saldo + limiteChequeEspecial >= valorTransferencia) {
                saldo -= valorTransferencia;
                contaCorrente.setSaldo(saldo);
                SistemaBanco.adicionarConta(contaCorrente);
                depositar(contaDestinoEncontrada, valorTransferencia);
                System.out.printf("Transferência de R$ %.2f realizada com sucesso.\n", valorTransferencia);
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }else {
            if (saldo >= valorTransferencia) {
                saldo -= valorTransferencia;
                contaCorrente.setSaldo(saldo);
                SistemaBanco.adicionarConta(contaCorrente);
                depositar(contaDestinoEncontrada, valorTransferencia);
                System.out.printf("Transferência de R$ %.2f realizada com sucesso.\n", valorTransferencia);
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }
    }

    public void calcularDividaChequeEspecial(double taxaRendimento, int meses) {
        double saldoChequeEspecial = this.getSaldo();

        for (int i = 0; i < meses; i++) {
            saldoChequeEspecial = saldoChequeEspecial + ((taxaRendimento / 100) * saldoChequeEspecial);
        }

        System.out.printf("O valor da dívida será de R$ %.2f, após %d meses.\n", Math.abs(saldoChequeEspecial), meses);

    }

    @Override
    public String toString() {
        return "Titular: " + this.getTitular() + "," + this.getNumeroConta() + "," + this.getSenha() + "," + this.getSaldo()
                + "," + this.getTipo() + "," + this.isChequeEspecial() + "," + this.getLimiteChequeEspecial();
    }
}
