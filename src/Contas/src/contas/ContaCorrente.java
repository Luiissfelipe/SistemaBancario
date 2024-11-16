package contas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContaCorrente extends Conta {
	private static double chequeEspecial;

	//Criando o construtor
	public ContaCorrente(String numeroConta, String titular, String senha, double saldo, String tipo, double chequeEspecial) {
		super(numeroConta, titular, senha, saldo, tipo);
		ContaCorrente.chequeEspecial = chequeEspecial;
	}

	// Método sacar
	public void sacar() {
		
		 // Iniciando o leitor
        Scanner scanner = new Scanner(System.in);
        
        // Obtendo dados do usuário
        System.out.println("Informe a conta:");
        String contaInformada = scanner.nextLine();
        
        System.out.println("Informe o valor do saque:");
        double valorSaque = scanner.nextDouble();
        
        System.out.println("Informe a senha:");
        // Consumindo a nova linha deixada pelo nextDouble
        scanner.nextLine();
        String senhaInformada = scanner.nextLine();
        
        scanner.close();
        
        // Lista para armazenar os dados atualizados
        List<String[]> dadosAtualizados = new ArrayList<>();
        
        String arquivo = "C:\\Users\\Client\\eclipse-workspace\\Contas\\src\\dadosContas.csv";
        boolean contaEncontrada = false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            
            // Lê o arquivo linha por linha
            while ((linha = br.readLine()) != null) {
                String[] campos = linha.split(",");
                
                // Verifica se o número da conta e a senha correspondem ao informado
                if (campos[0].equals(contaInformada) && campos[2].equals(senhaInformada)) {
                    double novoSaldo = Double.parseDouble(campos[3]);
                    double chequeEspecial = Double.parseDouble(campos[5]);
                    if (novoSaldo + chequeEspecial >= valorSaque) {
                        novoSaldo -= valorSaque;
                        campos[3] = String.valueOf(saldo);  // Atualiza o saldo na linha
                        contaEncontrada = true;
                     // Exibe o saldo após o saque
                        System.out.println("Saque realizado com sucesso.");
                        System.out.println("Seu novo saldo é de R$" + novoSaldo);
                    } else {
                        System.out.println("Saldo insuficiente.");
                    }
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            for (String[] campos : dadosAtualizados) {
                bw.write(String.join(",", campos));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
            return;
        }

    }

	@Override
	public void transferir() {
			
	}

}
