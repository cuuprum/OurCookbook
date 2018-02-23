package iak.intermediate.hydrargyrum.ourcookbook.model;

/**
 * Created by hydrargyrum on 2/21/2018.
 * Kelas ini menyimpan URL EndPoint dan Variable Global
 */

public class AppVar {

    public static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String BASE_IMAGE = "http://image.tmdb.org/t/p/w185";
    public static final String id = "id";
    public static final String title = "title";
    public static String API_KEY = "a6a32ab0ed2d1765bbcc21c938bdc5ae";
    //public static String key = "";
    public static final String URL_MOVIE_POPULAR = BASE_URL + "popular?api_key=" + API_KEY; // #TODO Ganti jadi URL_MOVIE_POPULAR

    String TAG_JSON_ARRAY = "results";

    public static final String tag_iso_639_1="iso_639_1";
    public static final String tag_iso_3166_1="iso_3166_1";
    public static final String tag_key="key";
    public static final String tag_name="name";
    public static final String tag_site="site";
    public static final String tag_size="size";
    public static final String tag_type="type";
}
