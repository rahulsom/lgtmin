def clientId = '16a216c410d687c79ee8'
def clientSecret = '0d5e9e31e6f2f1e9b558147c330ae6a7c1e49ff7'

def params = [
  client_id: clientId,
  redirect_uri: 'http://localhost:8080/auth/me',
  state: UUID.randomUUID().toString()
]

request.getSession(true).setAttribute 'githubState', params.state

redirect "https://github.com/login/oauth/authorize?${params.collect{k,v -> "$k=$v"}.join('&')}"