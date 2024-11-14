package sistema;

import usuarios.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class SistemaBanco {
    private static Map<String, Usuario> usuarios = new HashMap<>();
    //private static Map<String, Conta> contas = new HashMap<>();
    private static String USUARIOS_TXT = "src/usuarios/usuarios.txt";

    public static Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public static void adicionarUsuario(Usuario usuario) throws IOException {
        usuarios.put(usuario.getNome(), usuario);
    }

    public static void inicializarArquivo() throws IOException {
        File arquivo = new File(USUARIOS_TXT);

        if (!arquivo.exists()) {
            try (BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo))){
                escritor.write("NivelUsuario,Nome,Senha\n");
                escritor.write("admin,admin,1234\n");
            }
        }
    }

    public static void salvarUsuario(Usuario usuario) throws IOException {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(USUARIOS_TXT, true))) {
            escritor.write(usuario.getNivelUsuario() + "," + usuario.getNome() + "," + usuario.getSenha() + "\n");
        }
    }

    public static void carregarUsuarios() throws IOException {
        try (BufferedReader leitor = new BufferedReader(new FileReader(USUARIOS_TXT))){
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(",");
                String nivelUsuario = partes[0];
                String nome = partes[1];
                String senha = partes[2];

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
                    case "correntista":
                        adicionarUsuario(new Correntista(nome, senha));
                        break;
                }
            }
        }
    }
}
