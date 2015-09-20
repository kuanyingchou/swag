package swag.swag;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

public interface SwagService {
    String baseUrl = "http://10cf8c3d.ngrok.io/";

    //svg: “texthere"
    ///api/v1/drawings with optional user_id
    //@GET("/api/v1/drawings/")

    @POST("api/v1/drawings")
    Call<Drawing> addDrawing(@Body String drawingJson);

    @GET("api/v1/drawings")
    Call<List<Drawing>> listDrawings();

}
