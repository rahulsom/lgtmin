get "/", 						forward: "/index.groovy",											cache: 24.hours
get "/g", 					forward: "/random.groovy"
get "/g/upload",  	forward: "/upload.groovy",										cache: 24.hours
post "/g/save", 		forward: "/store.groovy"
get "/i/@hash", 		forward: "/load.groovy?hash=@hash",						cache: 2400.hours
get "/u/@hash", 		forward: "/upvote.groovy?hash=@hash"
get "/r/@hash", 		forward: "/report.groovy?hash=@hash"
get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png", cache: 2400.hours
get "/robots.txt/", redirect: "/robots.txt", 											cache: 2400.hours
