package usuario.password.passwordValidador;

import exceptions.*;
import getDataConfig.GetDataConfig;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComplexityValidator implements Validator {
    private boolean hasLowercaseLetter = true;
    private boolean hasUppercaseLetter = true;
    private boolean hasSpecialCharacter = true;
    private boolean hasNumber = true;

    private String patternLowerCase;
    private String patternUpperCase;
    private String patternNumberCase;
    private String patternSpecialCharacter;

    private  String pattern(){
        return "^(?=.*"+patternNumberCase+")(?=.*"+patternLowerCase+")(?=.*"+patternUpperCase+")(?=.*"+patternSpecialCharacter+")$";
    }

    private void getPatternsData() throws FileNotFoundException {
        patternLowerCase = GetDataConfig.getValue("patternLowerCase");
        patternUpperCase = GetDataConfig.getValue("patternUpperCase");
        patternNumberCase = GetDataConfig.getValue("patternNumberCase");
        patternSpecialCharacter = GetDataConfig.getValue("patternSpecialCharacter");
    }

    public void validatePassword(String password) throws FileNotFoundException, TransactionException {
        this.getPatternsData();
        Pattern ptn = Pattern.compile(pattern());
        Matcher mtr = ptn.matcher(password);

        if(mtr.matches() == false) {
            whatFailed(password);
        }
    }

    private void whatFailed(String password) throws TransactionException {
        Pattern ptn = Pattern.compile(patternLowerCase);
        Matcher mtr = ptn.matcher(password);
        hasLowercaseLetter = mtr.find();
        if (hasLowercaseLetter == false) {
            throw new TransactionException(BusinessError.PASSWORD_EXCEPTION,"No Se Encuentran Letras Minúsculas");
        }

        ptn = Pattern.compile(patternUpperCase);
        mtr = ptn.matcher(password);
        hasUppercaseLetter = mtr.find();
        if (hasUppercaseLetter == false) {
            throw new TransactionException(BusinessError.PASSWORD_EXCEPTION,"No Se Encuentran Letras Mayúsculas");
        }

        ptn = Pattern.compile(patternNumberCase);
        mtr = ptn.matcher(password);
        hasNumber = mtr.find();
        if (hasNumber == false) {
            throw new TransactionException(BusinessError.PASSWORD_EXCEPTION,"No Se Encuentran Números");
        }

        ptn = Pattern.compile(patternSpecialCharacter);
        mtr = ptn.matcher(password);
        hasSpecialCharacter = mtr.find();
        if (hasSpecialCharacter == false) {
            throw new TransactionException(BusinessError.PASSWORD_EXCEPTION,"No Se Encuentran Caracteres: @ $ _");
        }
    }

}
