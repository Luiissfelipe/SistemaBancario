package contas;

import sistema.SistemaBanco;

import java.io.IOException;

public class ContaPoupanca extends Conta{

    //Construtor da conta poupança, ja cria com o tipo de conta
    public ContaPoupanca(String numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "poupanca");
    }

    //Metodo para realizar saque
    @Override
    public void sacar(double valorSaque) throws IOException {
        //Declarando a conta poupança
        ContaPoupanca contaPoupanca = (ContaPoupanca) SistemaBanco.getContas().get(this.getNumeroConta());
        //Declarando o saldo da conta poupança
        double saldo = contaPoupanca.getSaldo();

        //Verificando se o saldo é suficiente
        if (saldo >= valorSaque) {
            saldo -= valorSaque;
            //Atualizando o saldo da conta
            this.setSaldo(saldo);
            //Atualizando a conta no sistema
            SistemaBanco.adicionarConta(contaPoupanca);
            System.out.printf("***Saque de R$ %.2f realizado com sucesso.***\n", valorSaque);
        } else {
            System.out.println("***Saldo insuficiente.***");
        }
    }

    //Metodo para realizar transferencia
    @Override
    public void transferir(Conta contaDestino, Double valorTransferencia) throws IOException {
        //Declarando a conta poupança
        ContaPoupanca contaPoupanca = (ContaPoupanca) SistemaBanco.getContas().get(this.getNumeroConta());
        //Declarando o saldo da conta poupança
        double saldo = contaPoupanca.getSaldo();

        //Verificando se o saldo é suficiente
        if (saldo >= valorTransferencia) {
            saldo -= valorTransferencia;
            //Atualizando o saldo da conta
            this.setSaldo(saldo);
            //Atualizando a conta no sistema
            SistemaBanco.adicionarConta(contaPoupanca);
            //Realizando deposito na conta de destino
            contaDestino.depositar(valorTransferencia);
            System.out.printf("***Transferência de R$ %.2f realizada com sucesso.***\n", valorTransferencia);
        } else {
            System.out.println("***Saldo insuficiente.***");
        }
    }

    //Metodo para calcular rendimento
    public void calcularRendimento(double taxaRendimento, int meses) {
        //Declarando o saldo da conta
        double saldo = this.getSaldo();

        //Realizando operação de juros compostos
        for (int i = 0; i < meses; i++) {
            saldo = saldo + ((taxaRendimento / 100) * saldo);
        }

        System.out.printf("***O valor do saldo será de R$ %.2f, após %d meses.***\n", saldo, meses);

    }
}
