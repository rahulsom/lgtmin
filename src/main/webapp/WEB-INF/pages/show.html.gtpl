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
            <img src="${image.imageUrl}" alt="LGTM"/>
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
        <input type="text" value="${image.imageUrl}" class="span8">
        <label>Data Url</label>
        <input type="text" value="${image.dataUrl}" class="span8">
        <label>Markdown</label>
        <textarea class="span8" rows="4">${image.markdown.replace("\\n", '\n')}</textarea>
      </fieldset>
    </form>
  </div>
</div>
<% if(request.getAttribute('comments')) { %>
<div class="row">
  <div class="span12">
    <div id="disqus_thread"></div>
    <script type="text/javascript">
      /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
      var disqus_shortname = 'lgtmin'; // required: replace example with your forum shortname

      /* * * DON'T EDIT BELOW THIS LINE * * */
      (function() {
        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
        dsq.src = '//' + disqus_shortname + '.disqus.com/embed.js';
        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
      })();
    </script>
    <noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
    <a href="http://disqus.com" class="dsq-brlink">comments powered by <span class="logo-disqus">Disqus</span></a>
  </div>
</div>
<% } %>
<% include '/WEB-INF/includes/footer.gtpl' %>

