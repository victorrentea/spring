package spring.training.props;

class MyException extends RuntimeException {
    public enum ErrorCode {
        I_NEGATIVE("error.i.negative");
        public final String code;
        ErrorCode(String code) {
            this.code = code;
        }
    }
    private ErrorCode code;
    private Object[] args;

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
