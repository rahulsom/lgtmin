import domain.letsencrypt.Challenge

def input = request.getParameter('challengeText')

log.info("params: ${request.getParameterMap()}")
log.info("challengeText was : $input")

response.setContentType('text/plain')
response.outputStream.print Challenge.findAll().find {it.challengeText == input}?.responseText