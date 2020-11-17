package usuario.password.passwordValidador;

import exceptions.*;
import getDataConfig.GetDataConfig;

import java.io.FileNotFoundException;

public class LengthValidator implements Validator {
    private boolean hasTheAdecuatedLong;
    private int lengthMin;
    private int lengthMax;

    public void validatePassword(String password) throws FileNotFoundException, TransactionException {
        this.getLengthData();
        hasTheAdecuatedLong = hasTheAdecuatedLong(password);
         if(hasTheAdecuatedLong == false) {
            whatFailed(password);
        }
    }

    private void getLengthData() throws FileNotFoundException {
        lengthMax = Integer.parseInt(GetDataConfig.getValue("lengthMax"));
        lengthMin = Integer.parseInt(GetDataConfig.getValue("lengthMin"));
    }

    private boolean hasTheAdecuatedLong(String password) {
        int passSize = password.length();
        return passSize >= lengthMin && passSize <= lengthMax;
    }

    private void whatFailed(String password) throws TransactionException {

        throw new TransactionException(BusinessError.PASSWORD_EXCEPTION,
                "Contraseña debe tener entre "
                        + Integer.valueOf(lengthMin)
                        + " Y "
                        + Integer.valueOf(lengthMax)
                        + " Caracteres De Longitud");
    }

}
