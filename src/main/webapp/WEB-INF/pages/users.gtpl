<% users.each { %>
<div class="col-md-2 col-sm-3 col-xs-6 browseImage">
    <div class="thumbnail">
        <div class="image" style="height: 162px; overflow-y: hidden; background-color: #F5F5F5;">
            <a href="https://github.com/${it.username}">
                <img alt="" src="https://github.com/${it.username}.png?size=200" style="max-height: 200px;">
            </a>
        </div>
    </div>
</div>
<% } %>