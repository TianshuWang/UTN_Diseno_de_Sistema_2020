package usuario.password.passwordValidador;

import exceptions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PasswordValidator {
    private static List<Validator> validatorList = Arrays.asList(new ComplexityValidator(),new LengthValidator(),new WorstValidator(),new ProhibitionValidator());

    public static void agregarValidator(Validator ... validators){
        Collections.addAll(validatorList,validators);
    }

    public static void validatePassword(String password) throws TransactionException, FileNotFoundException {
        for (Validator v : validatorList) {
            v.validatePassword(password);
        }
    }
}