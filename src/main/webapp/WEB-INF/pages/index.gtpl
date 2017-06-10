<html>
    <head>
        <title>Home</title>
    </head>
    <body>
        <div class="row">
            <% def imageList = request.getAttribute('imageList') %>
            <% def appUtil = request.getAttribute('appUtil') %>
            <% imageList.each { %>
            <div class="col-md-2 col-sm-3 col-xs-6">
                <div class="thumbnail">
                    <div class="image" style="height: 162px; overflow-y: hidden; background-color: #F5F5F5;">
                        <a href="${appUtil.patchUrl(it.dataUrl, request)}">
                            <img alt="" src="${appUtil.patchUrl(it.imageUrl, request)}" style="max-height: 200px;">
                        </a>
                    </div>
                    <div class="row">
                        <div class="col-md-6 col-xs-6 col-sm-6">
                            <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/moneybag.png"
                                 alt="Credits" style="height: 16px; width: 16px;" data-toggle="tooltip" title="Credits"/>
                            ${it.credits}
                        </div>
                        <div class="col-md-6 col-xs-6 col-sm-6" style="text-align: right;">
                            ${it.impressions}
                            <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/octocat.png"
                                 alt="Impressions" style="height: 16px; width: 16px;" data-toggle="tooltip" title="Impressions"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 col-xs-6 col-sm-6">
                            <a href="${it.upvoteUrl.replace(appUtil.root, request.contextPath)}"
                               data-toggle="tooltip" title="Upvote">
                                <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/+1.png"
                                     alt="Like" style="height: 16px; width: 16px;"/>
                            </a>
                        </div>
                        <div class="col-md-6 col-xs-6 col-sm-6" style="text-align: right;">
                            <a href="${appUtil.patchUrl(it.reportUrl, request)}"
                               data-toggle="tooltip" title="Report">
                                <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/-1.png"
                                     alt="Report" style="height: 16px; width: 16px;"/>
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
              Looks good to me <em>(LGTM)</em> is the most popular comment on Github, perhaps. But LGTM is boring. So we
                spice it up with some images.
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
              <h2>Quick and Easy</h2>
              <p>
                  This is the effective javascript in the extension. Feel free to create your own if you don't like
                  the default implementation. The extension source is
                            <a href="https://github.com/rahulsom/lgtm-chrome">here</a>.
              </p>
            <pre>
        \$.getJSON("http://www.lgtm.in/g", function (data) {
          \$("textarea[name='comment[body]']").
             append("\\n\\n" + data.markdown);
        });</pre>
          </div>
          <div class="col-md-6">

            <h2>Contributing</h2>
            <p>
              The project is hosted on Github at
              <a href="https://github.com/rahulsom/lgtmin">rahulsom/lgtmin</a>. Feel free to fork it and send a Pull Request.

              It also serves as the issue tracking system.
            </p>
              <p>
              There is the occasional porn/hatespeech/whatever else that makes this community look bad. If you see that,
                please send a tweet out to <a href="http://twitter.com/rahulsom">me</a>, and I'll exercise the kill switch.
            </p>
            <h2>Community</h2>
            <ul>
               <li>
                   <a href="https://chrome.google.com/webstore/detail/lgtm/oeacdmeoegfagkmiecjjikpfgebmalof">Google Chrome
                       Extension</a> from <a href="https://github.com/monochromegane/LGTM">monochromegane</a>
               </li>
               <li>
                   <a href="https://addons.mozilla.org/en-US/firefox/addon/lgtm-for-firefox/?src=cb-dl-created">Firefox Add-on</a>
                   from <a href="https://github.com/kumabook">kumabook</a>
               </li>
               <li>
                   <a href="https://github.com/geckotang/alfred-lgtmin-workflow">On Alfred 2</a> by
                   <a href="https://github.com/geckotang">GeckoTang</a>
               </li>
                <li>
                    <a href="http://lgtm.herokuapp.com/">LGTM on Heroku</a> kicks ass if you want to generate gifs with
                       our words embedded in them. <a href="https://github.com/negipo/lgtm">Source</a>
                </li>
                <li>
                    <a href="http://lgtmin-gh-bmlt.herokuapp.com/">LGTMin GH BMLT</a> by
                    <a href="https://github.com/yukihr/lgtmin-github-bookmarklet">yukihr</a>
                </li>
                <li>
                    <a href="https://rubygems.org/gems/lgtm_hd">Ruby CLI gem for generating or converting LGTM pic with live preview on terminal</a> by
                    <a href="https://github.com/phradion/lgtm_hd">phradion</a>
                </li>
                <li>
                    <a href="https://chrome.google.com/webstore/detail/lgtm-reloaded/hefidgcceobmmaiekccmbjpdcmbjklej">LGTM Reloaded</a>(Chrome Extension) by
                    <a href="https://github.com/mediavrog/lgtm">AIR_enjapan</a>
                </li>
            </ul>
            <h2>On Twitter</h2>
            <a class="twitter-timeline" href="https://twitter.com/search?q=LGTM.in" data-widget-id="439281581404852225">Tweets about "LGTM.in"</a>
            <script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+"://platform.twitter.com/widgets.js";fjs.parentNode.insertBefore(js,fjs);}}(document,"script","twitter-wjs");</script>


          </div>
        </div>
    </body>
</html>
