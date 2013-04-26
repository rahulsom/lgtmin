@Singleton
class Shortener {
  def codeSet = [(0..9), ('a'..'z'), ('A'..'Z')].flatten().join('')
  def base = codeSet.length()

  String encode(long n) {
    def converted = ""
    while (n > 0) {
      converted = converted + codeSet[(n % base) as int]
      n = Math.floor(n / base)
    }
    return converted
  }

  long decode(String converted) {
    long c = 0
    for (int i = converted.length() - 1; i >= 0; i--) {
      c += (codeSet.indexOf(converted[i])) * Math.pow(base, i)
    }
    return c;
  }

}