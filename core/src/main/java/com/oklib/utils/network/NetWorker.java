package com.oklib.utils.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.oklib.utils.network.Exception.NetworkException;
import com.oklib.utils.network.util.Utils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public final class NetWorker {
    private static Map<String, String> headers;
    private static Map<String, String> parameters;
    private static Retrofit.Builder retrofitBuilder;
    private static Retrofit retrofit;
    private static OkHttpClient.Builder okhttpBuilder;
    public static BaseApiService apiManager;
    private static OkHttpClient okHttpClient;
    private static Context mContext;
    private final okhttp3.Call.Factory callFactory;
    private final String baseUrl;
    private final List<Converter.Factory> converterFactories;
    private final List<CallAdapter.Factory> adapterFactories;
    private final Executor callbackExecutor;
    private final boolean validateEagerly;
    private Observable<ResponseBody> downObservable;
    private Map<String, Observable<ResponseBody>> downMaps = new HashMap<String, Observable<ResponseBody>>() {
    };
    private Observable.Transformer exceptTransformer = null;
    public static final String TAG = "NetWorker";

    NetWorker(okhttp3.Call.Factory callFactory, String baseUrl, Map<String, String> headers,
              Map<String, String> parameters, BaseApiService apiManager,
              List<Converter.Factory> converterFactories, List<CallAdapter.Factory> adapterFactories,
              Executor callbackExecutor, boolean validateEagerly) {
        this.callFactory = callFactory;
        this.baseUrl = baseUrl;
        this.headers = headers;
        this.parameters = parameters;
        this.apiManager = apiManager;
        this.converterFactories = converterFactories;
        this.adapterFactories = adapterFactories;
        this.callbackExecutor = callbackExecutor;
        this.validateEagerly = validateEagerly;
    }

    /**
     * create ApiService
     */
    public <T> T create(final Class<T> service) {

        return retrofit.create(service);
    }

    /**
     * @param subscriber
     */
    public <T> T call(Observable<T> observable, Subscriber<T> subscriber) {
        return (T) observable.compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    public <T> T call(Observable<T> observable) {
        return (T) observable.compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * Retroift execute get
     * <p>
     * return parsed data
     * <p>
     * you don't need to parse ResponseBody
     */
    public <T> T executeGet(final String url, final Map<String, String> maps) {

        return (T) apiManager.executeGet(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * MethodHandler
     */
    private List<Type> MethodHandler(Type[] types) {
        Log.d(TAG, "types size: " + types.length);
        List<Type> needtypes = new ArrayList<>();
        for (Type paramType : types) {
            if (paramType instanceof ParameterizedType) {
                Type[] parentypes = ((ParameterizedType) paramType).getActualTypeArguments();
                Log.d(TAG, "TypeArgument: ");
                for (Type childtype : parentypes) {
                    Log.d(TAG, "childtype:" + childtype);
                    needtypes.add(childtype);
                    if (childtype instanceof ParameterizedType) {
                        Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                        for (Type type : childtypes) {
                            needtypes.add(type);
                            Log.d(TAG, "type:" + childtype);
                        }
                    }
                }
            }
        }
        return needtypes;
    }

    final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    final Observable.Transformer schedulersTransformerDown = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
    };

    /**
     * @param <T>
     * @return
     */
    public <T> Observable.Transformer<Response<T>, T> handleErrTransformer() {

        if (exceptTransformer != null) return exceptTransformer;

        else return exceptTransformer = new Observable.Transformer() {
            @Override
            public Object call(Object observable) {
                return ((Observable) observable)/*.map(new HandleFuc<T>())*/.onErrorResumeNext(new HttpResponseFunc<T>());
            }
        };
    }

    private static class HttpResponseFunc<T> implements Func1<Throwable, Observable<T>> {
        @Override
        public Observable<T> call(Throwable t) {
            return Observable.error(NetworkException.handleException(t));
        }
    }

    private class HandleFuc<T> implements Func1<Response<T>, T> {
        @Override
        public T call(Response<T> response) {
            if (response == null || (response.getData() == null && response.getResult() == null)) {
                throw new JsonParseException("后端数据不对");
            }
            /*if (!response.isOk()) {
                throw new RuntimeException(response.getCode() + "" + response.getMsg() != null ? response.getMsg() : "");
            }
*/
            return response.getData();
        }
    }

    /**
     * Retroift get
     *
     * @param url
     * @param maps
     * @param subscriber
     * @param <T>
     * @return no parse data
     */
    public <T> T get(String url, Map<String, String> maps, BaseSubscriber<ResponseBody> subscriber) {
        return (T) apiManager.executeGet(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer())
                .subscribe(subscriber);
    }

    /**
     * @param url
     * @param parameters
     */
    public void post(String url, @FieldMap(encoded = true) Map<String, String> parameters) {
        apiManager.executePost(url, parameters)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * @return parsed data
     */
    public <T> T executePost(final String url, final Map<String, String> parameters) {

        return (T) apiManager.executePost(url, parameters)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }


    /**
     * Post by Form
     *
     * @param url
     */
    public void form(String url, @FieldMap(encoded = true) Map<String, Object> fields) {
        apiManager.postForm(url, fields)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }


    /**
     * Retroift execute Post by Form
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> T executeForm(final String url, final @FieldMap(encoded = true) Map<String, Object> fields) {
        return (T) apiManager.postForm(url, fields)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }


    /**
     * http Post by Body
     * you  need to parse ResponseBody
     *
     * @param url
     */
    public void body(String url, Object body) {
        apiManager.executePostBody(url, body)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * http execute Post by body
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> T executeBody(final String url, final Object body) {
        return (T) apiManager.executePostBody(url, body)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }


    /**
     * http Post by json
     * you  need to parse ResponseBody
     *
     * @param url
     * @param jsonStr Json String
     */
    public void json(String url, String jsonStr) {
        apiManager.postJson(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * http execute Post by Json
     *
     * @param url
     * @param jsonStr Json String
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> T executeJson(final String url, final String jsonStr) {
        return (T) apiManager.postJson(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * Execute http by Delete
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> T executeDelete(final String url, final Map<String, String> maps) {
        return (T) apiManager.executeDelete(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * Execute  Http by Put
     *
     * @return parsed data
     * you don't need to parse ResponseBody
     */
    public <T> T executePut(final String url, final Map<String, String> maps) {
        return (T) apiManager.executePut(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }


    /**
     * Test
     *
     * @param url  url
     * @param maps maps
     * @param <T>  T
     * @return
     */
    public <T> T test(String url, Map<String, String> maps) {
        return (T) apiManager.getTest(url, maps)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * upload
     *
     * @param url
     * @param requestBody requestBody
     * @param <T>         T
     * @return
     */
    public <T> T upload(String url, RequestBody requestBody) {
        return (T) apiManager.upLoadImage(url, requestBody)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * uploadImage
     *
     * @param url  url
     * @param file file
     * @param <T>
     * @return
     */
    public <T> T uploadImage(String url, File file) {
        return (T) apiManager.upLoadImage(url, Utils.createImage(file))
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * upload Flie
     *
     * @param url
     * @param requestBody requestBody
     * @param <T>         T
     * @return
     */
    public <T> T uploadFlie(String url, RequestBody requestBody, MultipartBody.Part file) {
        return (T) apiManager.uploadFlie(url, requestBody, file)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }

    /**
     * upload Flies
     *
     * @param url
     * @param <T> T
     * @return
     */
    public <T> T uploadFlies(String url, Map<String, RequestBody> files) {
        return (T) apiManager.uploadFiles(url, files)
                .compose(schedulersTransformer)
                .compose(handleErrTransformer());
    }


    /**
     * @param url
     * @param callBack
     */
    public void download(String url, DownLoadCallBack callBack) {
        download(url, null, callBack);
    }

    /**
     * @param url
     * @param name
     * @param callBack
     */
    public void download(String url, String name, DownLoadCallBack callBack) {
        download(url, null, name, callBack);
    }

    /**
     * downloadMin
     *
     * @param url
     * @param callBack
     */
    public void downloadMin(String url, DownLoadCallBack callBack) {
        downloadMin(url, null, callBack);
    }

    /**
     * downloadMin
     *
     * @param url
     * @param name
     * @param callBack
     */
    public void downloadMin(String url, String name, DownLoadCallBack callBack) {
        downloadMin(url, null, name, callBack);
    }

    /**
     * download small file
     *
     * @param url
     * @param savePath
     * @param name
     * @param callBack
     */
    public void downloadMin(String url, String savePath, String name, DownLoadCallBack callBack) {

        if (downMaps.get(url) == null) {
            downObservable = apiManager.downloadSmallFile(url);
            downMaps.put(url, downObservable);
        } else {
            downObservable = downMaps.get(url);
        }
        executeDownload(savePath, name, callBack);
    }

    /**
     * @param url
     * @param savePath
     * @param name
     * @param callBack
     */
    public void download(String url, String savePath, String name, DownLoadCallBack callBack) {

        if (downMaps.get(url) == null) {
            downObservable = apiManager.downloadFile(url);
            downMaps.put(url, downObservable);
        } else {
            downObservable = downMaps.get(url);
        }
        executeDownload(savePath, name, callBack);
    }

    /**
     * executeDownload
     *
     * @param savePath
     * @param name
     * @param callBack
     */
    private void executeDownload(String savePath, String name, DownLoadCallBack callBack) {
        if (DownLoadManager.isDownLoading) {
            downObservable.unsubscribeOn(Schedulers.io());
            DownLoadManager.isDownLoading = false;
            DownLoadManager.isCancel = true;
            return;
        }
        DownLoadManager.isDownLoading = true;
        downObservable.compose(schedulersTransformerDown)
                .compose(handleErrTransformer())
                .subscribe(new DownSubscriber<ResponseBody>(savePath, name, callBack, mContext));
    }

    /**
     * Mandatory Builder for the Builder
     */
    public static final class Builder {

        private static final int DEFAULT_TIMEOUT = 5;
        private static final int DEFAULT_MAXIDLE_CONNECTIONS = 5;
        private static final long DEFAULT_KEEP_ALIVEDURATION = 8;

        private okhttp3.Call.Factory callFactory;
        private String baseUrl;
        private Boolean isLog = true;
        private Boolean isCookie = false;
        private Boolean isCache = true;
        private List<InputStream> certificateList;
        private HostnameVerifier hostnameVerifier;
        private CertificatePinner certificatePinner;
        private List<Converter.Factory> converterFactories = new ArrayList<>();
        private List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
        private Executor callbackExecutor;
        private boolean validateEagerly;
        private Context context;
        private CookieManger cookieManager;
        private Cache cache = null;
        private Proxy proxy;
        private SSLSocketFactory sslSocketFactory;
        private ConnectionPool connectionPool;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory callAdapterFactory;
        private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR;

        public Builder(Context context) {
            // Add the base url first. This prevents overriding its behavior but also
            // ensures correct behavior when using novate that consume all types.
            okhttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
            this.context = context;

        }

        /**
         * The HTTP client used for requests. default OkHttpClient
         * <p/>
         * This is a convenience method for calling {@link #callFactory}.
         * <p/>
         * Note: This method <b>does not</b> make a defensive copy of {@code client}. Changes to its
         * settings will affect subsequent requests. Pass in a {@linkplain OkHttpClient#clone() cloned}
         * instance to prevent this if desired.
         */
        @NonNull
        public Builder client(OkHttpClient client) {
            retrofitBuilder.client(Utils.checkNotNull(client, "client == null"));
            return this;
        }

        /**
         * Add ApiManager for serialization and deserialization of objects.
         *//*
        public Builder addApiManager(final Class<ApiManager> service) {

            apiManager = retrofit.create((Utils.checkNotNull(service, "apiManager == null")));
            //return retrofit.create(service);
            return this;
        }*/

        /**
         * Specify a custom call factory for creating {@link } instances.
         * <p/>
         * Note: Calling {@link #client} automatically sets this value.
         */
        public Builder callFactory(okhttp3.Call.Factory factory) {
            this.callFactory = Utils.checkNotNull(factory, "factory == null");
            return this;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(int timeout) {
            return connectTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link Integer#MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder writeTimeout(int timeout) {
            return writeTimeout(timeout, TimeUnit.SECONDS);
        }

        /**
         * open default logcat
         *
         * @param isLog
         * @return
         */
        public Builder addLog(boolean isLog) {
            this.isLog = isLog;
            return this;
        }

        /**
         * open sync default Cookie
         *
         * @param isCookie
         * @return
         */
        public Builder addCookie(boolean isCookie) {
            this.isCookie = isCookie;
            return this;
        }

        /**
         * open default Cache
         *
         * @param isCache
         * @return
         */
        public Builder addCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        public Builder proxy(Proxy proxy) {
            okhttpBuilder.proxy(Utils.checkNotNull(proxy, "proxy == null"));
            return this;
        }

        /**
         * Sets the default write timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link TimeUnit #MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder writeTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                okhttpBuilder.writeTimeout(timeout, unit);
            } else {
                okhttpBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }

        /**
         * Sets the connection pool used to recycle HTTP and HTTPS connections.
         * <p>
         * <p>If unset, a new connection pool will be used.
         */
        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) throw new NullPointerException("connectionPool == null");
            this.connectionPool = connectionPool;
            return this;
        }

        /**
         * Sets the default connect timeout for new connections. A value of 0 means no timeout,
         * otherwise values must be between 1 and {@link TimeUnit #MAX_VALUE} when converted to
         * milliseconds.
         */
        public Builder connectTimeout(int timeout, TimeUnit unit) {
            if (timeout != -1) {
                okhttpBuilder.connectTimeout(timeout, unit);
            } else {
                okhttpBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            }
            return this;
        }


        /**
         * Set an API base URL which can change over time.
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = Utils.checkNotNull(baseUrl, "baseUrl == null");
            return this;
        }

        /**
         * Add converter factory for serialization and deserialization of objects.
         */
        public Builder addConverterFactory(Converter.Factory factory) {
            this.converterFactory = factory;
            return this;
        }

        /**
         * Add a call adapter factory for supporting service method return types other than {@link CallAdapter
         * }.
         */
        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            this.callAdapterFactory = factory;
            return this;
        }

        /**
         * Add Header for serialization and deserialization of objects.
         */
        public Builder addHeader(Map<String, String> headers) {
            okhttpBuilder.addInterceptor(new BaseInterceptor((Utils.checkNotNull(headers, "header == null"))));
            return this;
        }

        /**
         * Add parameters for serialization and deserialization of objects.
         */
        public Builder addParameters(Map<String, String> parameters) {
            okhttpBuilder.addInterceptor(new BaseInterceptor((Utils.checkNotNull(parameters, "parameters == null"))));
            return this;
        }

        /**
         * Returns a modifiable list of interceptors that observe a single network request and response.
         * These interceptors must call {@link Interceptor.Chain#proceed} exactly once: it is an error
         * for a network interceptor to short-circuit or repeat a network request.
         */
        public Builder addInterceptor(Interceptor interceptor) {
            okhttpBuilder.addInterceptor(Utils.checkNotNull(interceptor, "interceptor == null"));
            return this;
        }

        /**
         * The executor on which {@link Call} methods are invoked when returning {@link Call} from
         * your service method.
         * <p/>
         * Note: {@code executor} is not used for {@linkplain #addCallAdapterFactory custom method
         * return types}.
         */
        public Builder callbackExecutor(Executor executor) {
            this.callbackExecutor = Utils.checkNotNull(executor, "executor == null");
            return this;
        }

        /**
         * When calling {@link #create} on the resulting {@link Retrofit} instance, eagerly validate
         * the configuration of all methods in the supplied interface.
         */
        public Builder validateEagerly(boolean validateEagerly) {
            this.validateEagerly = validateEagerly;
            return this;
        }

        /**
         * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
         * outgoing HTTP requests.
         * <p/>
         * <p>If unset, {@linkplain CookieManger#NO_COOKIES no cookies} will be accepted nor provided.
         */
        public Builder cookieManager(CookieManger cookie) {
            if (cookie == null) throw new NullPointerException("cookieManager == null");
            this.cookieManager = cookie;
            return this;
        }

        /**
         *
         */
        public Builder addSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
            return this;
        }

        public Builder addHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder addCertificatePinner(CertificatePinner certificatePinner) {
            this.certificatePinner = certificatePinner;
            return this;
        }


        /**
         * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
         * outgoing HTTP requests.
         * <p/>
         * <p>If unset, {@linkplain CookieManger#NO_COOKIES no cookies} will be accepted nor provided.
         */
        public Builder addSSL(String[] hosts, int[] certificates) {
            if (hosts == null) throw new NullPointerException("hosts == null");
            if (certificates == null) throw new NullPointerException("ids == null");


            addSSLSocketFactory(HttpsFactroy.getSSLSocketFactory(context, certificates));
            addHostnameVerifier(HttpsFactroy.getHostnameVerifier(hosts));
            return this;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            okhttpBuilder.addNetworkInterceptor(interceptor);
            return this;
        }

        /**
         * setCache
         *
         * @param cache cahe
         * @return Builder
         */
        public Builder addCache(Cache cache) {
            int maxStale = 60 * 60 * 24 * 3;
            return addCache(cache, maxStale);
        }

        /**
         * @param cache
         * @param cacheTime ms
         * @return
         */
        public Builder addCache(Cache cache, final int cacheTime) {
            addCache(cache, String.format("max-age=%d", cacheTime));
            return this;
        }

        /**
         * @param cache
         * @param cacheControlValue Cache-Control
         * @return
         */
        private Builder addCache(Cache cache, final String cacheControlValue) {
            REWRITE_CACHE_CONTROL_INTERCEPTOR = new CacheInterceptor(mContext, cacheControlValue);
            addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
            this.cache = cache;
            return this;
        }

        /**
         * Create the {@link Retrofit} instance using the configured values.
         * <p/>
         * Note: If neither {@link #client} nor {@link #callFactory} is called a default {@link
         * OkHttpClient} will be created and used.
         */
        public NetWorker build() {
            if (baseUrl == null) {
                throw new IllegalStateException("Base URL required.");
            }

            if (okhttpBuilder == null) {
                throw new IllegalStateException("okhttpBuilder required.");
            }

            if (retrofitBuilder == null) {
                throw new IllegalStateException("retrofitBuilder required.");
            }
            /** set Context. */
            mContext = context;
            /**
             * Set a fixed API base URL.
             *
             * @see #baseUrl(HttpUrl)
             */
            retrofitBuilder.baseUrl(baseUrl);

            /** Add converter factory for serialization and deserialization of objects. */
            if (converterFactory == null) {
                converterFactory = GsonConverterFactory.create();
            }
            retrofitBuilder.addConverterFactory(converterFactory);
            if (callAdapterFactory == null) {
                callAdapterFactory = RxJavaCallAdapterFactory.create();
            }
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory);
            if (isLog) {
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            if (sslSocketFactory != null) {
                okhttpBuilder.sslSocketFactory(sslSocketFactory);
            }
            if (hostnameVerifier != null) {
                okhttpBuilder.hostnameVerifier(hostnameVerifier);
            }
            if (isCache) {
                try {
                    if (cache == null) {
                        cache = HttpCache.getCache();
                    }

                    addCache(cache);

                } catch (Exception e) {
                    Log.e("OKHttp", "Could not create http cache", e);
                }
                if (cache == null) {
                    cache = HttpCache.getCache();
                }
            }

            if (cache != null) {
                okhttpBuilder.cache(cache);
            }

            /**
             * Sets the connection pool used to recycle HTTP and HTTPS connections.
             *
             * <p>If unset, a new connection pool will be used.
             */
            if (connectionPool == null) {

                connectionPool = new ConnectionPool(DEFAULT_MAXIDLE_CONNECTIONS, DEFAULT_KEEP_ALIVEDURATION, TimeUnit.SECONDS);
            }
            okhttpBuilder.connectionPool(connectionPool);

            /**
             * Sets the HTTP proxy that will be used by connections created by this client. This takes
             * precedence over {@link #proxySelector}, which is only honored when this proxy is null (which
             * it is by default). To disable proxy use completely, call {@code setProxy(Proxy.NO_PROXY)}.
             */
            if (proxy == null) {
                okhttpBuilder.proxy(proxy);
            }

            /**
             * Sets the handler that can accept cookies from incoming HTTP responses and provides cookies to
             * outgoing HTTP requests.
             *
             * <p>If unset, {@link NetWorker CookieManager#NO_COOKIES no cookies} will be accepted nor provided.
             */
            if (isCookie && cookieManager == null) {
                okhttpBuilder.cookieJar(new CookieManger(context));
            }

            if (cookieManager != null) {
                okhttpBuilder.cookieJar(cookieManager);
            }

            /**
             *okhttp3.Call.Factory callFactory = this.callFactory;
             */
            if (callFactory != null) {
                retrofitBuilder.callFactory(callFactory);
            }
            /**
             * create okHttpClient
             */
            okHttpClient = okhttpBuilder.build();
            /**
             * set Retrofit client
             */

            retrofitBuilder.client(okHttpClient);

            /**
             * create Retrofit
             */
            retrofit = retrofitBuilder.build();
            /**
             *create BaseApiService;
             */
            apiManager = retrofit.create(BaseApiService.class);

            return new NetWorker(callFactory, baseUrl, headers, parameters, apiManager, converterFactories, adapterFactories,
                    callbackExecutor, validateEagerly);
        }
    }


}


