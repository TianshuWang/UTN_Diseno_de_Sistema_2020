package mensaje.mensaje;

import entityPersistente.EntidadPersistente;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import repositorio.DAOHibernate;
import repositorio.Repositorio;
import usuario.usuario.Usuario;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bandejaMensaje")
public class BandejaMensaje extends EntidadPersistente {
    @OneToOne
    @JoinColumn(name = "usuario", referencedColumnName = "username")
    private Usuario usuario;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private List<Mensaje> mensajes;

    private BandejaMensaje(){

    }

    public BandejaMensaje(Usuario usuario){
        this.usuario = usuario;
        this.mensajes = new ArrayList<>();
    }

    public void agregarMensaje(Mensaje mensaje){
        this.mensajes.add(mensaje);
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
