package domain

class ImageHolder {
    @Delegate Image image
    boolean favorited = false

    ImageHolder(Image image, boolean favorited) {
        this.image = image
        this.favorited = favorited
    }
}
