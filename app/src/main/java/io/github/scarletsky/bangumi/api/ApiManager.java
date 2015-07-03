package io.github.scarletsky.bangumi.api;

import retrofit.RestAdapter;

/**
 * In fact, you'd better ask @Sai for the Bangumi API document.
 */
public class ApiManager {

    private static RestAdapter retrofit;
    private static BangumiApi mBangumiApi;

    private static RestAdapter getRetrofit() {
        if (retrofit == null) {
            retrofit = new RestAdapter.Builder()
                    .setEndpoint(BangumiApi.API_HOST)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
        }
        return retrofit;
    }

    private static void initBangumiApi() {
        if (mBangumiApi == null) {
            mBangumiApi = getRetrofit().create(BangumiApi.class);
        }
    }

    public static BangumiApi getBangumiApi() {
        initBangumiApi();
        return mBangumiApi;
    }

}
