package com.oklib.widget.imageloader.glide.listener;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Damon.Han
 */
public class ProgressDataFetcher implements DataFetcher<InputStream> {
    private static final String TAG = "ProgressDataFetcher";
    private final String url;
    private Call progressCall;
    private InputStream stream;
    private volatile boolean isCancelled;
    private ProgressLoadListener proListener;

    public ProgressDataFetcher(String url, ProgressLoadListener listener) {
        this.url = url;
        this.proListener = listener;
    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), proListener))
                                .build();
                    }
                })
                .build();
        try {
            progressCall = client.newCall(request);
            Response response = progressCall.execute();
            if (isCancelled) {
                return null;
            }
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            stream = response.body().byteStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream;
    }

    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (progressCall != null) {
            progressCall.cancel();
        }
    }

    @Override
    public String getId() {
        return url;
    }

    @Override
    public void cancel() {
        // TODO: we should consider disconnecting the url connection here, but we can't do so directly because cancel is
        // often called on the main thread.
        isCancelled = true;
    }
}
