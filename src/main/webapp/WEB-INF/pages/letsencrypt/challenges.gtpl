<html>
    <head>
        <title>letsencrypt Challenges</title>
    </head>
    <body>
        <% def challenges = request.getAttribute('challenges') %>
        <table class="table">
            <tr>
                <th>Challenge</th>
                <th>Response</th>
                <th>&nbsp;</th>
            </tr>
            <% challenges.each { %>
            <tbody>
                <tr>
                    <td>${it.challengeText}</td>
                    <td>${it.responseText}</td>
                    <td><a href="/letsencrypt/delete/${it.id}">Delete</a></td>
                </tr>
            </tbody>
            <% } %>
            <tbody>
                <form action="/letsencrypt/save" method="post" class="form-inline">
                <tr>
                    <td>
                        <input placeholder="Challenge Text" class="form-control" name="challengeText">
                    </td>
                    <td>
                        <input placeholder="Response Text" class="form-control" name="responseText">

                    </td>
                    <td><button type="submit" class="btn btn-default">Submit</button></td>
                </tr>
                </form>
            </tbody>
        </table>
    </body>
</html>