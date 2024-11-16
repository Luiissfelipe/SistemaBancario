package contas;

public class Poupanca extends Conta {
	private double taxaJuros;

	public Poupanca(String numeroConta, String titular, String senha, double saldo, String tipo, double taxaJuros) {
		super(numeroConta, titular, senha, saldo, tipo);
		this.taxaJuros = taxaJuros;	
	}
	
	public void calcularRendimento() {
		
	}

	@Override
	public void sacar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transferir() {
		// TODO Auto-generated method stub
		
	}
	
	

}
