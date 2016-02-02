<% include '/WEB-INF/includes/header.gtpl' %>
<div class="row" id="imageList">
    <% include '/WEB-INF/pages/browse.gtpl' %>
</div>
<script>
window.onload = function() { // this will be run when the whole page is loaded
    jQuery('#imageList').infinitescroll({
        navSelector: '.navsystem',
        nextSelector: 'a.next',
        debug: true,
        itemSelector: '.browseImage',
        animate: true
    }, function(elems){
    console.log(elems);
    });
};
</script>
<% include '/WEB-INF/includes/footer.gtpl' %>
