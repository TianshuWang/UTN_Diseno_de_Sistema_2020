package converters;

import presupuesto.criterioSeleccion.CriterioMenorCosto;
import presupuesto.criterioSeleccion.CriterioSeleccion;

import javax.persistence.AttributeConverter;

public class CriterioSeleccionConverter implements AttributeConverter<CriterioSeleccion,String> {
    @Override
    public String convertToDatabaseColumn(CriterioSeleccion criterioSeleccion) {
        return criterioSeleccion == null ? null : criterioSeleccion.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public CriterioSeleccion convertToEntityAttribute(String s) {
        return s.equals("criteriomenorcosto")?new CriterioMenorCosto():null;
    }
}
