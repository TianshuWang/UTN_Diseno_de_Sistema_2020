package egreso.documento;

import egreso.egreso.Egreso;
import egreso.proveedor.Proveedor;
import exceptions.DocumentException;
import persistencia.*;

import java.io.IOException;

public class VerificadorDeDocumento {
    private static Repositorio<Documento> repositorio;

    public static void verificarDocumento(Documento documento) throws IOException, DocumentException {
        if(repositorio.existe(documento.getIdentificador())){
            throw new DocumentException("Document Had Already Loaded");
        }
        repositorio.agregar(documento);
    }

    public static void setRepositorio(Repositorio<Documento> repositorio) {
        VerificadorDeDocumento.repositorio = repositorio;
    }
}
