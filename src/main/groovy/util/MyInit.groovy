package util

import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestInitializer

/**
 * Created by rahulsomasunderam on 2/3/14.
 */
class MyInit implements HttpRequestInitializer {
    MyInit() {
    }

    private String contentType

    MyInit(String contentType) {
        this.contentType = contentType
    }

    @Override
    public void initialize(HttpRequest request) {
        if (contentType) {
            request.headers.put('Accept', [contentType])
        }
    }
}
