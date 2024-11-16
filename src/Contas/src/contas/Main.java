package contas;

public class Main {

	public static void main(String[] args) {
		ContaCorrente conta = new ContaCorrente() {
            @Override
            public void transferir() {
                // Implementação do método transferir
            }
        };
        
        // Chamar o método sacar usando a instância da conta
        conta.depositar();
	
	}

}
