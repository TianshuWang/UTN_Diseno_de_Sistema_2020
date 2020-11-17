package direccion_moneda.mercadolibre_api.services;

import direccion_moneda.mercadolibre_api.model.*;
import repositorio.DAOHibernate;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ImportadorMercadoLibreApi {

    public static void importarPaises() throws IOException {
        Repositorio<Pais> repoPais = FactoryRepositorio.get(Pais.class);
        Repositorio<Provincia> repoProvincias = FactoryRepositorio.get(Provincia.class);
        Repositorio<Ciudad> repoCiudad = new FactoryRepositorio().get(Ciudad.class);
        Pais[] paises = ServicioMercadolibre.instancia().paises();
        for(Pais p:Arrays.asList(paises)){
            if(repoPais.buscar(p.getId()) == null){
                repoPais.agregar(p);
                importarMoneda(p);
                importarProvincia(p);
            }
        }
    }

    public static void importarProvincia(Pais pais) throws IOException {
        Provincia[] provincias = ServicioMercadolibre.instancia().pais(pais.id).states;
        List<Provincia> provinciaList = Arrays.asList(provincias);
        Repositorio<Provincia> repoProvincias = FactoryRepositorio.get(Provincia.class);
        Repositorio<Pais> repoPais = FactoryRepositorio.get(Pais.class);
        for(Provincia p:provinciaList){
            if(repoProvincias.buscar(p.getId())==null){
                p.setCountry(pais);
                repoProvincias.agregar(p);
                importarCiudades(p);
            }
        }
        repoPais.modificar(pais);
    }

    public static void importarCiudades(Provincia provincia) throws IOException{
        Repositorio<Ciudad> repoCiudad = new FactoryRepositorio().get(Ciudad.class);
        Repositorio<Provincia> repoProvincias = FactoryRepositorio.get(Provincia.class);
        Ciudad[] ciudades = ServicioMercadolibre.instancia().provincia(provincia.id).cities;
        List<Ciudad> ciudadList = Arrays.asList(ciudades);
        for(Ciudad c:ciudadList){
            if(repoCiudad.buscar(c.getId()) == null){
                c.setCountry(provincia.getCountry());
                c.setState(provincia);
                repoCiudad.agregar(c);
            }
        }
        repoProvincias.modificar(provincia);
    }



    public static void importarMoneda(Pais pais) throws IOException {
        Repositorio<Moneda> repoMonedas = FactoryRepositorio.get(Moneda.class);
        Moneda moneda = repoMonedas.buscar(pais.currency_id);
        if(moneda == null){
            moneda = ServicioMercadolibre.instancia().moneda(pais.currency_id);
            repoMonedas.agregar(moneda);
        }
    }
}
