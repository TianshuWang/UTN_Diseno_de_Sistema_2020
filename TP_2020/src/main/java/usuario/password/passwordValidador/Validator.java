package usuario.password.passwordValidador;

import exceptions.*;

import java.io.FileNotFoundException;

public interface Validator {
    public void validatePassword(String password) throws FileNotFoundException, TransactionException;
}
