package sistema;

public class Login {
    private String nome;
    private String senha;
    private String nivelUsuario;
    private boolean loginRealizado;

    public String getNome() {
        return nome;
    }

    public String getNivelUsuario() {
        return nivelUsuario;
    }

    public void realizarLogin(String nome, String senha) {
        try {
            //Se o nome for nulo ou vazio retornará um erro
            if (nome == null || nome.isEmpty()) {
                System.out.println("O nome não pode estar vazio.");
                return;
            }
            //Se a senha for nula, vazia, ou tiver espaços retornará um erro
            if (senha == null || senha.isEmpty() || senha.contains(" ")) {
                System.out.println("Senha não pode estar vazia e não deve conter espaços.");
                return;
            }
            //Lendo o nome e senha, caso esteja tudo certo
            this.nome = nome;
            this.senha = senha;
            //Declarando nome do banco de dados
            var usuarioEncontrado = SistemaBanco.getUsuarios().get(this.nome);
            this.nivelUsuario = usuarioEncontrado.getNivelUsuario();

            //Verificação se o nome existe e se a senha esta correta
            if (usuarioEncontrado != null) {
                if (usuarioEncontrado.autenticar(this.senha)) {
                    System.out.println("Login realizado.\n");
                    this.loginRealizado = true;
                } else {
                    System.out.println("Senha incorreta.");
                    this.loginRealizado = false;
                }
            } else {
                System.out.println("Usuário não encotrado.");
                this.loginRealizado = false;
            }
        }
        //Tratamento para caso ocorra algum erro inesperado
        catch (Exception e) {
            System.out.println("Erro durante o processo de login. Tente novamente.");
        }
    }

    //Metodo para saber se o login foi realizado
    public boolean isLoginRealizado() {
        return loginRealizado;
    }
}
