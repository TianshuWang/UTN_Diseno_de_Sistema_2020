package presupuesto.validator;

import mensaje.mensaje.Mensaje;
import egreso.Egreso;
import presupuesto.validator.validacion.CantidadRequeridaPresupuesto;
import presupuesto.validator.validacion.CriterioSeleccionPresupuesto;
import presupuesto.validator.validacion.PresupuestoPresenteEnCompra;
import presupuesto.validator.validacion.ValidacionTransparencia;
import repositorio.DAOHibernate;
import repositorio.Repositorio;
import usuario.usuario.Usuario;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ValidatorDeTransparencia {
    private List<ValidacionTransparencia> validacionList;
    private Repositorio<Egreso> repoEgreso;
    private Repositorio<Usuario> repoUsuario;

    public ValidatorDeTransparencia(){
       this.validacionList = new ArrayList<>();
       agregarValidaciones(new CantidadRequeridaPresupuesto(),new PresupuestoPresenteEnCompra(),new CriterioSeleccionPresupuesto());
       this.repoEgreso = new Repositorio<>(new DAOHibernate<>(Egreso.class));
       this.repoUsuario = new Repositorio<>(new DAOHibernate<>(Usuario.class));
    }

    public void agregarValidaciones(ValidacionTransparencia ...validaciones){
        Collections.addAll(validacionList,validaciones);
    }

    public Boolean cumplirValidacion(Egreso egreso) throws FileNotFoundException {
        for (ValidacionTransparencia v : validacionList) {
            return v.cumplirValidacion(egreso)?true:false;
        }
        return true;
    }

    public List<ValidacionTransparencia> validacionesNoCumplidas(Egreso egreso)throws FileNotFoundException{
        List<ValidacionTransparencia> list = new ArrayList<>();
        for (ValidacionTransparencia v : validacionList) {
            if (!v.cumplirValidacion(egreso)) {
                list.add(v);
            }
        }
        return list;
    }

    public Mensaje generarResultadoDeValidacion(Egreso e) throws FileNotFoundException {
        e.setValidado(true);
        repoEgreso.modificar(e);
        Mensaje mensajeValidacion = ResultadoDeValidacionFactory.getResultadoDeValidacion(e);
        return mensajeValidacion;
    }

    private List<Egreso> egresosNoValidados(Usuario user){
        List<Egreso> egresos = repoEgreso.buscarTodos();
        List<Egreso> egresosRequierenPresupuesto = egresos.stream().filter(e->e.getUsuario().getUsername().equals(user.getUsername()) && e.getRequerirPresupuesto().equals(true)).collect(Collectors.toList());
        return egresosRequierenPresupuesto.stream().filter(c->c.getValidado().equals(false)).collect(Collectors.toList());
    }
}
