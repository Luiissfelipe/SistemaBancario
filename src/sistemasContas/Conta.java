package sistemasContas;

import java.util.Scanner;

public abstract class Conta {
	protected String numeroConta;
	protected double saldo;
	protected String titular;
	protected String senha;
	
	public Conta (String numeroConta, double saldo, String titular) {
		this.numeroConta = numeroConta;
		this.saldo = saldo;
		this.titular = titular;
		this.senha = senha;
		
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public void depositar() {
		
		Scanner leitor = new Scanner (System.in);
		System.out.println("Informe o valor do depósito: ");
		double valor = leitor.nextDouble();		
		leitor.close();
				
		if (valor > 0) {
			saldo += valor;
			System.out.println("Depósito realizado com sucesso.");
			System.out.println("Seu novo saldo é de R$" + saldo);
		} else {
			System.out.println("Valor de depósito inávlido.");
		}
		
		/*
		 * 
		 * Aqui será feita a manipulação do arquivo de texto
		 * 
		 */
	}
		
	public abstract void sacar();
	public abstract void transferir();
	public abstract void manipularArquivo();
	
}
