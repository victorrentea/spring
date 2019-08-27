package spring.training.resurse;

class MyException extends RuntimeException {
    public enum ErrorCode {
        I_NEGATIVE("error.i.negative");
        public final String code;
        ErrorCode(String code) {
            this.code = code;
        }
    }
    private ErrorCode code;

    public MyException(ErrorCode code) {
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

}
