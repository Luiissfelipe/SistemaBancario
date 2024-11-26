package contas;

import sistema.SistemaBanco;

import java.io.IOException;

public class ContaCorrente extends Conta{
    //Atributos da conta
    private boolean chequeEspecial;
    private double limiteChequeEspecial;

    //Construtor da conta corrente, ja cria com o tipo de conta
    public ContaCorrente(String numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "corrente");
    }

    //Getters e Setters
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

    //Metodo para realizar saque
    @Override
    public void sacar(double valorSaque) throws IOException {
        //Declarando a conta corrente
        ContaCorrente contaCorrente = (ContaCorrente) SistemaBanco.getContas().get(this.getNumeroConta());
        //Declarando o saldo da conta corrente
        double saldo = this.getSaldo();

        //Verificando se possui cheque especial
        if (isChequeEspecial()) {
            //Verificando se o saldo e limite do cheque especial são suficientes
            if (saldo + getLimiteChequeEspecial() >= valorSaque) {
                saldo -= valorSaque;
                //Atualizando o saldo da conta
                this.setSaldo(saldo);
                //Atualizando a conta no sistema
                SistemaBanco.adicionarConta(contaCorrente);
                System.out.printf("***Saque de R$ %.2f realizado com sucesso.***\n", valorSaque);
            } else {
                System.out.println("***Saldo insuficiente.***");
            }
        }else {
            //Verificando se o saldo é suficiente
            if (saldo >= valorSaque) {
                saldo -= valorSaque;
                //Atualizando o saldo da conta
                contaCorrente.setSaldo(saldo);
                //Atualizando a conta no sistema
                SistemaBanco.adicionarConta(contaCorrente);
                System.out.printf("***Saque de R$ %.2f realizado com sucesso.***\n", valorSaque);
            } else {
                System.out.println("***Saldo insuficiente.***");
            }
        }
    }

    //Metodo para realizar transferencia
    @Override
    public void transferir(Conta contaDestino, Double valorTransferencia) throws IOException {
        //Declarando a conta corrente
        ContaCorrente contaCorrente = (ContaCorrente) SistemaBanco.getContas().get(this.getNumeroConta());
        //Declarando o saldo da conta corrente
        double saldo = contaCorrente.getSaldo();

        //Verificando se possui cheque especial
        if (isChequeEspecial()) {
            //Verificando se o saldo e limite do cheque especial esta disponivel
            if (saldo + getLimiteChequeEspecial() >= valorTransferencia) {
                saldo -= valorTransferencia;
                //Atualizando o saldo da conta
                contaCorrente.setSaldo(saldo);
                //Atualizando a conta no sistema
                SistemaBanco.adicionarConta(contaCorrente);
                //Realizando deposito na conta de destino
                contaDestino.depositar(valorTransferencia);
                System.out.printf("***Transferência de R$ %.2f realizada com sucesso.***\n", valorTransferencia);
            } else {
                System.out.println("***Saldo insuficiente.***");
            }
        }else {
            //Verificando se o saldo esta disponivel
            if (saldo >= valorTransferencia) {
                saldo -= valorTransferencia;
                //Atualizando o saldo da conta
                contaCorrente.setSaldo(saldo);
                //Atualizando a conta no sistema
                SistemaBanco.adicionarConta(contaCorrente);
                //Realizando deposito na conta de destino
                contaDestino.depositar(valorTransferencia);
                System.out.printf("***Transferência de R$ %.2f realizada com sucesso.***\n", valorTransferencia);
            } else {
                System.out.println("***Saldo insuficiente.***");
            }
        }
    }

    //Metodo para calcular divida do cheque especial
    public void calcularDividaChequeEspecial(double taxaRendimento, int meses) {
        //Declarando o saldo do cheque especial
        double saldoChequeEspecial = this.getSaldo();

        //Realizando operação de juros compostos
        for (int i = 0; i < meses; i++) {
            saldoChequeEspecial = saldoChequeEspecial + ((taxaRendimento / 100) * saldoChequeEspecial);
        }

        System.out.printf("***O valor da dívida será de R$ %.2f, após %d meses.***\n", Math.abs(saldoChequeEspecial), meses);

    }

    @Override
    public String toString() {
        return "Titular: " + this.getTitular() + "," + this.getNumeroConta() + "," + this.getSenha() + "," + this.getSaldo()
                + "," + this.getTipo() + "," + this.isChequeEspecial() + "," + this.getLimiteChequeEspecial();
    }
}
