package usuarios;

public class Correntista extends Usuario{

    private String numeroConta;

    public String getNumeroConta() {
        return numeroConta;
    }

    public Correntista(String nome, String senha) {
        super(nome, senha, "correntista");
        this.numeroConta = "00012";
    }

    @Override
    public String toString() {
        return "Usuário: " + this.getNome() + " - " + this.getSenha() + " - " + this.getNumeroConta() + " [" + this.getNivelUsuario() + "]";
    }
}
