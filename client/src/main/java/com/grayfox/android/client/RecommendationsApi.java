package com.grayfox.android.client;

import android.content.Context;
import android.net.Uri;

import com.grayfox.android.client.model.Location;
import com.grayfox.android.client.model.Recommendation;

public class RecommendationsApi extends BaseApi {

    public RecommendationsApi(Context context) {
        super(context);
    }

    public Recommendation[] awaitRecommendationsByAll(String accessToken, Location location, Integer radius) {
        String url = Uri.parse(getString(R.string.gf_api_base_url)).buildUpon()
                .appendEncodedPath(getString(R.string.gf_api_recommendations_path))
                .appendEncodedPath(getString(R.string.gf_api_recommendations_by_all_path))
                .appendQueryParameter("access-token", accessToken)
                .appendQueryParameter("location", location != null ? location.stringValues() : null)
                .appendQueryParameter("radius", radius != null ? radius.toString() : null)
                .build().toString();

        return get(url, Recommendation[].class);
    }

    public Recommendation[] awaitRecommendationsByLikes(String accessToken, Location location, Integer radius) {
        String url = Uri.parse(getString(R.string.gf_api_base_url)).buildUpon()
                .appendEncodedPath(getString(R.string.gf_api_recommendations_path))
                .appendEncodedPath(getString(R.string.gf_api_recommendations_by_likes_path))
                .appendQueryParameter("access-token", accessToken)
                .appendQueryParameter("location", location != null ? location.stringValues() : null)
                .appendQueryParameter("radius", radius != null ? radius.toString() : null)
                .build().toString();

        return get(url, Recommendation[].class);
    }

    public Recommendation[] awaitRecommendationsByFriendsLikes(String accessToken, Location location, Integer radius) {
        String url = Uri.parse(getString(R.string.gf_api_base_url)).buildUpon()
                .appendEncodedPath(getString(R.string.gf_api_recommendations_path))
                .appendEncodedPath(getString(R.string.gf_api_recommendations_by_friends_likes_path))
                .appendQueryParameter("access-token", accessToken)
                .appendQueryParameter("location", location != null ? location.stringValues() : null)
                .appendQueryParameter("radius", radius != null ? radius.toString() : null)
                .build().toString();

        return get(url, Recommendation[].class);
    }
}