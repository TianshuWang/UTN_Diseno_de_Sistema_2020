package direccion_moneda;

public class DireccionPostal {
    private String calle;
    private Integer altura;
    private Integer piso;
    private Integer nro;
    private Ubicacion ubicacion;

    public DireccionPostal(String calle,Integer altura,Integer piso,Integer nro,Ubicacion ubicacion){
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.nro = nro;
        this.ubicacion = ubicacion;
    }

    //getters
    public Ubicacion getUbicacion(){
        return this.ubicacion;
    }
}
