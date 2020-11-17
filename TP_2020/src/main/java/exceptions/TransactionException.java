package exceptions;

public class TransactionException extends Exception implements CommonError{
    private CommonError commonError;

    public TransactionException(CommonError CommonError){
        super();
        this.commonError = CommonError;
    }

    public TransactionException(CommonError CommonError,String ErrMsg){
        super();
        this.commonError = CommonError;
        this.commonError.setErrorMsg(ErrMsg);
    }


    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return this.commonError.getErrorMsg();
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        commonError.setErrorMsg(errorMsg);
    }
}
