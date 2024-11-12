package usuarios;

import sistema.SistemaBanco;

import java.io.IOException;

public class Gerente extends Usuario {

    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    public void cadastrarBancario(String nome, String senha) throws IOException {
        Bancario novoBancario = new Bancario(nome, senha);
        SistemaBanco.adicionarUsuario(novoBancario);
        System.out.printf("Bancário %s cadastrado com sucesso.\n\n", nome);
    }

    public void cadastrarCorrenista(String nome, String senha) throws IOException {
        Correntista novoCorrentista = new Correntista(nome, senha);
        SistemaBanco.adicionarUsuario(novoCorrentista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n\n", nome);
    }
}
