package spark.utils;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import egreso.documento.Documento;
import organizacion.Organizacion;

import java.io.IOException;

public enum IsHelper implements Helper<String> {

    is {
        public Object apply(String tipoDeDocumento, Options options) throws IOException {
            Documento d = options.param(0);

            if(tipoDeDocumento.equals(d.getTipoDeDocumento().getTipo())){
                return  options.fn(d);
            }
            else{
                return options.inverse(this);
            }
        }
    },
}