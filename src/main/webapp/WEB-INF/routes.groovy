
get "/", forward: "/index.groovy"
get "/g", forward: "/random.groovy"
get "/g/upload", forward: "/upload.groovy"
post "/g/save", forward: "/store.groovy"
get "/i/@hash", forward: "/load.groovy?hash=@hash"
get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"
