package contas;

import sistema.SistemaBanco;
import usuarios.Correntista;

import java.io.IOException;

public class ContaCorrenteAdicional extends Conta{
    //Atributos da conta
    private String dependente;

    //Construtor da conta corrente adicional, ja cria com o tipo de conta
    public ContaCorrenteAdicional(String numeroConta, String titular, String senha) {
        super(numeroConta, titular, senha, "adicional");
    }

    //Getters e Setters
    public String getDependente() {
        return dependente;
    }

    public void setDependente(String dependente) {
        this.dependente = dependente;
    }

    //Metodo para realizar saque
    @Override
    public void sacar(double valorSaque) throws IOException {
        //Declarando conta corrente adicional
        ContaCorrenteAdicional contaAdicional = (ContaCorrenteAdicional) SistemaBanco.getContas().get(this.getNumeroConta());
        //Declarando o nome do titular da conta
        String titular = this.getTitular();
        //Declarando o correntista
        Correntista correntista = (Correntista) SistemaBanco.getUsuarios().get(titular);
        //Declarando o numero da conta corrente do titular
        String numContaCorrenteTitular = correntista.getNumContaCorrente();
        //Declarando conta corrente
        ContaCorrente contaCorrente = (ContaCorrente) SistemaBanco.getContas().get(numContaCorrenteTitular);

        //Declarando saldo da conta corrente
        double saldoContaCorrente = SistemaBanco.getContas().get(numContaCorrenteTitular).getSaldo();
        //Declarando limite da conta adicional
        double limiteContaAdicional = contaAdicional.getSaldo();

        //Verificando se o saldo da conta corrente é suficiente
        if (saldoContaCorrente < valorSaque) {
            System.out.println("***O saldo da conta corrente é insuficiente.***");
        }
        //Verificando se o limite da conta adicional é suficiente
        else if (limiteContaAdicional < valorSaque) {
            System.out.println("***O limite da conta adicional é insuficiente.***");
        } else {
            limiteContaAdicional -= valorSaque;
            //Atualizando o saldo da conta adicional
            this.setSaldo(limiteContaAdicional);
            //Atualizando a conta no sistema
            SistemaBanco.adicionarConta(contaAdicional);

            saldoContaCorrente -= valorSaque;
            //Atualizando o saldo da conta corrente
            contaCorrente.setSaldo(saldoContaCorrente);
            //Atualizando a conta no sistema
            SistemaBanco.adicionarConta(contaCorrente);
            System.out.printf("***Saque de R$ %.2f realizado com sucesso.***\n", valorSaque);
        }
    }

    @Override
    public void transferir(Conta contaDestinoEncontrada, Double valorTransferencia) throws IOException {
        System.out.println("***Não é possivel realizar transferencia de um conta adicional.***");
    }

}
