package io.github.scarletsky.bangumi.api;

import java.util.List;

import io.github.scarletsky.bangumi.api.models.Calendar;
import io.github.scarletsky.bangumi.api.models.Collection;
import io.github.scarletsky.bangumi.api.models.Subject;
import io.github.scarletsky.bangumi.api.models.SubjectEp;
import io.github.scarletsky.bangumi.api.models.UserCollection;
import io.github.scarletsky.bangumi.api.responses.SubjectProgressResponse;
import io.github.scarletsky.bangumi.api.models.User;
import io.github.scarletsky.bangumi.api.responses.BaseResponse;
import io.github.scarletsky.bangumi.api.responses.SearchResponse;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by scarlex on 15-7-3.
 */
public interface BangumiApi {

    String API_HOST = "http://api.bgm.tv";
    String URL_RAKUEN = "http://bangumi.tv/m";
    String SOURCE = "onAir";

    @GET("/calendar")
    void listCalendar(Callback<List<Calendar>> cb);

    @GET("/subject/{subjectId}")
    void getSubject(@Path("subjectId") int subjectId, Callback<Subject> cb);

    @GET("/subject/{subjectId}?responseGroup=large")
    void getSubjectLarge(@Path("subjectId") int subjectId, Callback<SubjectEp> cb);

    @FormUrlEncoded
    @POST("/auth?source=" + SOURCE)
    void auth(@Field("username") String username, @Field("password") String password, Callback<User> cb);

    @GET("/user/{userId}/collection?cat=watching")
    void getUserCollection(@Path("userId") int userId, Callback<List<UserCollection>> cb);

    @GET("/user/{userId}/progress?source=" + SOURCE)
    void getSubjectProgress(@Path("userId") int userId, @Query("auth") String auth, @Query("subject_id") int subjectId, Callback<SubjectProgressResponse> cb);

    @FormUrlEncoded
    @POST("/ep/{epId}/status/{status}?source=" + SOURCE)
    void updateEp(@Path("epId") int epId, @Path("status") String status, @Field("auth") String auth, Callback<BaseResponse> cb);

    @GET("/search/subject/{query}")
    void search(@Path("query") String query, Callback<SearchResponse> cb);

    @GET("/collection/{subjectId}?source=" + SOURCE)
    void getSubjectCollection(@Path("subjectId") int subjectId, @Query("auth") String auth, Callback<Collection> cb);

    @FormUrlEncoded
    @POST("/collection/{subjectId}/update?source=" + SOURCE)
    void updateCollection(
            @Path("subjectId") int subjectId,
            @Field("status") String status,
            @Field("rating") int rating,
            @Field("comment") String comment,
            @Query("auth") String auth,
            Callback<Collection> cb);
}
