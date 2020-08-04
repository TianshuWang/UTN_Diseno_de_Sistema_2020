package organizacion.organizacionJuridicaBuilder;

import organizacion.organizacionJuridicaBuilder.TipoOrganizacionJuridica;

public class OSC implements TipoOrganizacionJuridica {
    private String mision;


    public OSC(String mision) {
        this.mision = mision;
    }

    @Override
    public String getCategoria() {
        return null;
    }
}
