        </div>
        <div id="footer">
            <div class="container">
                <p class="text-muted">Help feed the developer: Use this link to buy stuff on
                    <a target="_blank"
                       href="http://www.amazon.com/?_encoding=UTF8&camp=1789&creative=390957&linkCode=ur2&tag=lig047-20">Amazon</a>.
                    <img src="https://ir-na.amazon-adsystem.com/e/ir?t=lig047-20&l=ur2&o=1" width="1" height="1"
                         border="0" alt="" style="border:none !important; margin:0px !important;" />
                    <!--
                     Just kidding guys; I've got a regular job. However I would appreciate the commission to keep me
                     motivated in delivering more features.
                    -->
                </p>
            </div>
        </div>
        <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
        <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.0/jquery.cookie.min.js"></script>
        <script type="text/javascript" src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.min.js"></script>
        <script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/detectizr/1.5.0/detectizr.min.js"></script>
        <script type="text/javascript">
            jQuery(function () {
                Modernizr.Detectizr.detect({detectScreen:false});

                if (Modernizr.Detectizr.device.browser === 'chrome' &&
                        Modernizr.Detectizr.device.type === 'desktop') {
                  jQuery('[data-toggle="tooltip"]').tooltip();

                  var protipCookie = jQuery.cookie('protip1');
                  console.log('Protip Cookie: ' + protipCookie);
                  if (!protipCookie) {
                      console.log('Showing...');
                      jQuery('#protip1').removeClass('hide');
                      console.log('...done');
                  }

                  jQuery('#protip1 .close').click(function () {
                      jQuery.cookie('protip1', 'true');
                  });

                }

            });

            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-40490747-1', 'lgtm.in');
            ga('require', 'displayfeatures');
            ga('send', 'pageview');

        </script>
        </body>
</html>
