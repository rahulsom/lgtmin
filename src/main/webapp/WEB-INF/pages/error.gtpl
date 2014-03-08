<% include '/WEB-INF/includes/header.gtpl' %>
<% def image = request.getAttribute('image') %>
<div class="row">
    <div class="col-md-12">
        <div class="thumbnail">
            <a href="${image}">
                <img src="${image}" alt="LGTM"/>
            </a>
        </div>
    </div>
</div>
<% include '/WEB-INF/includes/footer.gtpl' %>

