package principal;

import contas.Conta;
import contas.ContaCorrente;
import sistema.SistemaBanco;
import usuarios.Admin;
import usuarios.Correntista;

import java.io.IOException;
import java.util.Random;

public class teste {
    public static void main(String[] args) throws IOException {

        SistemaBanco.inicializarArquivoUsuarios();
        SistemaBanco.inicializarArquivoContas();
        SistemaBanco.carregarUsuarios();
        SistemaBanco.carregarContas();

        Correntista correntista = (Correntista) SistemaBanco.getUsuarios().get("luis");

        correntista.menuCorrentista();

    }
}
