package usuarios;

import controlador.Cadastrar;
import sistema.SistemaBanco;

public class Gerente extends Usuario implements Cadastrar {

    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    @Override
    public void cadastrarUsuario() {
        Correntista novoCorrentista = new Correntista(this.nome, this.senha);
        SistemaBanco.adicionarUsuario(novoCorrentista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n", nome);
    }
}
