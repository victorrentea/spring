package hello;

class MyException extends RuntimeException {
    private Object[] args;

    public enum ErrorCode {
        I_NEGATIVE("error.i.negative"),
        GENERAL("error.general");

        public final String code;

        ErrorCode(String code) {
            this.code = code;
        }
    }

    private ErrorCode code;

    public MyException(ErrorCode code, Object... args) {
        this.code = code;
        this.args = args;
    }

    public ErrorCode getCode() {
        return code;
    }


    public Object[] getArgs() {
        return args;
    }
}
