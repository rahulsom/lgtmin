package domain

class UniqueConstraintViolatedException extends ValidationException {
    String hash

    UniqueConstraintViolatedException(String hash) {
        super("Image already uploaded")
        this.hash = hash
    }
}
