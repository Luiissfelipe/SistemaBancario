package usuarios;

import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public class Admin extends Usuario {
    //Classe Admin herda atributos e metodos da classe Usuario

    //Construtor do Admin, ja cria com o nivel de usuario
    public Admin(String nome, String senha) {
        super(nome, senha, "admin");
    }

    //Metodo para cadastrar um gerente
    private void cadastrarGerente() throws IOException {
        //Declarando Scanner
        Scanner input = new Scanner(System.in);

        //Leitura do nome
        System.out.println("Digite o nome do gerente:");
        String nomeGerente = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeGerente.isEmpty()) {
            System.out.println("O nome do gerente não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado já existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeGerente) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        //Leitura da senha
        System.out.println("Digite a senha do gerente:");
        String senhaGerente = input.nextLine();
        //Erro para caso a senha esteja vazia ou tenha espaços
        if (senhaGerente.isEmpty() || senhaGerente.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        //Erro para caso a senha não tenha entre 4 e 7 caracteres
        if (senhaGerente.length() < 4 || senhaGerente.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        //Se não ocorrer nenhum erro, um novo Gerente é criado
        Gerente novoGerente = new Gerente(nomeGerente, senhaGerente);
        //Chamando o metodo para adicionar o Usuario criado
        SistemaBanco.adicionarUsuario(novoGerente);
        //Chamando o metodo para salvar no arquivo de dados o Usuario criado
        SistemaBanco.salvarUsuario(novoGerente);
        System.out.printf("Gerente %s cadastrado com sucesso.\n\n", nomeGerente);
    }

    //Menu de opçoes para o admin
    public void menuAdmin() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        //Loop para escolher uma opção
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Cadastrar gerente
                        [2] Funções de gerente
                        [3] Funções de bancário
                        [4] Funções de correntista
                        [0] Sair
                        """);
            try {
                //Leitura da opção escolhida
                opcao = input.nextInt();
                //Pulando uma linha para não dar erro no scanner
                input.nextLine();
                switch (opcao) {
                    case 1:
                        //Chama o metodo para cadastrar um gerente
                        cadastrarGerente();
                        break;
                    case 2:
                        //Cria um gerente para acessar as funções de gerente
                        Gerente gerente = new Gerente("admin", "1234");
                        //Chamando o menu do gerente
                        gerente.menuGerente();
                        //Declarando a opção para fechar o sistema
                        opcao = 0;
                        break;
                    case 3:
                        //Cria um bancario para acessar as funções de bancario
                        Bancario bancario = new Bancario("admin", "1234");
                        //Chamando o menu do bancario
                        bancario.menuBancario();
                        //Declarando a opção para fechar o sistema
                        opcao = 0;
                        break;
                    case 4:
                        //Cria um correntista para acessar as funções de correntista
                        Correntista correntista = new Correntista("admin", "1234");
                        break;
                    case 0:
                        //Fecha o sistema
                        break;
                    default:
                        //Erro para caso digite uma opção invalida
                        System.out.println("Opção invalida\n");
                }
            }catch (java.util.InputMismatchException e) {
                //Erro para caso não seja digitado um número na opção
                System.out.println("Entrada inválida. Por favor, insira um número.\n");
                input.nextLine();
            }
        } while (opcao != 0);
        //Fecha o Scanner
        input.close();
    }

}
