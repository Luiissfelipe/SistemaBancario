package contas;

import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public abstract class Conta {
    private String numeroConta;
    private String titular;
    private String senha;
    private double saldo;
    private String tipo;

    public Conta(String numeroConta, String titular, String senha, String tipo) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.senha = senha;
        this.tipo = tipo;
        this.saldo = 0.0;
    }

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

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    public abstract void sacar(Conta conta, double valorSaque) throws IOException;

    public void depositar(Conta conta, double valorDeposito) throws IOException {
        double novoSaldo = conta.getSaldo() + valorDeposito;
        conta.setSaldo(novoSaldo);
        SistemaBanco.adicionarConta(conta);
    }

    @Override
    public String toString() {
        return "Titular: " + this.getTitular() + "," + this.getNumeroConta() + "," + this.getSenha() + "," + this.getSaldo()
                + "," + this.getTipo();
    }
}
