<% include '/WEB-INF/includes/header.gtpl' %>
  <h1>Random Picture</h1>

  <p>
    <%
      log.info "Fetching the image attr"
      def image = request.getAttribute('image')
    %>
<div class="row">
  <div class="span5">
    <img src="<%=image.imageUrl%>" alt="LGTM" class="span4"/>
  </div>
  <div class="span7">
    <form>
      <fieldset>
        <legend>Picture Info</legend>
        <label>Image Url</label>
        <input type="text" value="<%=image.imageUrl%>" class="span7">
        <label>Data Url</label>
        <input type="text" value="<%="http://lgtm.in/i/${image.hash}"%>" class="span7">
        <label>Markdown</label>
        <input type="text" value="[![LGTM](<%=image.imageUrl%>)](<%="http://lgtm.in/i/${image.hash}"%>)" class="span7">
      </fieldset>
    </form>
  </div>
</div>
  </p>
<% include '/WEB-INF/includes/footer.gtpl' %>

