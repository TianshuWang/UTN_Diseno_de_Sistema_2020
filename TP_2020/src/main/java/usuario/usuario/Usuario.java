package usuario.usuario;
import mensaje.mensaje.BandejaMensaje;
import mensaje.mensaje.Mensaje;
import usuario.password.Password;
import organizacion.*;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    private String username;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private Password password;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "organizacion_id")
    private Organizacion organizacion;

    @Column(name = "administrador")
    
    private Boolean isAdm;

    @Column(name = "revisor")
    
    private Boolean isRevisor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bandeja_validacion_id")
    private BandejaMensaje bandejaValidacion;

    private Usuario(){

    }

    public Usuario(Builder builder){
        this.username = builder.username;
        this.password = builder.password;
        this.isAdm = builder.isAdm;
        this.isRevisor = builder.isRevosor;
        this.organizacion = builder.organizacion;
        this.bandejaValidacion = new BandejaMensaje(this);
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
    }

    public static class Builder{
        private String username;
        private Password password;
        private boolean isAdm;
        private boolean isRevosor;
        private Organizacion organizacion;
        private String nombre;
        private String apellido;

        public Builder agregarNombre(String nombre){
            this.nombre = nombre;
            return this;
        }

        public Builder agregarApellido(String apellido){
            this.apellido = apellido;
            return this;
        }

        public Builder agregarUsername(String username){
            this.username = username;
            return this;
        }

        public Builder agregarPassword(Password password){
            this.password = password;
            return this;
        }

        public Builder agregarIsAdm(boolean isAdm){
            this.isAdm = isAdm;
            return this;
        }

        public Builder agregarIsRevisor(boolean isResivor){
            this.isRevosor = isResivor;
            return this;
        }

        public Builder agregarOrganizacion(Organizacion organizacion){
            this.organizacion = organizacion;
            return this;
        }

        public Usuario build(){
            return new Usuario(this);
        }
    }

    public void recibirMensaje(Mensaje mensaje){
        this.bandejaValidacion.agregarMensaje(mensaje);
    }

    //getters and setters
    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public BandejaMensaje getBandejaValidacion() {
        return bandejaValidacion;
    }

    public boolean getisAdm() {
        return isAdm;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(Password password){
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public void setAdm(Boolean adm) {
        isAdm = adm;
    }

    public void setRevisor(Boolean revisor) {
        isRevisor = revisor;
    }

    public void setBandejaValidacion(BandejaMensaje bandejaValidacion) {
        this.bandejaValidacion = bandejaValidacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public boolean getisRevisor() {
        return isRevisor;
    }

    public Boolean getAdm() {
        return isAdm;
    }

    public Boolean getRevisor() {
        return isRevisor;
    }


}
