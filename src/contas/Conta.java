package contas;

import sistema.SistemaBanco;

import java.io.IOException;

public abstract class Conta {
    //Atributos da conta
    private String numeroConta;
    private String titular;
    private String senha;
    private double saldo;
    private String tipo;

    //Construtor da conta
    public Conta(String numeroConta, String titular, String senha, String tipo) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.senha = senha;
        this.tipo = tipo;
        this.saldo = 0.0;
    }

    //Getters e Setters
    public String getNumeroConta() {
        return numeroConta;
    }

    public String getTitular() {
        return titular;
    }

    public String getSenha() {
        return senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    //Metodo para conferir se a senha informada é igual a da conta
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    //Metodo para realizar saque
    public abstract void sacar(double valorSaque) throws IOException;

    //Metodo para realizar deposito
    public void depositar(double valorDeposito) throws IOException {
        double novoSaldo = this.getSaldo() + valorDeposito;
        //Atualizando saldo da conta
        this.setSaldo(novoSaldo);
        //Declarando conta
        Conta conta = SistemaBanco.getContas().get(this.getNumeroConta());
        //Atualizando conta no sistema
        SistemaBanco.adicionarConta(conta);
    }

    //Metodo para realizar transferencia
    public abstract void transferir(Conta contaDestino, Double valorTransferencia) throws IOException;

    @Override
    public String toString() {
        return "Titular: " + this.getTitular() + "," + this.getNumeroConta() + "," + this.getSenha() + "," + this.getSaldo()
                + "," + this.getTipo();
    }
}
