<!doctype html>
<html>
<head>
  <title>LGTM.in/g</title>
  <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png">
  <link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png">
  <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css"/>
  <link rel="stylesheet" type="text/css" href="/css/bootstrap-responsive.min.css"/>
  <style type="text/css">
  body {
    padding-top: 60px;
  }

  .center {
    text-align: center;
  }

  span#title {
    font-size: 72px;
  }
  </style>
</head>

<body>
<div class="navbar navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <a class="btn btn-navbar" data-toggle="collapse"
         data-target=".nav-collapse"><span class="icon-bar"></span> <span
          class="icon-bar"></span> <span class="icon-bar"></span>
      </a> <a class="brand" href="/">
      LGTM.in/g
      </a>

      <div class="nav-collapse">
        <ul class="nav">
          <li class="${request.servletPath == '/WEB-INF/pages/index.gtpl' ? 'active' : ''}"><a href="/">Home</a></li>
          <li class="${request.requestURI == '/g' ? 'active' : ''}"><a href="/g">Random</a></li>
          <li class="${request.servletPath == '/WEB-INF/pages/upload.gtpl' ? 'active' : ''}"><a
              href="/g/upload">Submit</a></li>
        </ul>
        <ul class="nav pull-right">
        <%
          if(session.getAttribute('username')) {
        %>
          <li><a href="#"><%=session.getAttribute('username')%></a></li>
        <%
          } else {
        %>
          <li><a href="/auth/github">Login with github</a></li>
        <%
          }
        %>
        </ul>
      </div>
      <!--/.nav-collapse -->
    </div>
  </div>
</div>

<div class="container">
  <%
    if(request.getAttribute('message')) {
  %>
    <div class="alert">
      <%=request.getAttribute('message')%>
    </div>
  <%
    }
  %>

  <div class="row">
    <div class="span12">
      <div class="alert alert-block alert-info hide" id="protip1">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <strong>Pro Tip:</strong> Drag this bookmarklet to your favorites bar. You can then LGTM with a
        click of a bookmarklet. <a
          href='javascript:(function () {
            jQuery.getJSON("http://www.lgtm.in/g", function (data) {
                var oldMessage = jQuery("textarea[name=\"comment[body]\"]").val();
                var msg = data.markdown;
                jQuery("textarea[name=\"comment[body]\"]").val(oldMessage + "\n\n" + msg);
            });
            })();'><span class="label label-info">LGTM</span></a>
      </div>

    </div>
  </div>