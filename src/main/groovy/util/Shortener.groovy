package util

/**
 * Shortens/Unshortens an id
 */
@Singleton
class Shortener {
    static final codeSet = [(0..9), ('a'..'z'), ('A'..'Z')].flatten().join('')
    static final base = codeSet.length()

    /**
     * Turns id into hash
     * @param n the id
     * @return the hash
     */
    String encode(long n) {
        def converted = ""
        while (n > 0) {
            converted = converted + codeSet[(n % base) as int]
            n = Math.floor(n / base)
        }
        return converted
    }

    /**
     * Converts hash to id
     * @param hash the hash
     * @return the id
     */
    long decode(String hash) {
        long c = 0
        for (int i = hash.length() - 1; i >= 0; i--) {
            c += (codeSet.indexOf(hash[i])) * Math.pow(base, i)
        }
        return c;
    }

}