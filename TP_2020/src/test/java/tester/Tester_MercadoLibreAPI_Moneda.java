package tester;

import direccion_moneda.ConversorDeMoneda;
import direccion_moneda.mercadolibre_api.ExcepcionMercadoLibreApi;
import direccion_moneda.mercadolibre_api.ListadoDePaises;
import direccion_moneda.mercadolibre_api.molde.Pais;
import direccion_moneda.mercadolibre_api.molde.Provincia;

import java.io.IOException;
import java.util.Scanner;

public class Tester_MercadoLibreAPI_Moneda {
    public static void main(String[] args) throws IOException, ExcepcionMercadoLibreApi {
        System.out.println("Seleccione un de los siguientes paises, ingresando su nombre:");
        for(Pais p: ListadoDePaises.getInstancia().getPaises()){
            System.out.println(p.id + "|" + p.name);
        }
        System.out.print(">>");
        Scanner enter = new Scanner(System.in);
        String nombrePaisElegido1 = enter.nextLine();
        Pais paisElegido1 = ListadoDePaises.getInstancia().paisDeNombre(nombrePaisElegido1);
        System.out.println("La moneda del pais "+ paisElegido1.name +" es:");
        System.out.println(paisElegido1.name + "|" + paisElegido1.currency_id);
    }
}
