<!doctype html>
<html>
<body><% def imgurId = request.getAttribute('imgurId') %>
<div id="content">
    <div class="embed-main-image">
        <div class="panel">
            <div class="image-panel">
                <div id="image" class="image">
                    <div class="video-container">
                        <script type="text/javascript">
                            var videoItem = {
                                looping:  true,
                                width:    100,
                                height:   100,
                                hash:     '${imgurId}'
                            };
                        </script>
                        <video class="post" poster="https://i.imgur.com/${imgurId}h.jpg"
                               preload="auto" autoplay="autoplay" muted="muted" loop="loop"
                               webkit-playsinline height="100%" width="100%"></video>
                        <div class="video-elements">
                            <source src="https://i.imgur.com/${imgurId}.mp4" type="video/mp4">
                        </div>
                        <script id="script${imgurId}" type="text/javascript"
                                src="https://s.imgur.com/min/imageViewerInline.js?1522865199"></script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
