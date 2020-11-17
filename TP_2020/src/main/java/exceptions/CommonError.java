package exceptions;

public interface CommonError {
    public int getErrorCode();

    public String getErrorMsg();

    public void setErrorMsg(String errorMsg);
}
