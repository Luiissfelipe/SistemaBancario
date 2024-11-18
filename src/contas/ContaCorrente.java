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
                System.out.println("Saque realizado com sucesso.");
                System.out.printf("Seu novo saldo será de R$ %.2f\n", contaCorrente.getSaldo());
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }else {
            if (saldo >= valorSaque) {
                saldo -= valorSaque;
                contaCorrente.setSaldo(saldo);
                SistemaBanco.adicionarConta(contaCorrente);
                System.out.println("Saque realizado com sucesso.");
                System.out.printf("Seu novo saldo será de R$ %.2f\n", contaCorrente.getSaldo());
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }
    }

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
                System.out.println("Transferência realizada com sucesso.");
                System.out.printf("Novo saldo da conta de origem: R$ %.2f\n", contaOrigemEncontrada.getSaldo());
                System.out.printf("Novo saldo da conta de destino: R$ %.2f\n", contaDestinoEncontrada.getSaldo());
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }else {
            if (saldo >= valorTransferencia) {
                saldo -= valorTransferencia;
                contaCorrente.setSaldo(saldo);
                SistemaBanco.adicionarConta(contaCorrente);
                depositar(contaDestinoEncontrada, valorTransferencia);
                System.out.println("Transferência realizada com sucesso.");
                System.out.printf("Novo saldo da conta de origem: R$ %.2f\n", contaOrigemEncontrada.getSaldo());
                System.out.printf("Novo saldo da conta de destino: R$ %.2f\n", contaDestinoEncontrada.getSaldo());
            } else {
                System.out.println("Saldo insuficiente.");
            }
        }
    }

    @Override
    public String toString() {
        return "Titular: " + this.getTitular() + "," + this.getNumeroConta() + "," + this.getSenha() + "," + this.getSaldo()
                + "," + this.getTipo() + "," + this.isChequeEspecial() + "," + this.getLimiteChequeEspecial();
    }
}
