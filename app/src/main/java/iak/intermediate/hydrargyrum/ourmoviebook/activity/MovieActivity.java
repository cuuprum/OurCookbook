package iak.intermediate.hydrargyrum.ourmoviebook.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import iak.intermediate.hydrargyrum.ourmoviebook.model.AppVar;
import iak.intermediate.hydrargyrum.ourmoviebook.model.BaseActivity;
import iak.intermediate.hydrargyrum.ourmoviebook.R;
import iak.intermediate.hydrargyrum.ourmoviebook.adapter.ListAdapter;
import iak.intermediate.hydrargyrum.ourmoviebook.adapter.viewholders.MovieViewHolder;
import iak.intermediate.hydrargyrum.ourmoviebook.app.AppMovie;
import iak.intermediate.hydrargyrum.ourmoviebook.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieActivity extends BaseActivity {

    private static final String TAG = MovieActivity.class.getSimpleName();
    AlertDialogManager alert = new AlertDialogManager();
    private TabLayout tabLayoutMovie;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private ArrayList<Movies> movieList = new ArrayList<>();
    String selectedTab = "popular";
    private FloatingActionButton fabNext;
    private FloatingActionButton fabPrev;
    int pageOnline;
    int pageOffline;
    int pageFav;
    boolean isSelectPopTab;
    private Parcelable recyclerViewStartPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("OUR MOVIE BOOK");

        isSelectPopTab = true;
        initTabLayout();
        tabLayoutMovie.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0) {
                    pageOffline = 1;
                    pageOnline = 1;
                    isSelectPopTab = true;
                }else {
                    pageFav = 1;
                    isSelectPopTab = false;
                }
                connecting();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pageFav = 1;
        pageOffline = 1;
        pageOnline = 1;
        fabNext = (FloatingActionButton) findViewById(R.id.fabNext);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectPopTab) {
                    pageOnline += 1;
                    pageOffline += 1;
                }
                else{
                    pageFav += 1;
                }
                connecting();
            }
        });

        fabPrev = (FloatingActionButton) findViewById(R.id.fabPrev);
        fabPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSelectPopTab) {
                    pageOnline -= 1;
                    pageOffline -= 1;
                }
                else{
                    pageFav -= 1;
                }

                if(pageOnline < 1 || pageFav < 1 || pageOffline < 1) {
                    Toast.makeText(MovieActivity.this, "Already First Page", Toast.LENGTH_SHORT).show();
                    pageOnline = 1;
                    pageFav = 1;
                    pageOffline = 1;
                }

                connecting();
            }
        });
        initRecycler();
        initAdapterMovies();

        showDialog("Loading...");
        connecting();
    }

    private void connecting() {
        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewStartPos);
        if (isInternetConnectionAvailable()) {
            if(isSelectPopTab)
                getData(AppVar.URL_MOVIE_LATEST + pageOnline);
            else
                listAdapter.swapData(getDB().getAllListRated(pageFav));
                //getData(AppVar.URL_MOVIE_LATEST);
        } else {
            Toast.makeText(this, "No Connection", Toast.LENGTH_SHORT).show();
            listAdapter.swapData(getDB().getAllListMovies(pageOffline));
            hideDialog();
        }
    }

    private void initTabLayout(){
        tabLayoutMovie = (TabLayout) findViewById(R.id.tabLayoutMovie);
        tabLayoutMovie.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayoutMovie.addTab(tabLayoutMovie.newTab().setText("Popular Movie"));
        tabLayoutMovie.addTab(tabLayoutMovie.newTab().setText("My Favorite"));
    }
    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerViewStartPos = recyclerView.getLayoutManager().onSaveInstanceState(); // Save state/posisi recycler view pas di awal
    }
    private void initAdapterMovies() {
        listAdapter = new ListAdapter<Movies
                , MovieViewHolder>
                (R.layout.item_list_movie
                        , MovieViewHolder.class
                        , Movies.class
                        , movieList) {
            @Override
            protected void bindView(MovieViewHolder _holder, final Movies _model, int _position) {
                Picasso.with(getApplicationContext())
                        .load(AppVar.BASE_IMAGE+_model.getPoster_path())
                        .into(_holder.gambar_movie);
                //Log.d(TAG, "URL : " + AppVar.BASE_IMAGE+_model.getPoster_path());
                _holder.tvTitle.setText(_model.getTitle() + " (" + _model.getRelease_date().substring(0,4) + ")");
                Log.d("Reading: ", "Reading all movies..");

                _holder.getItem().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(MovieActivity.this,DetailMovieActivity.class);
                        in.putExtra("movie",_model);
                        startActivity(in);
                    }
                });
            }
        };
        recyclerView.setAdapter(listAdapter);
    }
    public void getData(String url) {
        StringRequest request = new StringRequest(Request.Method.GET
                , url
                , new Response.Listener<String>() { //response api
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject parent = new JSONObject(response);
                    JSONArray results = parent.getJSONArray("results");
                    ArrayList<Movies> onlineMovieList = new ArrayList<>();

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject sourceParam = results.getJSONObject(i);
                        Movies datajson = new Movies();
                        datajson.setId(sourceParam.getInt("id"));
                        datajson.setOriginal_language(sourceParam.getString("original_language"));
                        datajson.setTitle(sourceParam.getString("title"));
                        datajson.setOverview(sourceParam.getString("overview"));
                        datajson.setPopularity(sourceParam.getDouble("popularity"));
                        datajson.setPoster_path(sourceParam.getString("poster_path"));
                        datajson.setRelease_date(sourceParam.getString("release_date"));
                        datajson.setVote_average(sourceParam.getDouble("vote_average"));
                        datajson.setVote_count(sourceParam.getInt("vote_count"));
                        if (getDB().isMovieExist(datajson.getId())) {
                            getDB().addMovie(datajson);
                        }
                        onlineMovieList.add(datajson);
                    }

                    listAdapter.swapData(onlineMovieList);
                    hideDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // error response
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                hideDialog();

            }
        });
        AppMovie.getInstance().addToRequestQueue(request, TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MovieActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.exitapp)
            exit();
        else{
            Intent inAbout = new Intent(MovieActivity.this, AboutActivity.class);
            startActivity(inAbout);
        }

        return super.onOptionsItemSelected(item);
    }

    private void exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Exit Movie App?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        alert.showAlertDialog(MovieActivity.this, "Closing program....", "Please Wait...", false);
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        finish();
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
