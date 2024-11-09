package usuarios;

public class Gerente extends Usuario{

    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    public void cadastrarCorrentista(String nome, String senha) {
        Correntista novoCorrentista = new Correntista(nome, senha);

        System.out.printf("Correntista %s cadastrado com sucesso.\n", nome);
    }


}
