<h1>Challenges</h1>
<% def challenges = request.getAttribute('challenges') %>
<table class="tables">
    <tr>
        <th>Challenge</th>
        <th>Response</th>
    </tr>
<% challenges.each { %>
    <tr>
        <td>${it.challengeText}</td>
        <td>${it.responseText}</td>
    </tr>
<% } %>
</table>

<h1>New Challenge</h1>
<form action="/letsencrypt/save" method="post">
        <label>Challenge</label>
        <input placeholder="Challenge Text" class="form-control" name="challengeText">
        <label>Response</label>
        <input placeholder="Response Text" class="form-control" name="responseText">
    <button type="submit" class="btn btn-default">Submit</button>
</form>
