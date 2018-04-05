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
    <div class="thumbnail ${it.favorited ? 'favorited' : 'not-favorited'}">
        <div class="image" style="height: 162px; overflow-y: hidden; background-color: #F5F5F5;">
            <a href="${appUtil.patchUrl(it.dataUrl, request)}">
            <% if (it.embedUrl) {%>
                <iframe src="${it.embedUrl}" width="200" height="220" scrolling="no"
                        style="border:none; -ms-zoom: 0.75;
                                                    -moz-transform: scale(0.75);
                                                    -moz-transform-origin: 0 0;
                                                    -o-transform: scale(0.75);
                                                    -o-transform-origin: 0 0;
                                                    -webkit-transform: scale(0.75);
                                                    -webkit-transform-origin: 0 0;"></iframe>
            <% } else {%>
                <img alt="" src="${appUtil.patchUrl(it.imageUrl, request)}" style="max-height: 200px;">
            <% } %>
            </a>
        </div>
        <div class="row buttons">
            <div class="col-md-3 col-xs-3 col-sm-3">
                <a href="${it.upvoteUrl.replace(appUtil.root, request.contextPath)}"
                   data-toggle="tooltip" title="Upvote">
                    <i class="fa fa-thumbs-o-up" aria-hidden="true"></i>
                </a>
            </div>
            <div class="col-md-3 col-xs-3 col-sm-3">
                <a href="/m/${it.hash}"
                   data-toggle="tooltip" title="${it.favorited ? 'Remove from my list' : 'Add to my list'}">
                    <i class="fa fa-heart-o" aria-hidden="true"></i>
                    <i class="fa fa-heart" aria-hidden="true"></i>
                </a>
            </div>
            <div class="col-md-6 col-xs-6 col-sm-6" style="text-align: right;">
                <a href="${appUtil.patchUrl(it.reportUrl, request)}"
                   data-toggle="tooltip" title="Report">
                    <i class="fa fa-thumbs-o-down" aria-hidden="true"></i>
                </a>
            </div>
        </div>
    </div>
</div>
<% } %>
