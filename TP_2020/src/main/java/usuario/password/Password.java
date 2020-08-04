package usuario.password;

import java.time.LocalDateTime;

public class Password {
    private String content;
    private LocalDateTime passwordInitialTime;

    public Password(String password){
        this.content = password;
        this.passwordInitialTime = LocalDateTime.now();
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getPasswordInitialTime() {
        return passwordInitialTime;
    }

    public void changePassword(String password) {
        this.content = password;
        this.passwordInitialTime = LocalDateTime.now();
    }
}
