package exceptions;

public enum BusinessError implements CommonError{
    //para login
    USER_NOT_MATCH(444,"Usuario No Existe"),
    PASSWORD_NOT_MATCH(446,"Contraseña Ingresada Es Incorrecta"),
    PASSWORD_EXPIRED(447,"Contraseña Ingresada Es Vencida"),

    //para creacion de usuarios
    USER_EXISTED(445,"Usuario Ingresado Ya Existe"),
    PASSWORD_EXCEPTION(448,""),

    //para ingresos
    INGRESO_EXISTED(450, "El ingreso ya existe"),

    //para asociacion de ingresos y egresos
    ASOCIACION_EXCEPTION(411,""),

    //session
    SESSION_EXCEPTION(422,"Por favor Inicia la session"),

    //para asociacion de categorias
    CATEGORIA_REPETIDA_EXCEPTION(460, "La Categoria Seleccionada Ya Fue Escogida Anteriormente"),

    //proveedor
    PROVEEDOR_EXCEPTION(470, "Proveedor ya Existe"),

    //documento
    DOCUMENTOS_EXCEPTION(480, "Documento no se Encuentra");

    private int errCode;
    private String errMsg;

    private BusinessError(int errCode,String errMsg ) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errMsg = errorMsg;
    }
}
