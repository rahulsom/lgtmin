log.warning params.toString()
if (params.state == session.getAttribute('githubState')) {
  def code = params.code
  if (session.getAttribute('postLoginUri')) {
    def postLoginUri = session.getAttribute('postLoginUri')
    session.removeAttribute 'postLoginUri'

    

    redirect postLoginUri
  } else {
    redirect "/"
  }
} else {
  forward '/WEB-INF/pages/nouser.html.gtpl'
}
