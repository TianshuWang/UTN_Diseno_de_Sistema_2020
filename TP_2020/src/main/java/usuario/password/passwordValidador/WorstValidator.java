package usuario.password.passwordValidador;

import exceptions.*;
import getDataConfig.GetDataConfig;

import java.io.*;
import java.net.URL;

public class WorstValidator implements Validator {
    private boolean isNotTheWorstPassword;

    public void validatePassword(String password) throws FileNotFoundException, TransactionException {
        isNotTheWorstPassword = !isTheWorstPassword(password);
        if(isNotTheWorstPassword == false) {
            whatFailed(password);
        }
    }

    private void whatFailed(String password) throws TransactionException {
        throw new TransactionException(BusinessError.PASSWORD_EXCEPTION,"Contraseña Está En La Lista De Las Peores Contraseñas");
    }

    private boolean isTheWorstPassword(String password) throws FileNotFoundException {
        boolean res = false;
        String URL = GetDataConfig.getValue("worst_password_list");
        String strTmp = "";
        try{
            java.net.URL url = new URL(URL);
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(url.openStream()));
            while((strTmp = buffReader.readLine())!=null){
                if(password.toLowerCase().equals(strTmp)){
                    res = true;
                    break;
                }
            }
            buffReader.close();
        }
        catch (Exception e) {
            System.err.println("read errors :" + e);
        }
        return res;
    }
}
