package tester;

import direccion_moneda.mercadolibre_api.ExcepcionMercadoLibreApi;
import direccion_moneda.mercadolibre_api.ListadoDePaises;
import direccion_moneda.mercadolibre_api.ListadoDeProvincias;
import direccion_moneda.mercadolibre_api.molde.Ciudad;
import direccion_moneda.mercadolibre_api.molde.Pais;
import direccion_moneda.mercadolibre_api.molde.Provincia;

import java.io.IOException;
import java.util.Scanner;

public class Tester_MercadoLibreAPI_Ubicacion {
    public static void main(String[] args) throws IOException, ExcepcionMercadoLibreApi {
        System.out.println("Seleccione un de los siguientes paises, ingresando su nombre:");
        for(Pais p: ListadoDePaises.getInstancia().getPaises()){
            System.out.println(p.id + "|" + p.name);
        }
        System.out.print(">>");
        Scanner enter = new Scanner(System.in);
        String nombrePaisElegido = enter.nextLine();
        Pais paisElegido = ListadoDePaises.getInstancia().paisDeNombre(nombrePaisElegido);
        System.out.println("Las provincias del pais "+ paisElegido.name +" son:");
        for(Provincia p:paisElegido.states){
            System.out.println(p.id + "|" + p.name);
        }

        System.out.println("Seleccione una de las provincias del pais, ingresando su nombre:");
        System.out.print(">>");
        String nombreProvinciaElegida = enter.nextLine();
        Provincia provinciaElegida = ListadoDeProvincias.getInstancia().provinciaDeNombre(nombrePaisElegido,nombreProvinciaElegida);
        System.out.println("Las ciudades de la provincia "+ provinciaElegida.name +" son:");
        for(Ciudad c:provinciaElegida.cities){
            System.out.println(c.name);
        }

    }
}
