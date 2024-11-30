package sistema;

import contas.Conta;
import contas.ContaCorrente;
import contas.ContaCorrenteAdicional;
import contas.ContaPoupanca;
import usuarios.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SistemaBanco {
    //Map dos usuarios
    private static Map<String, Usuario> usuarios = new HashMap<>();
    //Map das contas
    private static Map<String, Conta> contas = new HashMap<>();
    //Arquivo de texto dos usuarios
    private static String USUARIOS_TXT = "src/usuarios/usuarios.txt";
    //Arquivo de texto das contas
    private static String CONTAS_TXT = "src/contas/contas.txt";

    //Getter dos usuarios
    public static Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    //Getter das contas
    public static Map<String, Conta> getContas() {
        return contas;
    }

    //Metodo para adicionar/salvar um usuario
    public static void adicionarUsuario(Usuario usuario) throws IOException {
        //Adiciona o usuario no map de usuarios
        usuarios.put(usuario.getNome(), usuario);
        //Salva o usuario no arquivo de texto
        salvarUsuarios(usuarios);
    }

    //Metodo para adicionar/salvar um usuario
    public static void adicionarConta(Conta conta) throws IOException{
        //Adiciona a conta no map de contas
        contas.put(conta.getNumeroConta(), conta);
        //Salva a conta no arquivo de texto
        salvarContas(contas);
    }

    //Metodo para inicializar o arquivo de texto dos usuarios
    public static void inicializarArquivoUsuarios() throws IOException {
        File arquivo = new File(USUARIOS_TXT);

        //Verificando se o arquivo existe
        if (!arquivo.exists()) {
            //Cria o arquivo de texto e escreve o titulo e o usuario admin
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))){
                escritor.write("Usuários:\n");
                escritor.write("admin,admin,1234\n");
            }
        }
    }

    //Metodo para inicializar o arquivo de texto das contas
    public static void inicializarArquivoContas() throws IOException {
        File arquivo = new File(CONTAS_TXT);

        //Verificando se o arquivo existe
        if (!arquivo.exists()) {
            //Cria o arquivo de texto e escreve o titulo
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))){
                escritor.write("Contas:\n");
            }
        }
    }

    //Metodo para salvar os usuarios no arquivo de texto
    public static void salvarUsuarios(Map<String, Usuario> usuarios) throws IOException{
        //Cria o titulo do arquivo de texto
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(USUARIOS_TXT))) {
            escritor.write("Usuários:\n");

            //Loop para percorrer o map e escrever cada um no arquivo de texto
            for (Map.Entry<String, Usuario> entrada : usuarios.entrySet()) {
                Usuario usuario = entrada.getValue();
                //Verificando se o usuario é um correntista
                if (usuario instanceof Correntista correntista) {
                    escritor.write(correntista.getNivelUsuario() + "," + correntista.getNome() + "," + correntista.getSenha()
                            + "," + correntista.getNumContaCorrente() + "," + correntista.getNumContaPoupanca() + "," + correntista.getNumContaAdicional() + "\n");
                } else {
                    escritor.write(usuario.getNivelUsuario() + "," + usuario.getNome() + "," + usuario.getSenha() + "\n");
                }
            }
        }
    }

    //Metodo para salvar as contas no arquivo de texto
    public static void salvarContas(Map<String, Conta> contas) throws IOException {
        //Cria o titulo do arquivo de texto
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CONTAS_TXT))) {
            escritor.write("Contas:\n");

            //Loop para percorrer o map e escrever cada um no arquivo de texto
            for (Map.Entry<String, Conta> entrada : contas.entrySet()) {
                Conta conta = entrada.getValue();
                //Verificando se é uma conta corrente
                if (conta instanceof ContaCorrente contaC) {
                    escritor.write(contaC.getNumeroConta() + "," + contaC.getTipo() + "," + contaC.getTitular() + "," + contaC.getSenha()
                            + "," + contaC.getSaldo() + "," + contaC.isChequeEspecial() + "," + contaC.getLimiteChequeEspecial() +"\n");
                }
                //Verificando se é uma conta corrente adicional
                else if (conta instanceof ContaCorrenteAdicional contaA) {
                    escritor.write(contaA.getNumeroConta() + "," + contaA.getTipo() + "," + contaA.getTitular() + "," + contaA.getSenha()
                            + "," + contaA.getSaldo() + "," + contaA.getDependente() + "\n");
                }
                else {
                    escritor.write(conta.getNumeroConta() + "," + conta.getTipo() + "," + conta.getTitular() + "," + conta.getSenha()
                            + "," + conta.getSaldo() + "\n");
                }
            }
        }
    }

    //Metodo para ler os usuarios no arquivo de texto
    public static void carregarUsuarios() throws IOException {
        //Criando o leitor
        try (BufferedReader leitor = new BufferedReader(new FileReader(USUARIOS_TXT))){
            //Declara a linha e pula a do titulo
            String linha = leitor.readLine();
            //Loop para fazer a leitura do arquivo de texto
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(",");
                //Declarando dados do usuario
                String nivelUsuario = partes[0];
                String nome = partes[1];
                String senha = partes[2];

                //Verificando se o usuario é um correntista
                if (nivelUsuario.equals("correntista") && partes.length >= 6) {
                    //Declarando o numero das contas
                    String numContaCorrente = partes[3];
                    String numContaPoupanca = partes[4];
                    String numContaAdicional = partes[5];

                    //Criando correntista conforme os dados lidos
                    Correntista correntista = new Correntista(nome, senha);
                    //Adicionando o numero de suas respectivas contas
                    correntista.setNumContaCorrente(numContaCorrente);
                    correntista.setNumContaPoupanca(numContaPoupanca);
                    correntista.setNumContaAdicional(numContaAdicional);
                    //Salva o usuario correntista no map de usuarios
                    adicionarUsuario(correntista);
                }else {
                    //Verificando o nivel do usuario
                    switch (nivelUsuario) {
                        case "admin":
                            //Salva o usuario admin no map de usuarios
                            adicionarUsuario(new Admin(nome, senha));
                            break;
                        case "gerente":
                            //Salva o usuario gerente no map de usuarios
                            adicionarUsuario(new Gerente(nome,senha));
                            break;
                        case "bancario":
                            //Salva o usuario bancario no map de usuarios
                            adicionarUsuario(new Bancario(nome, senha));
                            break;
                    }
                }
            }
        }
    }

    //Metodo para ler as contas no arquivo de texto
    public static void carregarContas() throws IOException {
        //Criando o leitor
        try (BufferedReader leitor = new BufferedReader(new FileReader(CONTAS_TXT))) {
            //Declara a linha e pula a do titulo
            String linha = leitor.readLine();
            //Loop para fazer a leitura do arquivo de texto
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(",");
                //Declarando dados da conta
                String numeroConta = partes[0];
                String tipoConta = partes[1];
                String titular = partes[2];
                String senha = partes[3];
                double saldo = Double.parseDouble(partes[4]);

                //Verificando se é uma conta corrente
                if (tipoConta.equals("corrente") && partes.length == 7) {
                    //Declarando dados da conta corrente
                    boolean chequeEspecial = Boolean.parseBoolean(partes[5]);
                    double limiteChequeEspecial = Double.parseDouble(partes[6]);

                    //Criando conta corrente conforme os dados lidos
                    ContaCorrente contaCorrente = new ContaCorrente(numeroConta, titular, senha);
                    //Adicionando dados da conta corrente
                    contaCorrente.setSaldo(saldo);
                    contaCorrente.setChequeEspecial(chequeEspecial);
                    contaCorrente.setLimiteChequeEspecial(limiteChequeEspecial);
                    //Salva a conta corrente no map de contas
                    adicionarConta(contaCorrente);
                }
                //Verificando se é uma conta corrente adicional
                else if (tipoConta.equals("adicional") && partes.length == 6) {
                    //Declarando dados da conta corrente adicional
                    String dependente = partes[5];

                    //Criando conta poupança conforme os dados lidos
                    ContaCorrenteAdicional contaCorrenteAdicional = new ContaCorrenteAdicional(numeroConta, titular, senha);
                    //Adicionando saldo da conta adicional
                    contaCorrenteAdicional.setSaldo(saldo);
                    //Adicionando nome do dependente
                    contaCorrenteAdicional.setDependente(dependente);
                    //Salva a conta poupança no map de contas
                    adicionarConta(contaCorrenteAdicional);
                } else {
                    //Criando conta poupança conforme os dados lidos
                    ContaPoupanca contaPoupanca = new ContaPoupanca(numeroConta, titular, senha);
                    //Adicionando saldo da conta poupança
                    contaPoupanca.setSaldo(saldo);
                    //Salva a conta poupança no map de contas
                    adicionarConta(contaPoupanca);
                }
            }
        }
    }

}
