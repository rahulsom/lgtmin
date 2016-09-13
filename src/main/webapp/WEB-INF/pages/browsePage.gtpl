<html>
    <head>
        <% def username = request.getAttribute('username') %>
        <% if (username) { %>
            <title>@$username &raquo; List</title>

            <meta name="twitter:card" content="summary" />
            <meta name="twitter:site" content="@lgtmin" />
            <meta name="twitter:title" content="Looks good to me" />
            <meta name="twitter:description" content="Check out @${username}'s curated collection of LGTMs" />
            <meta name="twitter:image" content="https://github.com/${username}.png" />
        <% } else { %>
            <title>Browse</title>
        <% } %>
    </head>
    <body>
        <div class="row" id="imageList">
            <% include '/WEB-INF/pages/browse.gtpl' %>
        </div>
    </body>
</html>
