<div class="navsystem">
    <% if (request.getAttribute('next')) { %>
        <a href="?page=${request.getAttribute('next')}" class="next">Next</a>
    <% } %>
    <% if (request.getAttribute('prev') != null) { %>
        <a href="?page=${request.getAttribute('prev')}" class="prev">Prev</a>
    <% } %>
</div>
<% def imageList = request.getAttribute('imageList') %>
<% def appUtil = request.getAttribute('appUtil') %>
<% imageList.each { %>
<div class="col-md-2 col-sm-3 col-xs-6 browseImage">
    <div class="thumbnail">
        <div class="image" style="height: 162px; overflow-y: hidden; background-color: #F5F5F5;">
            <a href="${appUtil.patchUrl(it.dataUrl, request)}">
                <img alt="" src="${appUtil.patchUrl(it.imageUrl, request)}" style="max-height: 200px;">
            </a>
        </div>
        <div class="row">
            <div class="col-md-6 col-xs-6 col-sm-6">
                <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/moneybag.png" alt="Credits"
                     style="height: 16px; width: 16px;" data-toggle="tooltip" title="Credits"/>
                ${it.credits}
            </div>
            <div class="col-md-6 col-xs-6 col-sm-6" style="text-align: right;">
                ${it.impressions}
                <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/octocat.png" alt="Impressions"
                     style="height: 16px; width: 16px;" data-toggle="tooltip" title="Impressions"/>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6 col-xs-6 col-sm-6">
                <a href="${appUtil.patchUrl(it.upvoteUrl, request)}" data-toggle="tooltip" title="Upvote">
                    <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/+1.png" alt="Like"
                         style="height: 16px; width: 16px;"/>
                </a>
            </div>
            <div class="col-md-6 col-xs-6 col-sm-6" style="text-align: right;">
                <a href="${appUtil.patchUrl(it.reportUrl, request)}" data-toggle="tooltip" title="Report">
                    <img src="https://a248.e.akamai.net/assets.github.com/images/icons/emoji/-1.png" alt="Report"
                         style="height: 16px; width: 16px;"/>
                </a>
            </div>
        </div>
    </div>
</div>
<% } %>
