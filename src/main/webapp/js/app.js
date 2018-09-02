var onNewImages = function (elems) {
    var imageList = $('#imageList');
    var prevLen = imageList.find('.browseImage').size();
    if (prevLen > 32 * 3) {
        var oldImages = _.chain(imageList.find('.browseImage')).take(32).value();
        oldImages.forEach(function (it) {
            $(it).remove();
        });
    }
    $('html, body').animate({
        scrollTop: $(elems[0]).offset().top
    }, 2000);
};
$('#imageList').infinitescroll({
    navSelector: '.navsystem',
    nextSelector: 'a.next',
    itemSelector: '.browseImage'
}/*, onNewImages*/);

$(function () {
    Modernizr.Detectizr.detect({detectScreen: false});

    if (Modernizr.Detectizr.device.browser === 'chrome' &&
            Modernizr.Detectizr.device.type === 'desktop') {
        $('[data-toggle="tooltip"]').tooltip();

        var protipCookie = $.cookie('protip1');
        console.log('Protip Cookie: ' + protipCookie);
        var protip1 = $('#protip1');
        if (!protipCookie) {
            console.log('Showing...');
            protip1.removeClass('hide');
            console.log('...done');
        }

        protip1.find('.close').click(function () {
            jQuery.cookie('protip1', 'true');
        });
    }

    $('a.disabled').on('click', function(event){
        event.preventDefault();
    })

});

(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','//www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-40490747-1', 'lgtm.in');
ga('require', 'displayfeatures');
ga('send', 'pageview');

