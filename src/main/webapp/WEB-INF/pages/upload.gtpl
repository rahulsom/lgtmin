<html>
    <head>
        <title>Upload</title>
    </head>
    <body>
        <h1>Upload</h1>

        <% if (request.getAttribute('banned') == true) { %>
            <div class="alert alert-warning" role="alert">
                You have been banned from uploading images for violating our content policy. You may still use
            </div>
        <% } else { %>
            <form action="/save" method="post">
                <div class="form-group>
                    <label for="imageUrl">Image Url</label>
                    <input type="url" placeholder="http://i.imgur.com/ABCDEF.png"
                            class="form-control" name="imageUrl"
                            value="<%=request.getAttribute('imageUrl') ?: ''%>">
                    <label class="checkbox">
                    </label>
                </div>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
        <% } %>
    </body>
</html>