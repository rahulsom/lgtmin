<div class="navbar navbar-fixed-top navbar-default" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/">LGTM.in/g</a>
    </div>

    <div class="navbar-collapse collapse">
      <ul class="nav navbar-nav">
        <li class="${request.servletPath == '/WEB-INF/pages/index.gtpl' ? 'active' : ''}">
          <a href="/">Home</a>
        </li>
        <li class="${request.requestURI == '/g' ? 'active' : ''}">
          <a href="/g">Random</a>
        </li>
        <li class="${request.servletPath == '/WEB-INF/pages/upload.gtpl' ? 'active' : ''}">
          <a href="/upload">Submit</a>
        </li>
        <li class="${request.servletPath == '/WEB-INF/pages/browse.gtpl' ? 'active' : ''}">
          <a href="/browse">Browse</a>
        </li>
        <% if (session != null && session.getAttribute("githubUsername") != null
            && session.getAttribute("isAdmin").equals(Boolean.TRUE)) { %>
          <li class="${request.servletPath == '/WEB-INF/pages/users.gtpl' ? 'active' : ''}">
            <a href="/banned">Banned Users</a>
          </li>
          <li class="${request.servletPath == '/WEB-INF/pages/letsencrypt/challenges.gtpl' ? 'active' : ''}">
            <a href="/letsencrypt/challenges">Lets Encrypt Challenges</a>
          </li>
        <% } %>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <% if (session != null && session.getAttribute("githubUsername") != null) { %>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
            <img src="<%=session.getAttribute("githubAvatar")%>" height="50px"
                 style="margin-top: -18px;margin-bottom: -16px;"/>
            <%=session.getAttribute("githubUsername")%>
          </a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="/l/<%=session.getAttribute("githubUsername")%>">My List</a></li>
            <li><a href="/g/<%=session.getAttribute("githubUsername")%>">Random in My List</a></li>
            <li class="divider"></li>
            <li><a href="/auth/logout">Logout</a></li>
          </ul>
        </li>
        <% } else { %>
        <li>
          <a href="/auth/github">
            Login with <img src="/images/GitHub_Logo.png" alt="Github" height="20px"
                            class="github"/>
          </a>
        </li>
        <% } %>
      </ul>
    </div>
    <!--/.nav-collapse -->
  </div>
</div>