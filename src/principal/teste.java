package principal;

import sistema.SistemaBanco;
import usuarios.Gerente;

import java.io.IOException;

public class teste {
    public static void main(String[] args) throws IOException {
        SistemaBanco.carregarUsuarios();
        System.out.println(SistemaBanco.getUsuarios());
    }
}
