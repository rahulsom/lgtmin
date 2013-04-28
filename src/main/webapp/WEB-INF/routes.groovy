
get "/", forward: "/index.groovy", cache: 5.minutes
get "/g", forward: "/random.groovy"
get "/g/upload", forward: "/upload.groovy", cache: 24.hours
post "/g/save", forward: "/store.groovy"
get "/i/@hash", forward: "/load.groovy?hash=@hash", cache: 1.hours
get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png", cache: 100.hours
