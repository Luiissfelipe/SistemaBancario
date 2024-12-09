package principal;

import sistema.Login;
import sistema.SistemaBanco;
import usuarios.*;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        //Inicializando arquivo de usuarios
        SistemaBanco.inicializarArquivoUsuarios();
        //Inicializando arquivo de contas
        SistemaBanco.inicializarArquivoContas();
        //Carregando arquivo de usuarios
        SistemaBanco.carregarUsuarios();
        //Carregando arquivo de contas
        SistemaBanco.carregarContas();

        //Mensagem de inicio
        System.out.println("Bem vindo(a) ao VaiNaFéBank\n");
        //Instanciando a classe de login
        Login login = new Login();
        //Chamando método para realizar login
        login.realizarlogin();
        //Declarando o usuario logado
        Usuario usuario = SistemaBanco.getUsuarios().get(login.getNome());

        //Lendo o nivel do usuario logado
        switch (login.getNivelUsuario()) {
            case "admin":
                //Fazendo o casting do usuario logado para a classe admin
                Admin admin = (Admin) usuario;
                //Chamando o menu do admin
                admin.menuAdmin();
                System.out.println("***Sistema finalizado.***");
                break;
            case "gerente":
                //Fazendo o casting do usuario logado para a classe gerente
                Gerente gerente = (Gerente) usuario;
                //Chamando o menu do gerente
                gerente.menuGerente();
                System.out.println("***Sistema finalizado.***");
                break;
            case "bancario":
                //Fazendo o casting do usuario logado para a classe bancario
                Bancario bancario = (Bancario) usuario;
                //Chamando o menu do bancario
                bancario.menuBancario();
                System.out.println("***Sistema finalizado.***");
                break;
            case "correntista":
                //Fazendo o casting do usuario logado para a classe correntista
                Correntista correntista = (Correntista) usuario;
                //Chamando o menu do correntista
                correntista.menuCorrentista();
                System.out.println("***Sistema finalizado.***");
                break;
            default:
                System.out.println("***Usuário não identificado***");
        }

    }
}
