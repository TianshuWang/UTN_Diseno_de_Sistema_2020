package egreso.proveedor;

import persistencia.Repositorio;

import java.io.IOException;

public class VerificadorDeProveedor {
    private static Repositorio<Proveedor> repositorio;

    public static void verificarProveedor(Proveedor proveedor) throws IOException {
        if(!repositorio.existe(proveedor.getIdentificador())){
            repositorio.agregar(proveedor);
        }
    }

    public static void setRepositorio(Repositorio<Proveedor> repositorio) {
        VerificadorDeProveedor.repositorio = repositorio;
    }
}
