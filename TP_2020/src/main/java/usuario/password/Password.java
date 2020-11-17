package usuario.password;

import converters.LocalDateAttributeConverter;
import entityPersistente.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "password")
public class Password extends EntidadPersistente {
    @Column(name = "contenido")
    private String content;

    @Column(name = "fecha_creacion")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate passwordInitialTime;

    private Password(){

    }

    public Password(String password){
        this.content = password;
        this.passwordInitialTime = LocalDate.now();
    }

    //getters and setters
    public String getContent() {
        return content;
    }

    public LocalDate getPasswordInitialTime() {
        return passwordInitialTime;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPasswordInitialTime(LocalDate passwordInitialTime) {
        this.passwordInitialTime = passwordInitialTime;
    }
}
