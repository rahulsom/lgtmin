baseUrl = 'http://localhost:8080/'
reportsDir = new File('build/reports/geb').with {
    it.mkdirs()
    it
}