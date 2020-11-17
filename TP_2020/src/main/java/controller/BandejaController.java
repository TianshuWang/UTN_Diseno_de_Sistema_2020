package controller;

import exceptions.BusinessError;
import exceptions.TransactionException;
import mensaje.mensaje.BandejaMensaje;
import mensaje.mensaje.Mensaje;
import repositorio.FactoryRepositorio;
import repositorio.Repositorio;
import repositorio.entitymanager.EntityManagerHelper;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import usuario.usuario.Usuario;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class BandejaController {
    private Repositorio<Usuario> repoUsuario;
    private Repositorio<Mensaje> repoMensaje;
    private Repositorio<BandejaMensaje> repoBandeja;

    public BandejaController(){
        this.repoUsuario = FactoryRepositorio.get(Usuario.class);
        this.repoMensaje = FactoryRepositorio.get(Mensaje.class);
        this.repoBandeja = FactoryRepositorio.get(BandejaMensaje.class);
    }

    public ModelAndView ingresarBandeja(Request request, Response response) throws TransactionException {
        String username = request.session().attribute("currentUser");
        if(username == null) response.redirect("/login");

        Usuario usuario = repoUsuario.buscar(username);
        String rol = (usuario.getisAdm()==true)?"Administrador":"Comun";
        BandejaMensaje bandeja = usuario.getBandejaValidacion();

        Map<String,Object> parametros = new HashMap<>();
        parametros.put("mensajes",bandeja.getMensajes());
        parametros.put("usuario",usuario);
        parametros.put("rol",rol);
        parametros.put("isAdm",usuario.getisAdm());
        EntityManagerHelper.closeEntityManager();

        return new ModelAndView(parametros,"mensajes.hbs");
    }

    public String leerMensaje(Request request, Response response){
        String username = request.session().attribute("currentUser");
        if(username == null)response.redirect("/login");

        Integer mensajeId = Integer.parseInt(request.params("id"));

        Mensaje mensaje = repoMensaje.buscar(mensajeId);
        if(!mensaje.getLeido()){
            mensaje.setLeido(true);
            mensaje.setFechaDeLectura(LocalDate.now());
            repoMensaje.modificar(mensaje);
        }
        EntityManagerHelper.closeEntityManager();

        return mensaje.getFechaDeLectura().toString();
    }
}
