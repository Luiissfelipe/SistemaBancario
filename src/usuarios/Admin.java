package usuarios;

public class Admin extends Usuario{

    public Admin(String nome, String senha) {
        super(nome, senha, "admin");
    }

    public void cadastrarGerente(String nome, String senha) {
        Gerente novoGerente = new Gerente(nome, senha);

        System.out.printf("Gerente %s cadastrado com sucesso.\n", nome);

    }
}
