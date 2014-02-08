<% include '/WEB-INF/includes/header.gtpl' %>
<div class="row">
	<% def imageList = request.getAttribute('imageList') %>
	<% imageList.each { %>
	<div class="col-md-2 col-sm-3 col-xs-6">
		<div class="thumbnail">
			<div class="image" style="height: 162px; overflow-y: hidden; background-color: #F5F5F5;">
				<a href="${it.dataUrl}">
					<img alt="" src="${it.imageUrl}" style="max-height: 200px;">
				</a>
			</div>
			<div class="row">
				<div class="col-md-6">
					<img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/moneybag.png" alt="Credits"
							 style="height: 16px; width: 16px;" data-toggle="tooltip" title="Credits"/>
					${it.credits}
				</div>
				<div class="col-md-6" style="text-align: right;">
					${it.impressions}
					<img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/octocat.png" alt="Impressions"
							 style="height: 16px; width: 16px;" data-toggle="tooltip" title="Impressions"/>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<a href="${it.upvoteUrl}" data-toggle="tooltip" title="Upvote">
						<img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/+1.png" alt="Like"
								 style="height: 16px; width: 16px;"/>
					</a>
				</div>
				<div class="col-md-6" style="text-align: right;">
					<a href="${it.reportUrl}" data-toggle="tooltip" title="Report">
						<img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/-1.png" alt="Report"
								 style="height: 16px; width: 16px;"/>
					</a>
				</div>
			</div>

		</div>
	</div>
	<% } %>
</div>

<div class="row">
  <div class="col-md-6">
    <h2>What</h2>
    <p>
      LGTM is the most popular comment on Github, perhaps. But LGTM is boring. So we spice it up with some images.
    </p>
    <div class="row">
      <div class="col-md-6">
        <h4>Boring</h4>
        <img src="/images/text.png" alt="LGTM as text" width="100%"/>
      </div>
      <div class="col-md-6">
        <h4>Fun</h4>
        <img src="/images/fun.png" alt="LGTM with fun" width="100%"/>
      </div>
    </div>
    <h2>Why</h2>
    <p>
      Crafting images and picking them works fine on a leisurely day, but at crunch time, it helps to crowdsource
      your LGTMs.
    </p>
  </div>
  <div class="col-md-6">
    <h2>Quick and Easy</h2>
    <p>
      This is the javascript in the bookmarklet. Feel free to create your own if you don't like
      the default implementation.
    </p>
    <pre>
\$.getJSON("http://www.lgtm.in/g", function (data) {
  var oldMessage = \$("textarea[name='comment[body]']").val();
  var msg = data.markdown;
  \$("textarea[name='comment[body]']").val(oldMessage + "\\n\\n" + msg);
});</pre>
    <h2>Contributing</h2>
    <p>
      The project is hosted on Github at
      <a href="https://github.com/rahulsom/lgtmin">rahulsom/lgtmin</a>. Feel free to fork it and send a Pull Request.

      It also serves as the issue tracking system.
    </p>
  </div>
</div>

<!--
<div class="row">
  <div class="col-md-4">
    <h2>Start Experimenting</h2>

    <p>This template contains following sample files<ul><li><code>datetime.groovy</code>
  </li><li><code>WEB-INF/pages/datetime.gtpl</code></li></ul>Try to edit them and watch the changes.</p>
  </div>

  <div class="col-md-4">
    <h2>Learn More</h2>

    <p>All <a href="http://gaelyk.appspot.com">Gaelyk</a> features are well documented. If you are new to <a
        href="http://gaelyk.appspot.com">Gaelyk</a> best place to learn more is the <a
        href="http://gaelyk.appspot.com/tutorial">Tutorial</a>.</p>

    <p><a class="btn btn-default" href="http://gaelyk.appspot.com/tutorial">Read Tutorial &raquo;</a></p>
  </div>

  <div class="col-md-4">
    <h2>Work Less</h2>

    <p>Take advantage of existing plugins. You can for example unleash the power of <a
        href="http://developer.google.com/appengine/">Google App Engine</a> using <a
        href="https://github.com/musketyr/gpars-appengine">GPars App Engine</a> integration library</p>

    <p><a class="btn btn-default" href="http://gaelyk.appspot.com/plugins">More about plugins &raquo;</a></p>
  </div>
</div>
-->
<% include '/WEB-INF/includes/footer.gtpl' %>

