package egreso.documento;

public class Link implements TipoDeDocumento{
    private String link;

    public Link(String link){
        this.link = link;
    }

    public void showContenido(){
        System.out.println("Documento Comercial:"+ this.link);
    }
}
