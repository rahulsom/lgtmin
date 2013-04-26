<% include '/WEB-INF/includes/header.gtpl' %>
  <h1>Upload</h1>

  <form action="/g/save" method="post">
    <fieldset>
      <label for="imageUrl">Image Url</label>
      <input type="text" placeholder="http://i.imgur.com/ABCDEF.png" class="span7" name="imageUrl"
          value="<%=request.getAttribute('imageUrl')%>">
      <label class="checkbox">
      </label>
      <button type="submit" class="btn">Submit</button>
    </fieldset>
  </form>
  <p>
    <%
      log.info "outputing the datetime attribute"
    %>
  </p>
<% include '/WEB-INF/includes/footer.gtpl' %>

