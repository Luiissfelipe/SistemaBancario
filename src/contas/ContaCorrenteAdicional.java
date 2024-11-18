package contas;

import sistema.SistemaBanco;
import usuarios.Correntista;

import java.io.IOException;

public class ContaCorrenteAdicional extends Conta{

    public ContaCorrenteAdicional(String numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "adicional");
    }

    @Override
    public void sacar(Conta conta, double valorSaque) throws IOException {
        ContaCorrenteAdicional contaCorrenteAdicional = (ContaCorrenteAdicional) conta;
        String titular = contaCorrenteAdicional.getTitular();
        Correntista correntista = (Correntista) SistemaBanco.getUsuarios().get(titular);
        String numContaCorrenteTitular = correntista.getNumContaCorrente();

        double saldoContaCorrente = SistemaBanco.getContas().get(numContaCorrenteTitular).getSaldo();
        double saldoContaAdicional = contaCorrenteAdicional.getSaldo();

        if (saldoContaCorrente < valorSaque) {
            System.out.println("O saldo da conta corrente é insuficiente.");
        } else if (saldoContaAdicional < valorSaque) {
            System.out.println("O limite da conta adicional é insuficiente.");
        } else {
            saldoContaAdicional -= valorSaque;
            contaCorrenteAdicional.setSaldo(saldoContaAdicional);
            SistemaBanco.adicionarConta(contaCorrenteAdicional);

            ContaCorrente contaCorrente = (ContaCorrente) SistemaBanco.getContas().get(numContaCorrenteTitular);
            saldoContaCorrente -= valorSaque;
            contaCorrente.setSaldo(saldoContaCorrente);
            SistemaBanco.adicionarConta(contaCorrente);
            System.out.println("Saque realizado com sucesso.");
            System.out.printf("Seu novo saldo será de R$ %.2f\n", contaCorrenteAdicional.getSaldo());
        }
    }

}
