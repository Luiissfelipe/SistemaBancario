package usuarios;

import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Scanner;

public class Gerente extends Usuario {
    //Classe Gerente herda atributos e metodos da classe Usuario

    //Construtor do Gerente, ja cria com o nivel de usuario
    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    //Metodo para cadastrar um bancario
    private void cadastrarBancario() throws IOException {
        //Declarando Scanner
        Scanner input = new Scanner(System.in);

        //Leitura do nome do bancario
        System.out.println("Digite o nome do bancário:");
        String nomeBancario = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeBancario.isEmpty()) {
            System.out.println("O nome do bancário não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado já existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeBancario) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        //Leitura da senha
        System.out.println("Digite a senha do bancário:");
        String senhaBancario = input.nextLine();
        //Erro para caso a senha esteja vazia ou tenha espaços
        if (senhaBancario.isEmpty() || senhaBancario.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        //Erro para caso a senha não tenha entre 4 e 7 caracteres
        if (senhaBancario.length() < 4 || senhaBancario.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        //Se não ocorrer nenhum erro, um novo Bancario é criado
        Bancario novoBancario = new Bancario(nomeBancario, senhaBancario);
        //Chamando o metodo para adicionar o Usuario criado
        SistemaBanco.adicionarUsuario(novoBancario);
        //Chamando o metodo para salvar no arquivo de dados o Usuario criado
        SistemaBanco.salvarUsuario(novoBancario);
        System.out.printf("Bancário %s cadastrado com sucesso.\n\n", nomeBancario);
    }

    //Metodo para cadastrar um correntista
    private void cadastrarCorrentista() throws IOException {
        //Declarando Scanner
        Scanner input = new Scanner(System.in);

        //Leitura do nome
        System.out.println("Digite o nome do correntista:");
        String nomeCorrentista = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeCorrentista.isEmpty()) {
            System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado já existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeCorrentista) != null) {
            System.out.println("Já existe uma conta com esse nome.\n");
            return;
        }

        //Leitura da senha
        System.out.println("Digite a senha do correntista:");
        String senhaCorrentista = input.nextLine();
        //Erro para caso a senha esteja vazia ou tenha espaços
        if (senhaCorrentista.isEmpty() || senhaCorrentista.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        //Erro para caso a senha não tenha entre 4 e 7 caracteres
        if (senhaCorrentista.length() < 4 || senhaCorrentista.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        //Se não ocorrer nenhum erro, um novo Correntista é criado
        Correntista novoCorrentista = new Correntista(nomeCorrentista, senhaCorrentista);
        //Chamando o metodo para adicionar o Usuario criado
        SistemaBanco.adicionarUsuario(novoCorrentista);
        //Chamando o metodo para salvar no arquivo de dados o Usuario criado
        SistemaBanco.salvarUsuario(novoCorrentista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n\n", nomeCorrentista);
    }

    //Metodo para configurar o limite das contas adicionais
    private void configurarLimiteContasAdicionais() {
        System.out.println("Configurando limites de contas adicionais");
    }

    //Metodo para criar uma conta corrente adicional
    private void criarContaCorrenteAdicional() {
        System.out.println("Criando conta corrente adicional.");
    }

    //Menu de opçoes para o gerente
    public void menuGerente() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        //Loop para escolher uma opção
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Cadastrar bancario
                        [2] Cadastrar correntista
                        [3] Configurar limites de contas adicionais
                        [4] Criar conta corrente adicional
                        [0] Sair
                        """);
            try {
                //Leitura da opção escolhida
                opcao = input.nextInt();
                //Pulando uma linha para não dar erro no scanner
                input.nextLine();
                switch (opcao) {
                    case 1:
                        //Chama o metodo para cadastrar um bancario
                        cadastrarBancario();
                        break;
                    case 2:
                        //Chama o metodo para cadastrar um correntista
                        cadastrarCorrentista();
                        break;
                    case 3:
                        //Chama o metodo para configurar o limite das contas adicionais
                        configurarLimiteContasAdicionais();
                        break;
                    case 4:
                        //Chama o metodo para criar uma conta corrente adicional
                        criarContaCorrenteAdicional();
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
