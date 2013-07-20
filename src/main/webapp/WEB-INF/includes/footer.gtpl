    </div>
    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/js/bootstrap.min.js"></script>
    <script type="text/javascript">
      jQuery(function(){
        var protipCookie = jQuery.cookie('protip1');
        console.log ('Protip Cookie: ' + protipCookie);
        if (!protipCookie) {
          console.log ('Showing...');
          jQuery('#protip1').show();
          console.log ('...done');
        }

        jQuery('#protip1 .close').click(function(){
          jQuery.cookie('protip1', 'true');
        });

      });
    </script>
    <script>
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
          m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

      ga('create', 'UA-40490747-1', 'lgtm.in');
      ga('send', 'pageview');

    </script>
  </body>
</html>