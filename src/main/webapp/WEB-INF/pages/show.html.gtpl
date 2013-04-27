<% include '/WEB-INF/includes/header.gtpl' %>

<%
  log.info "Fetching the image attr"
  def image = request.getAttribute('image')
%>
<div class="row">
  <div class="span4">
    <ul class="thumbnails">
      <li class="span4">
        <div class="thumbnail">
          <a href="${image.dataUrl}">
            <img src="<%=image.imageUrl%>" alt="LGTM"/>
          </a>
        </div>
      </li>
    </ul>
  </div>
  <div class="span8">
    <form>
      <fieldset>
        <legend>Picture Info</legend>
        <label>Image Url</label>
        <input type="text" value="<%=image.imageUrl%>" class="span8">
        <label>Data Url</label>
        <input type="text" value="<%="http://lgtm.in/i/${image.hash}"%>" class="span8">
        <label>Markdown</label>
        <input type="text" value="<%=image.markdown%>" class="span8">
      </fieldset>
    </form>
  </div>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>

