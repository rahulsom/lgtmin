<!doctype html>
<html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name=viewport content="width=device-width, initial-scale=1, minimal-ui" />
    <meta name="apple-mobile-web-app-capable" content="yes" />

    <title>LGTM.in/g</title>

    <link rel="shortcut icon" href="/images/gaelyk-small-favicon.png" type="image/png" />
    <link rel="icon" href="/images/gaelyk-small-favicon.png" type="image/png" />
    <link rel="author" href="/humans.txt" />
    <link rel='stylesheet' type='text/css' href='http://fonts.googleapis.com/css?family=Cabin+Sketch'/>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/css/app.css"/>
</head>

<body>
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
                    <a href="/g/upload">Submit</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <% if (session?.getAttribute('githubUsername')) { %>
                <li>
                    <a href="/auth/logout">
                        <img src="<%=session?.getAttribute('githubAvatar')%>" height="18px"/>
                        <%=session?.getAttribute('githubUsername')%>
                    </a>
                </li>
                <% } else { %>
                <li>
                    <a href="/auth/github">
                        Login with <img src="/images/GitHub_Logo.png" alt="Github" height="20px" class="github"/>
                    </a>
                </li>
                <% } %>
            </ul>
        </div>
        <!--/.nav-collapse -->
    </div>
</div>

<div class="container">
        <% if(request.getAttribute('message')) { %>
    <div class="alert">
        <%= request.getAttribute('message') %>
    </div>
        <% } %>
        <% if(session?.getAttribute('message')) { %>
    <div class="alert">
        <%= session?.getAttribute('message') %>
        <% session?.removeAttribute('message') %>
    </div>
        <% } %>

    <div class="row">
        <div class="col-md-12">
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
