package domain

/**
 * Created by rahulsomasunderam on 1/23/14.
 */
class UniqueConstraintViolatedException extends RuntimeException {
	String hash

	UniqueConstraintViolatedException(String hash) {
		super("Image already uploaded")
		this.hash = hash
	}
}
