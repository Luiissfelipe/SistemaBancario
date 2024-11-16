package contas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Criando a classe conta
public abstract class Conta {
	String numeroConta;
	String titular;
	String senha;
	static double saldo;
	String tipo;
	
	// Método construtor
	public Conta (String numeroConta, String titular, String senha, double saldo, String tipo){
		this.numeroConta = numeroConta;
		this.titular = titular;
		this.senha = senha;
		this.saldo = saldo;
		this.tipo = tipo;
	}

	// Métodos Getters e Setters
	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	// Método para realizar depósitos
	public static void depositar() {
	
		// Criando o leitor
		Scanner scanner = new Scanner(System.in);
		
		// Obtendo dados do usuário
		System.out.println("Informe o número da conta:");
		String contaInformada = scanner.nextLine();
		
		System.out.println("Informe o valor do depósito:");
		double valorDeposito = scanner.nextDouble();
		
		// Encerrando o leitor
		scanner.close();
		
		if (valorDeposito > 0) {
			
			// Lista para armazenar os dados atualizados
			List<String[]> dadosAtualizados = new ArrayList<>();
			
			String arquivo = "C:\\Users\\Client\\eclipse-workspace\\Contas\\src\\dadosContas.csv";
			boolean contaEncontrada = false;
			
			try (BufferedReader br = new BufferedReader(new FileReader(arquivo))){
				String linha;
				
				// Lê o arquivo linha por linha
				while ((linha = br.readLine()) != null) {
					String[] campos = linha.split(",");
					
					// Verifica se o número da conta corresponde ao informado
					if (campos[0].equals(contaInformada)) {
						saldo = Double.parseDouble(campos[3]);
						saldo += valorDeposito;
						campos[3] = String.valueOf(saldo);
						contaEncontrada = true;
					} 
					
					// Adiciona os dados à lista
					dadosAtualizados.add(campos);
				} 
				
			} catch (IOException e) {
				System.out.println("Erro ao ler o arquivo: " + e.getMessage());
				return;
			}
			
			if (!contaEncontrada) {
				System.out.println("Conta não encontrada");
				return;
			}
			
			// Escreve os dados atualizados de volta no arquivo
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))){
				for (String[] campos : dadosAtualizados) {
					bw.write(String.join(",", campos));
					bw.newLine();
				}
			} catch (IOException e) {
				System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
				return;
			}
			
				System.out.println("Depósito realizado com sucesso.");
				System.out.println("Seu novo saldo é de R$" + saldo);
				
			} else {
				System.out.println("Valor de depósito inávlido.");
			}
			
		}
	
	public abstract void sacar();
	public abstract void transferir();
					
}