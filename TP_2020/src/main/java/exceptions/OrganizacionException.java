package exceptions;

public class OrganizacionException extends Exception{
    public OrganizacionException(){
        super("No Pertenece A Una Organizacion Juridica");
    }
}
