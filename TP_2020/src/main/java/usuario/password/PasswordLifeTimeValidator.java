package usuario.password;

import exceptions.*;
import getDataConfig.GetDataConfig;
import usuario.password.passwordValidador.PasswordValidator;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PasswordLifeTimeValidator {
    private static Integer limitDays;

    static {
        try {
            limitDays = Integer.parseInt(GetDataConfig.getValue("limitDays"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void checkPasswordLastUpdate(Password password) throws TransactionException{
        LocalDateTime presentTime = LocalDateTime.now();
        if(password.getPasswordInitialTime().until(presentTime,ChronoUnit.DAYS) > limitDays){
            throw new TransactionException(BusinessError.PASSWORD_EXPIRED);
        }
    }
}
