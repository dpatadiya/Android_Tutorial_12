package com.example.android_tutorial_12;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import static com.android.volley.toolbox.Volley.newRequestQueue;
import org.json.JSONArray;

public class MainActivity extends AppCompatActivity {

    // RecyclerView .......
    RecyclerView rcvUsers;
    RequestQueue requestQueue;
    JsonArrayRequest jsonArrayRequest;
    Volley volley;
    LinearLayoutManager layoutManager;

    UserAdapter userAdapter;
    DividerItemDecoration dividerItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcvUsers=findViewById(R.id.rcvUsers);
        // use this setting to Improve performance if u know that changes
        // in content do not change the layout size of the RecyclerView
        rcvUsers.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager=new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(layoutManager);

     // Add Divider
        DividerItemDecoration dividerItemDecoration=
                new DividerItemDecoration(rcvUsers.getContext(), LinearLayoutManager.VERTICAL);
                rcvUsers.addItemDecoration(dividerItemDecoration);

       // Apply Animation
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation =
                AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        rcvUsers.setLayoutAnimation(animation);


        volleyNetworkAPI();
    }

    private void volleyNetworkAPI() {
        requestQueue=volley.newRequestQueue(getApplicationContext());
        jsonArrayRequest=new JsonArrayRequest(
                Request.Method.GET,
                MyUtil.URL_USERS,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        MyUtil.userData=response;
                        userAdapter=new UserAdapter(response);
                        rcvUsers.setAdapter(userAdapter);
                        userAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
       requestQueue.add(jsonArrayRequest);
    }
}