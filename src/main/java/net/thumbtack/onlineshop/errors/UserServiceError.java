package net.thumbtack.onlineshop.errors;

public class UserServiceError {
    private UserErrorCode errorCode;
    private String message;
    private String field;

    public UserServiceError() {
        super();
    }

    public UserServiceError(UserErrorCode userErrorCode, String message, String field) {
        this.errorCode = userErrorCode;
        this.message = message;
        this.field = field;
    }

    public UserErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(UserErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
