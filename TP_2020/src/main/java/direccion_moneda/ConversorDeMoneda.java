package direccion_moneda;

import Importe.Importe;
import direccion_moneda.mercadolibre_api.molde.Moneda;
import direccion_moneda.mercadolibre_api.molde.Ratio;
import direccion_moneda.mercadolibre_api.services.ServicioMercadolibre;

import java.io.IOException;
import java.math.BigDecimal;

public class ConversorDeMoneda {
    private static ConversorDeMoneda instancia = null;

    private ConversorDeMoneda(){

    }

    public static ConversorDeMoneda instancia(){
        if(instancia == null){
            instancia = new ConversorDeMoneda();
        }
        return instancia;
    }

    private Ratio getRatio(Moneda m1, Moneda m2) throws IOException {
        return ServicioMercadolibre.instancia().ratio(m1.id,m2.id);
    }

    public void convertir(Importe importe,Moneda m1) throws IOException {
        Moneda m2 = importe.getMoneda();
        if(m1.symbol != m2.symbol){
            Ratio ratio = this.getRatio(m2,m1);
            BigDecimal valorConvertido = importe.getValor().multiply(new BigDecimal(ratio.ratio.toString()));

            importe.setMoneda(m1);
            importe.setValor(valorConvertido);
        }
    }
}
