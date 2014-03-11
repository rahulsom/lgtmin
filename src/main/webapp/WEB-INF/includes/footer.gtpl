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
        <script defer type="text/javascript">
            (function() {
                function getScript(url,success){
                    var script=document.createElement('script');
                    script.src=url;
                    var head=document.getElementsByTagName('head')[0],
                            done=false;
                    script.onload=script.onreadystatechange = function(){
                        if ( !done && (!this.readyState || this.readyState == 'loaded' || this.readyState == 'complete') ) {
                            done=true;
                            success();
                            script.onload = script.onreadystatechange = null;
                            head.removeChild(script);
                        }
                    };
                    head.appendChild(script);
                }
                getScript('http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js',function(){
                getScript('http://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.0/jquery.cookie.min.js',function(){
                getScript('http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js',function(){
                    jQuery(function () {
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
                    });
                });
                });
                });
            })();

            (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
            })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

            ga('create', 'UA-40490747-1', 'lgtm.in');
            ga('send', 'pageview');

        </script>
        </body>
</html>