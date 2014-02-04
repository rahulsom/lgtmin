package domain

class ValidationException extends RuntimeException {
    ValidationException(String s) {
        super(s)
    }
}
