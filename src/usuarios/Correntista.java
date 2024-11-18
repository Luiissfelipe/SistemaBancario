package usuarios;

import java.util.Random;

public class Correntista extends Usuario{

    private String numContaCorrente;
    private String numContaPoupanca;
    private String numContaAdicional;

    public Correntista(String nome, String senha) {
        super(nome, senha, "correntista");
    }

    public String getNumContaCorrente() {
        return numContaCorrente;
    }

    public void setNumContaCorrente(String numContaCorrente) {
        this.numContaCorrente = numContaCorrente;
    }

    public String getNumContaPoupanca() {
        return numContaPoupanca;
    }

    public void setNumContaPoupanca(String numContaPoupanca) {
        this.numContaPoupanca = numContaPoupanca;
    }

    public String getNumContaAdicional() {
        return numContaAdicional;
    }

    public void setNumContaAdicional(String numContaAdicional) {
        this.numContaAdicional = numContaAdicional;
    }

    public void gerarNumContaCorrente() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaCorrente = "CC" + numero;
    }

    public void gerarNumContaPoupanca() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaPoupanca = "CP" + numero;
    }

    public void gerarNumContaAdicional() {
        Random random = new Random();
        int numero = random.nextInt(90000) + 10000;
        this.numContaAdicional = "CA" + numero;
    }

    @Override
    public String toString() {
        return "Usuário: " + this.getNome() + " - " + this.getSenha() + " [" + this.getNivelUsuario() + "] - " + this.getNumContaCorrente()
                + " - " + this.getNumContaPoupanca() + " - " + this.getNumContaAdicional();
    }
}
