package usuario.password.passwordValidador;

import exceptions.*;
import getDataConfig.GetDataConfig;

import java.io.FileNotFoundException;

public class LengthValidator implements Validator {
    private boolean hasTheAdecuatedLong;
    private int lengthMin;
    private int lengthMax;

    public void validatePassword(String password) throws FileNotFoundException, PassawordException {
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

    private void whatFailed(String password) throws PassawordException {
        throw new PassawordException("The Password Doesn't Have The Adequate length");
    }

}
