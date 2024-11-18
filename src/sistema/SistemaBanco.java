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
    private static Map<String, Usuario> usuarios = new HashMap<>();
    private static Map<String, Conta> contas = new HashMap<>();
    private static String USUARIOS_TXT = "src/usuarios/usuarios.txt";
    private static String CONTAS_TXT = "src/contas/contas.txt";

    public static Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public static Map<String, Conta> getContas() {
        return contas;
    }

    public static void adicionarUsuario(Usuario usuario) throws IOException {
        usuarios.put(usuario.getNome(), usuario);
        salvarUsuarios(usuarios);
    }

    public static void adicionarConta(Conta conta) throws IOException{
        contas.put(conta.getNumeroConta(), conta);
        salvarContas(contas);
    }

    public static void inicializarArquivoUsuarios() throws IOException {
        File arquivo = new File(USUARIOS_TXT);

        if (!arquivo.exists()) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))){
                escritor.write("NivelUsuario,Nome,Senha,ContaCorrente,ContaPoupanca,ContaAdicional\n");
                escritor.write("admin,admin,1234\n");
            }
        }
    }

    public static void inicializarArquivoContas() throws IOException {
        File arquivo = new File(CONTAS_TXT);

        if (!arquivo.exists()) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))){
                escritor.write("NumeroConta,TipoConta,Titular,Senha,Saldo,ChequeEspecial,LimiteCheque\n");
            }
        }
    }

    public static void salvarUsuarios(Map<String, Usuario> usuarios) throws IOException{
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(USUARIOS_TXT))) {
            escritor.write("NivelUsuario,Nome,Senha,ContaCorrente,ContaPoupanca,ContaAdicional\n");

            for (Map.Entry<String, Usuario> entrada : usuarios.entrySet()) {
                Usuario usuario = entrada.getValue();
                if (usuario instanceof Correntista correntista) {
                    escritor.write(correntista.getNivelUsuario() + "," + correntista.getNome() + "," + correntista.getSenha()
                            + "," + correntista.getNumContaCorrente() + "," + correntista.getNumContaPoupanca() + "," + correntista.getNumContaAdicional() + "\n");
                } else {
                    escritor.write(usuario.getNivelUsuario() + "," + usuario.getNome() + "," + usuario.getSenha() + "\n");
                }
            }
        }
    }

    public static void salvarContas(Map<String, Conta> contas) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(CONTAS_TXT))) {
            escritor.write("NumeroConta,TipoConta,Titular,Senha,Saldo,ChequeEspecial,LimiteCheque\n");

            for (Map.Entry<String, Conta> entrada : contas.entrySet()) {
                Conta conta = entrada.getValue();
                if (conta instanceof ContaCorrente contaC) {
                    escritor.write(contaC.getNumeroConta() + "," + contaC.getTipo() + "," + contaC.getTitular() + "," + contaC.getSenha()
                            + "," + contaC.getSaldo() + "," + contaC.isChequeEspecial() + "," + contaC.getLimiteChequeEspecial() +"\n");
                }else {
                    escritor.write(conta.getNumeroConta() + "," + conta.getTipo() + "," + conta.getTitular() + "," + conta.getSenha()
                            + "," + conta.getSaldo() + "\n");
                }
            }
        }
    }

    public static void carregarUsuarios() throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(USUARIOS_TXT))){
            String linha = leitor.readLine();
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(",");

                String nivelUsuario = partes[0];
                String nome = partes[1];
                String senha = partes[2];

                if (nivelUsuario.equals("correntista") && partes.length >= 6) {
                    String numContaCorrente = partes[3];
                    String numContaPoupanca = partes[4];
                    String numContaAdicional = partes[5];

                    Correntista correntista = new Correntista(nome, senha);
                    correntista.setNumContaCorrente(numContaCorrente);
                    correntista.setNumContaPoupanca(numContaPoupanca);
                    correntista.setNumContaAdicional(numContaAdicional);
                    adicionarUsuario(correntista);
                }else {
                    switch (nivelUsuario) {
                        case "admin":
                            adicionarUsuario(new Admin(nome, senha));
                            break;
                        case "gerente":
                            adicionarUsuario(new Gerente(nome,senha));
                            break;
                        case "bancario":
                            adicionarUsuario(new Bancario(nome, senha));
                            break;
                    }
                }
            }
        }
    }

    public static void carregarContas() throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(CONTAS_TXT))) {
            String linha = leitor.readLine();
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(",");
                String numeroConta = partes[0];
                String tipoConta = partes[1];
                String titular = partes[2];
                String senha = partes[3];
                double saldo = Double.parseDouble(partes[4]);

                if (tipoConta.equals("corrente") && partes.length == 7) {
                    boolean chequeEspecial = Boolean.parseBoolean(partes[5]);
                    double limiteChequeEspecial = Double.parseDouble(partes[6]);

                    ContaCorrente contaCorrente = new ContaCorrente(numeroConta, titular, senha);
                    contaCorrente.setSaldo(saldo);
                    contaCorrente.setChequeEspecial(chequeEspecial);
                    contaCorrente.setLimiteChequeEspecial(limiteChequeEspecial);
                    adicionarConta(contaCorrente);
                } else {
                    switch (tipoConta) {
                        case "poupanca":
                            ContaPoupanca contaPoupanca = new ContaPoupanca(numeroConta, titular, senha);
                            contaPoupanca.setSaldo(saldo);
                            adicionarConta(contaPoupanca);
                            break;
                        case "adicional":
                            ContaCorrenteAdicional contaCorrenteAdicional = new ContaCorrenteAdicional(numeroConta, titular, senha);
                            contaCorrenteAdicional.setSaldo(saldo);
                            adicionarConta(contaCorrenteAdicional);
                            break;
                    }
                }
            }
        }
    }

}
