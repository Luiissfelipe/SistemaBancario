package sistema;

import usuarios.Usuario;

import java.util.HashMap;
import java.util.Map;

public class SistemaBanco {
    private static Map<String, Usuario> usuarios = new HashMap<>();

    public static Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public static void adicionarUsuario(Usuario usuario) {
        usuarios.put(usuario.getNome(), usuario);
    }
}
