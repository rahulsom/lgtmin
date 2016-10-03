<% def image = request.getAttribute('image') %>
<html>
    <head>
        <meta name="twitter:card" content="summary" />
        <meta name="twitter:site" content="@lgtmin" />
        <meta name="twitter:title" content="Looks good to me" />
        <meta name="twitter:description" content="View the image on LGTM.in." />
        <meta name="twitter:image" content="${image.imageUrl}" />
    </head>
    <body>
        <% if (image.isDeleted) { %>
          <div class="alert alert-warning">This image has been deleted.</div>
        <% } %>
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
            <br/><br/>
            <div class="row">

                <% def favClass = request.getAttribute('favorite') == 'true' ? 'btn-info' : 'btn-default' %>
                <div class="col-md-3">
                    <a class="btn ${favClass} btn-block btn-lg" href="/m/${image.hash}">
                        <span class="glyphicon glyphicon-star"></span> My List
                    </a>
                </div>
                <% if (request.getAttribute('allowDelete')) { %>
                <div class="col-md-3">
                  <a class="btn btn-danger btn-block btn-lg" href="${image.deleteUrl}">
                    <span class="glyphicon glyphicon-trash"></span> Delete
                  </a>
                </div>
                <div class="col-md-5">
                  <% if (image.uploader) { %>
                    <div class="btn-group">
                      <button class="btn btn-default btn-lg dropdown-toggle" type="button" data-toggle="dropdown"
                            aria-haspopup="true" aria-expanded="false">
                        @${image.uploader} <span class="caret"></span>
                      </button>
                      <ul class="dropdown-menu">
                        <li><a href="https://github.com/${image.uploader}">Github</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="${image.deleteUrl}?ban=true">Delete image and ban user</a></li>
                        <li><a href="mailto:${image.uploaderEmail}" data-toggle="modal" data-target="#myModal">Message User</a></li>
                      </ul>
                    </div>

                    <!-- Modal -->
                    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="myModalLabel">Send Message to User</h4>
                          </div>
                          <form action="/mailUser" method="post">
                          <div class="modal-body">
                            <p>From: admin@lgtm.in</p>
                            <input type="hidden" name="email" value="${image.uploaderEmail}">
                            <input type="hidden" name="username" value="${image.uploader}">
                            <p>To: ${image.uploaderEmail}</p>
                            <p>Subject: lgtm.in Fair Use Policy</p>
                            <textarea style="width: 100%; height: 200px;" name="message">
Hi @${image.uploader}

lgtm.in is an app that makes it possible for users of sites like github to show their approval of a pull request.
It is being used in other contexts too, and we are glad for all the support from our users. A good number of our
users use this app from work, or for work.

We prefer to keep porn/hatespeech/<whatever else that could make this community look bad> out of here.

It appears that this image you've uploaded fits in one of those categories - ${image.dataUrl}.

We request that you refrain from uploading such content.

Thank you!

lgtm.in
                            </textarea>
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <input type="submit" class="btn btn-primary" value="Send"></input>
                          </div>
                          </form>
                        </div>
                      </div>
                    </div>
                  <% } %>
                </div>
                <% } %>
            </div>
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

    </body>
</html>
