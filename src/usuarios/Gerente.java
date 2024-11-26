package usuarios;

import contas.Conta;
import contas.ContaCorrente;
import contas.ContaCorrenteAdicional;
import contas.ContaPoupanca;
import sistema.SistemaBanco;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Gerente extends Usuario {
    //Classe Gerente herda atributos e metodos da classe Usuario

    //Construtor do Gerente, ja cria com o nivel de usuario
    public Gerente(String nome, String senha) {
        super(nome, senha, "gerente");
    }

    private void mostrarUsuarios() {
        System.out.println("Usuários cadastrados no sistema:");
        for (Map.Entry<String, Usuario> entrada : SistemaBanco.getUsuarios().entrySet()) {
            Usuario usuario = entrada.getValue();
            if (usuario instanceof Correntista correntista) {
                System.out.printf("Nome: %s [%s] Contas: %s - %s - %s\n",usuario.getNome(), usuario.getNivelUsuario(), correntista.getNumContaCorrente(),
                        correntista.getNumContaPoupanca(), correntista.getNumContaAdicional());
            } else {
                System.out.printf("Nome: %s [%s]\n",usuario.getNome(), usuario.getNivelUsuario());
            }
        }
        System.out.println(" ");
    }

    private void mostrarContas() {
        System.out.println("Contas cadastradas no sistema:");
        for (Map.Entry<String, Conta> entrada : SistemaBanco.getContas().entrySet()) {
            Conta conta = entrada.getValue();
            if (conta instanceof ContaCorrenteAdicional contaAdc) {
                System.out.printf("Nº: %s [%s] - Titular: %s - Limite: R$ %.2f\n", contaAdc.getNumeroConta(), contaAdc.getTipo(),
                        contaAdc.getTitular(), contaAdc.getSaldo());
            }else {
                System.out.printf("Nº: %s [%s] - Titular: %s - Saldo: R$ %.2f\n", conta.getNumeroConta(), conta.getTipo(),
                        conta.getTitular(), conta.getSaldo());
            }
        }
        System.out.println(" ");
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
        Correntista correntista = new Correntista(nomeCorrentista, senhaCorrentista);
        System.out.printf("Correntista %s cadastrado com sucesso.\n\n", nomeCorrentista);

        //Perguntando qual conta deve ser criada
        System.out.println("""
                Qual conta deseja criar?
                [1] Conta corrente
                [2] Conta poupanca
                """);
        int opcao = input.nextInt();
        input.nextLine();
        switch (opcao) {
            case 1:
                //Chama o metodo para criar uma conta corrente
                criarContaCorrente(correntista);
                break;
            case 2:
                //Chama o metodo para criar uma conta poupança
                criarContaPoupanca(correntista);
                break;
            default:
                //Erro caso não digite uma opção válida
                System.out.println("Digite uma opção válida.");
        }


        //Chamando o metodo para adicionar o Usuario criado no sistema
        SistemaBanco.adicionarUsuario(correntista);
    }

    //Metodo para criar uma conta corrente
    private void criarContaCorrente(Correntista correntista) throws IOException {
        //Declarando scanner
        Scanner input = new Scanner(System.in);
        //Chamando metodo para gerar um número de conta
        correntista.gerarNumContaCorrente();
        //Declarando o número de conta gerado
        String numContaCorrente = correntista.getNumContaCorrente();
        //Verificando se o número de conta gerado ja existe
        if (SistemaBanco.getContas().get(numContaCorrente) != null) {
            //Se existir ele volta e gera outro
            return;
        }
        //Cria uma nova conta para o correntista
        ContaCorrente contaCorrente = new ContaCorrente(numContaCorrente,correntista.getNome(),correntista.getSenha());
        //Perguntando se deseja utilizar cheque especial
        System.out.println("""
                        Deseja utilizar cheque especial?
                        [1] Sim
                        [2] Não
                        """);
        int opcaoChequeEspecial = input.nextInt();
        input.nextLine();
        switch (opcaoChequeEspecial) {
            case 1:
                //Declarando que a conta utilizara cheque especial
                contaCorrente.setChequeEspecial(true);
                //Lendo o valor do limite do cheque especial
                System.out.println("Digite o valor do limite do cheque especial:");
                double limiteChequeEspecial = input.nextDouble();
                //Declarando o limite do cheque especial
                contaCorrente.setLimiteChequeEspecial(limiteChequeEspecial);
                //Adicionando a conta no sistema
                SistemaBanco.adicionarConta(contaCorrente);
                //Atualizando o usuario no sistema
                SistemaBanco.adicionarUsuario(correntista);
                System.out.printf("Conta corrente criada com um limite de R$ %.2f para cheque especial.\n", limiteChequeEspecial);
                //Mostrando o número da conta criada
                System.out.printf("Número da conta: %s\n", contaCorrente.getNumeroConta());
                break;
            case 2:
                //Declarando que a conta não utilizara cheque especial
                contaCorrente.setChequeEspecial(false);
                //Declarando o limite do cheque especial igual a 0
                contaCorrente.setLimiteChequeEspecial(0);
                //Adicionando a conta no sistema
                SistemaBanco.adicionarConta(contaCorrente);
                //Atualizando o usuario no sistema
                SistemaBanco.adicionarUsuario(correntista);
                System.out.println("Conta corrente criada sem opção de cheque especial.");
                //Mostrando o número da conta criada
                System.out.printf("Número da conta: %s\n", contaCorrente.getNumeroConta());
                break;
            default:
                //Erro caso não digite uma opção válida
                System.out.println("Digite uma opção válida.");
        }
    }

    //Metodo para criar uma conta poupança
    private void criarContaPoupanca(Correntista correntista) throws IOException {
        //Chamando metodo para gerar um número de conta
        correntista.gerarNumContaPoupanca();
        //Declarando o número de conta gerado
        String numContaPoupanca = correntista.getNumContaPoupanca();
        //Verificando se o número de conta gerado ja existe
        if (SistemaBanco.getContas().get(numContaPoupanca) != null) {
            //Se existir ele volta e gera outro
            return;
        }
        //Cria uma nova conta para o correntista
        ContaPoupanca contaPoupanca = new ContaPoupanca(numContaPoupanca, correntista.getNome(), correntista.getSenha());
        //Adicionando a conta no sistema
        SistemaBanco.adicionarConta(contaPoupanca);
        //Atualizando o usuario no sistema
        SistemaBanco.adicionarUsuario(correntista);
        System.out.println("Conta poupança criada com sucesso.");
        //Mostrando o número da conta criada
        System.out.printf("Número da conta: %s\n", contaPoupanca.getNumeroConta());
    }

    //Metodo para criar uma conta corrente adicional
    private void criarContaCorrenteAdicional(Correntista correntista) throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);
        //Chamando metodo para gerar um número de conta
        correntista.gerarNumContaAdicional();
        //Declarando o número de conta gerado
        String numContaAdicional = correntista.getNumContaAdicional();
        //Verificando se o número de conta gerado ja existe
        if (SistemaBanco.getContas().get(numContaAdicional) != null) {
            //Se existir ele volta e gera outro
            return;
        }
        //Lendo o limite da conta adicional
        System.out.println("Digite o valor do limite da Conta Adicional:");
        double limite = input.nextDouble();
        //Cria uma nova conta para o correntista
        ContaCorrenteAdicional contaCorrenteAdicional = new ContaCorrenteAdicional(numContaAdicional, correntista.getNome(), correntista.getSenha());
        //Declarando o limite da conta
        contaCorrenteAdicional.setSaldo(limite);
        //Adicionando a conta no sistema
        SistemaBanco.adicionarConta(contaCorrenteAdicional);
        //Atualizando o usuario no sistema
        SistemaBanco.adicionarUsuario(correntista);
        System.out.printf("Conta corrente adicional criada com um limite de R$ %.2f\n", contaCorrenteAdicional.getSaldo());
        //Mostrando o número da conta criada
        System.out.printf("Número da conta: %s\n", contaCorrenteAdicional.getNumeroConta());
    }

    //Metodo de verificação para criar uma conta
    private void criarConta() throws IOException {
        //Declarando scanner
        Scanner input = new Scanner(System.in);

        //Lendo o nome do usuario
        System.out.println("Digite o nome do correntista que deseja criar uma conta bancária:");
        String nomeCorrentista = input.nextLine();
        //Erro para caso o nome esteja vazio
        if (nomeCorrentista.isEmpty()) {
            System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeCorrentista) == null) {
            System.out.println("Não existe um usuário com esse nome.\n");
            return;
        }

        //Verificando se o usuario é um correntista
        if (!(SistemaBanco.getUsuarios().get(nomeCorrentista) instanceof Correntista correntistaEncontrado)) {
            System.out.println("O nome digitado não pertence a um correntista.\n");
            return;
        }

        //Declarando correntista encontrado

        //Opção para criar conta
        System.out.println("""
                Qual conta deseja criar?
                [1] Conta Corrente
                [2] Conta Corrente Adicional
                [3] Conta Poupança
                """);
        int opcao = input.nextInt();
        input.nextLine();

        switch (opcao) {
            case 1:
                //Verificando se o correntista ja possui uma conta corrente
                if (correntistaEncontrado.getNumContaCorrente().equals("null")) {
                    //Chamando metodo para criar uma conta corrente
                    criarContaCorrente(correntistaEncontrado);
                }else {
                    System.out.println("Esse correntista já possui uma conta corrente.");
                }
                break;
            case 2:
                //Verificando se o correntista possui uma conta corrente para poder criar uma adicional
                if (!correntistaEncontrado.getNumContaCorrente().equals("null")) {
                    //Verificando se o correntista ja possui uma conta corrente adicional
                    if (correntistaEncontrado.getNumContaAdicional().equals("null")){
                        //Chamando metodo para criar uma conta corrente adicional
                        criarContaCorrenteAdicional(correntistaEncontrado);
                    } else {
                        System.out.println("Esse correntista já possui uma conta adicional.");
                    }
                }else {
                    System.out.println("Esse correntista não possui uma conta corrente para criar uma conta adicional.");
                }
                break;
            case 3:
                //Verificando se o correntista ja possui uma conta poupança
                if (correntistaEncontrado.getNumContaPoupanca().equals("null")) {
                    //Chamando metodo para criar uma conta poupança
                    criarContaPoupanca(correntistaEncontrado);
                }else {
                    System.out.println("Esse correntista já possui uma conta poupanca.");
                }
                break;
            default:
                //Erro caso não escolha uma opção valida
                System.out.println("Escolha uma opção válida.");
        }

    }

    //Metodo para alterar o limite de uma conta adicional
    private void alterarLimiteContaAdicional() throws IOException {
        //Declarando o scanner
        Scanner input = new Scanner(System.in);

        //Lendo o numero da conta
        System.out.println("Informe o número da conta adicional:");
        String numContaAdicional = input.nextLine();
        //Declarando conta conforme o numero lido
        Conta contaEncontrada = SistemaBanco.getContas().get(numContaAdicional);

        //Erro para caso o nome esteja vazio
        if (numContaAdicional.isEmpty()) {
            System.out.println("O número da conta não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se a conta existe no sistema
        if (contaEncontrada == null){
            System.out.println("Conta inexistente.");
            return;
        }
        //Verificando se é uma conta corrente adicional
        if (!contaEncontrada.getTipo().equals("adicional")) {
            System.out.println("Essa não é uma conta corrente adicional.");
            return;
        }

        //Mostrando o limite da conta
        System.out.printf("O limite dessa conta é R$ %.2f\n", contaEncontrada.getSaldo());
        //Lendo o limite digitado
        System.out.println("Digite o novo valor de limite.");
        double novoLimite = input.nextDouble();

        //Declarando novo limite
        contaEncontrada.setSaldo(novoLimite);
        //Atualizando a conta no sistema
        SistemaBanco.adicionarConta(contaEncontrada);
        //Mostrando novo limite
        System.out.printf("Limite alterado. Novo limite é R$ %.2f\n", contaEncontrada.getSaldo());
    }

    private void alterarSenhaUsuario() throws IOException {
        Scanner input = new Scanner(System.in);

        //Lendo o numero da conta
        System.out.println("Informe o nome do usuário:");
        String nomeUsuario = input.nextLine();
        if (nomeUsuario.isEmpty()) {
            System.out.println("O nome do correntista não pode ser vazio. Tente novamente.\n");
            return;
        }
        //Verificando se o nome digitado já existe no sistema
        if (SistemaBanco.getUsuarios().get(nomeUsuario) == null) {
            System.out.println("Usuário não encontrado.\n");
            return;
        }

        System.out.println("Digite a nova senha do usuário:");
        String novaSenha = input.nextLine();
        //Erro para caso a senha esteja vazia ou tenha espaços
        if (novaSenha.isEmpty() || novaSenha.contains(" ")) {
            System.out.println("A senha não pode ser vazia nem conter espaços. Tente novamente.\n");
            return;
        }
        //Erro para caso a senha não tenha entre 4 e 7 caracteres
        if (novaSenha.length() < 4 || novaSenha.length() > 7) {
            System.out.println("A senha deve conter entre 4 e 7 caracteres.\n");
            return;
        }

        Usuario usuarioEncontrado = SistemaBanco.getUsuarios().get(nomeUsuario);
        usuarioEncontrado.setSenha(novaSenha);
        SistemaBanco.adicionarUsuario(usuarioEncontrado);

        if (usuarioEncontrado instanceof Correntista correntista) {
            Conta contaCorrente = SistemaBanco.getContas().get(correntista.getNumContaCorrente());
            Conta contaPoupanca = SistemaBanco.getContas().get(correntista.getNumContaPoupanca());
            Conta contaAdicional = SistemaBanco.getContas().get(correntista.getNumContaAdicional());

            if (contaCorrente != null) {
                contaCorrente.setSenha(novaSenha);
                SistemaBanco.adicionarConta(contaCorrente);
            }
            if (contaPoupanca != null) {
                contaPoupanca.setSenha(novaSenha);
                SistemaBanco.adicionarConta(contaPoupanca);
            }
            if (contaAdicional != null) {
                contaAdicional.setSenha(novaSenha);
                SistemaBanco.adicionarConta(contaAdicional);
            }
        }

        System.out.println("Senha alterada com sucesso.");

    }

    //Menu de opçoes para o gerente
    public void menuGerente() throws IOException {
        Scanner input = new Scanner(System.in);
        int opcao = 0;
        //Loop para escolher uma opção
        do {
            System.out.print("""
                        Escolha uma opção:
                        [1] Ver usuários cadastrados
                        [2] Ver contas cadastradas
                        [3] Cadastrar bancario
                        [4] Cadastrar correntista
                        [5] Criar conta para usuario existente
                        [6] Alterar limite de conta corrente adicional
                        [7] Alterar senha de usuário
                        [0] Sair
                        """);
            try {
                //Leitura da opção escolhida
                opcao = input.nextInt();
                //Pulando uma linha para não dar erro no scanner
                input.nextLine();
                switch (opcao) {
                    case 1:
                        mostrarUsuarios();
                        break;
                    case 2:
                        mostrarContas();
                        break;
                    case 3:
                        //Chama o metodo para cadastrar um bancario
                        cadastrarBancario();
                        break;
                    case 4:
                        //Chama o metodo para cadastrar um correntista
                        cadastrarCorrentista();
                        break;
                    case 5:
                        //Chama o metodo para criar uma conta
                        criarConta();
                        break;
                    case 6:
                        //Chama o metodo para alterar o limite de uma conta adicional
                        alterarLimiteContaAdicional();
                        break;
                    case 7:
                        alterarSenhaUsuario();
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
