package sistemasContas;

import java.util.Scanner;

public class ContaCorrente extends Conta {
	private double chequeEspecial;

	public ContaCorrente(String numeroConta, double saldo, String titular, double chequeEspecial) {
		super(numeroConta, saldo, titular);
		this.chequeEspecial = chequeEspecial;
	}

	@Override
	public void sacar() {
		
		Scanner leitor = new Scanner (System.in);
		System.out.println("Informe o valor do saque: ");
		double valor = leitor.nextDouble();		
		leitor.close();
		
		if (valor > 0 && saldo + chequeEspecial >= valor) {
			saldo -= valor;
			System.out.println("Saque realizado com sucesso.");
			System.out.println("Seu novo saldo é de R$" + saldo);
		} else {
			System.out.println("Saldo insuficiente.");
		}
		
		/*
		 * 
		 * Aqui será feita a manipulação do arquivo de texto
		 * 
		 */		
	}

	@Override
	public void transferir() {
		
		Scanner leitor = new Scanner (System.in);
		System.out.println("Informe o valor da transferência: ");
		double valorTransferencia = leitor.nextDouble();
		
		System.out.println("Informe a conta de destino: ");
		String contaDestino = leitor.nextLine();		
		leitor.close();
		
		if (valorTransferencia > 0 && saldo + chequeEspecial >= valorTransferencia) {
			saldo -= valorTransferencia;
			
			/*
			 * Descobrir como alterar o arquivo da conta de destino por meio da manipulação de arquivo. 
			*/
			
			System.out.println("Transferência realizada com sucesso.");
		} else {
			System.out.println("Erro na transferência.");
		}
		
	}

	@Override
	public void manipularArquivo() {
		// TODO Auto-generated method stub
		
	}
	
	public void calcularJuros() {
		
	}
	

}
