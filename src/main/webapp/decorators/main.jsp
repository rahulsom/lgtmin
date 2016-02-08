<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<!doctype html>
<html>
  <head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name=viewport content="width=device-width, initial-scale=1, minimal-ui"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>

    <title>LGTM.in &raquo; <decorator:title/></title>

    <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png?q=1" type="image/png"/>
    <link rel="icon" href="/images/gaelyk-small-favicon.png?q=1" type="image/png"/>
    <link rel="author" href="/humans.txt"/>
    <link rel='stylesheet' type='text/css' href='//fonts.googleapis.com/css?family=Cabin+Sketch'/>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/app.css"/>
    <link rel="chrome-webstore-item"
          href="https://chrome.google.com/webstore/detail/fagoekogpjjhmdnmpgpnahioapbeaajp"/>

    <decorator:head/>

    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
  </head>
  <body>

    <%@include file="nav.jsp" %>

    <div class="container">
      <%@include file="messages.jsp" %>
      <%@include file="protip.jsp" %>

      <decorator:body/>

    </div>

    <%@include file="footer.jsp" %>

    <script type="text/javascript"
            src="//cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.0/jquery.cookie.min.js"></script>
    <script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.min.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/detectizr/1.5.0/detectizr.min.js"></script>
    <script type="text/javascript"
            src="//cdnjs.cloudflare.com/ajax/libs/jquery-infinitescroll/2.1.0/jquery.infinitescroll.js"></script>
    <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/lodash.js/4.2.1/lodash.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script>

  </body>
</html>
