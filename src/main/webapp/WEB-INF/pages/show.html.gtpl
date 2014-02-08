<% include '/WEB-INF/includes/header.gtpl' %>
<% def image = request.getAttribute('image') %>
<div class="row">
  <div class="col-md-4">
        <div class="thumbnail">
          <a href="${image.dataUrl}">
            <img src="${image.imageUrl}" alt="LGTM"/>
          </a>
        </div>
  </div>
  <div class="col-md-8">
    <form>
  		<div class="form-group>
        <label for="imageUrl">Image Url</label>
        <input type="text" value="${image.imageUrl}" class="form-control" id="imageUrl" name="imageUrl">
			</div>
  		<div class="form-group>
        <label for="dataUrl">Data Url</label>
        <input type="text" value="${image.dataUrl}" class="form-control" id="dataUrl" name="dataUrl">
			</div>
  		<div class="form-group>
        <label for="markdown">Markdown</label>
        <textarea class="form-control" rows="4" id="markdown" name="markdown"
        		>${image.markdown.replace("\\n", '\n')}</textarea>
			</div>
    </form>
  </div>
</div>
<% if(request.getAttribute('comments')) { %>
<div class="row">
  <div class="col-md-12">
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

