package usuarios;

import controlador.Cadastrar;
import sistema.SistemaBanco;

public class Admin extends Usuario implements Cadastrar {

    public Admin(String nome, String senha) {
        super(nome, senha, "admin");
    }

    @Override
    public void cadastrarUsuario() {
        Gerente novoGerente = new Gerente(this.nome, this.senha);
        SistemaBanco.adicionarUsuario(novoGerente);
        System.out.printf("Gerente %s cadastrado com sucesso.\n", nome);
    }
}
