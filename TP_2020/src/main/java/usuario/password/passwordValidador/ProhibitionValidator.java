package usuario.password.passwordValidador;

import exceptions.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProhibitionValidator implements Validator {
    private boolean hasNoWhiteSpace = true;
    private boolean hasNoTildeLetter = true;
    private String patternTildeLetter = "[ü|é|á|í|ó|ú|ñ|Ñ]";
    private String pattern(){
        return "^(?<!"+patternTildeLetter+")(?=\\S+$)$";
    }

    public void validatePassword(String password) throws PassawordException {
        Pattern ptn = Pattern.compile(pattern());
        Matcher mtr = ptn.matcher(password);
        if(!mtr.matches()) {
            whatFailed(password);
        }
    }

    private void whatFailed(String password) throws PassawordException {
        Pattern ptn = Pattern.compile(patternTildeLetter);
        Matcher mtr = ptn.matcher(password);
        hasNoTildeLetter = !mtr.find();
        if(hasNoTildeLetter == false) {
            throw new PassawordException("Tilde Letter Found");
        }

        hasNoWhiteSpace = !(password.contains(" "));
        if(hasNoWhiteSpace == false) {
            throw new PassawordException("White Space Found");
        }
    }
}
