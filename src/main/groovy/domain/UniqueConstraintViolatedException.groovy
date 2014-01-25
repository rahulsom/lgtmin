package domain

class ValidationException extends RuntimeException {
	ValidationException(String s) {
		super(s)
	}
}
class UniqueConstraintViolatedException extends ValidationException {
	String hash

	UniqueConstraintViolatedException(String hash) {
		super("Image already uploaded")
		this.hash = hash
	}
}
