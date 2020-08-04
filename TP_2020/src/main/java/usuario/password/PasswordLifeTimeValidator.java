package usuario.password;

import exceptions.*;
import getDataConfig.GetDataConfig;
import usuario.password.passwordValidador.PasswordValidator;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PasswordLifeTimeValidator {
    private Integer limitDays;

    public PasswordLifeTimeValidator() throws FileNotFoundException {
        limitDays = Integer.parseInt(GetDataConfig.getValue("limitDays"));
    }

    public void checkPasswordLastUpdate(Password password) throws PasswordExpired {
        LocalDateTime presentTime = LocalDateTime.now();
        if(password.getPasswordInitialTime().until(presentTime,ChronoUnit.DAYS) > limitDays){
            throw new PasswordExpired(this.limitDays);
        }
    }

    private void changePassword(Password password, String newPassword) throws FileNotFoundException, PassawordException {
        new PasswordValidator().validatePassword(newPassword);
        password.changePassword(newPassword);
    }
}
