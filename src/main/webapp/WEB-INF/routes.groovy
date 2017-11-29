// Global Pages
all "/",                                          forward: "/index.groovy"
all "/browse",                                    forward: "/browse.groovy"
all "/g",                                         forward: "/random.groovy"
all "/upload",                                    forward: "/upload.groovy"
post "/save",                                     forward: "/store.groovy"

// Image Pages
all "/i/@hash",                                   forward: "/load.groovy?hash=@hash"
all "/u/@hash",                                   forward: "/upvote.groovy?hash=@hash"
all "/m/@hash",                                   forward: "/mylist.groovy?hash=@hash"
all "/r/@hash",                                   forward: "/report.groovy?hash=@hash"
all "/p/@hash",                                   forward: "/picture.groovy?hash=@hash"
all "/d/@hash",                                   forward: "/delete.groovy?hash=@hash"

// User Pages
all "/g/@username",                               forward: "/random.groovy?username=@username"
all "/l/@username",                               forward: "/userlist.groovy?username=@username"

// SE and Browser Pages
all "/favicon.ico",                               redirect: "/images/gaelyk-small-favicon.png"
all "/robots.txt/",                               redirect: "/robots.txt"
all "/humans.txt/",                               redirect: "/humans.txt"

// Cron Jobs
all "/cron/revalidate",                           forward: "/cron/revalidate.groovy"
all "/cron/clearsessions",                        forward: "/cron/clearsessions.groovy"

// Auth Pages
all "/auth/github",                               forward: "/auth/github.groovy"
all "/auth/me",                                   forward: "/auth/me.groovy"
all "/auth/logout",                               forward: "/auth/logout.groovy"

// Admin Actions
post "/mailUser",                                 forward: "/mailUser.groovy"
get "/banned",                                    forward: "/banned.groovy"
get "/unban/@username",                           forward: "/unban.groovy?username=@username"

get '/jwt/generate',                              forward: '/tokens/gentoken.groovy'

all "/_ah/warmup",                                forward: "/warmup.groovy"
