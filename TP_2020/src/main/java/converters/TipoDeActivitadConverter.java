package converters;

import organizacion.categoria.Comisionista;
import organizacion.categoria.NoComisionista;
import organizacion.categoria.TipoDeActividad;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoDeActivitadConverter implements AttributeConverter<TipoDeActividad,String> {

    @Override
    public String convertToDatabaseColumn(TipoDeActividad tipoDeActividad) {
        return tipoDeActividad == null? null : tipoDeActividad.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public TipoDeActividad convertToEntityAttribute(String s) {
        if(s.equals("nocomisionista")){
            return new NoComisionista();
        }
        else if(s.equals("comisonista")){
            return new Comisionista();
        }
        return null;
    }
}
