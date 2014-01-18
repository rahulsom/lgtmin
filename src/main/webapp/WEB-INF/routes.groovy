
get "/", forward: "/index.groovy"
get "/g", forward: "/random.groovy"
get "/g/upload", forward: "/upload.groovy"
post "/g/save", forward: "/store.groovy"
get "/i/@hash", forward: "/load.groovy?hash=@hash"
get "/u/@hash", forward: "/upvote.groovy?hash=@hash"
get "/r/@hash", forward: "/report.groovy?hash=@hash"
get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png", cache: 100.hours
get "/robots.txt/", redirect: "/robots.txt", cache: 100.hours
