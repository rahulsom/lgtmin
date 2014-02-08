<% include '/WEB-INF/includes/header.gtpl' %>
  <h1>Upload</h1>

  <form action="/g/save" method="post">
  	<div class="form-group>

		<label for="imageUrl">Image Url</label>
		<input type="url"
		placeholder="http://i.imgur.com/ABCDEF.png" class="form-control" name="imageUrl"
				value="<%=request.getAttribute('imageUrl') ?: ''%>">
		<label class="checkbox">
		</label>
		</div>
		<button type="submit" class="btn btn-default">Submit</button>
  </form>
  <p>
    <%
      log.info "outputing the datetime attribute"
    %>
  </p>
<% include '/WEB-INF/includes/footer.gtpl' %>

