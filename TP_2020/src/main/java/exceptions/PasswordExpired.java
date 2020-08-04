package exceptions;

public class PasswordExpired extends Exception{
    public PasswordExpired(int days){
        super("Password Has Not Been Changed for "+days+" Days");
    }
}
