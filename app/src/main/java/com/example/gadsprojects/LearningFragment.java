package com.example.gadsprojects;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LearningFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    ProgressDialog progressDialog;
    private RecyclerView recyclerView;

    LearnersAdapter learnersAdapter;
    ViewGroup root;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

//        mSwipeRefreshLayout.setOnRefreshListener((SwipeRefreshLayout.OnRefreshListener) LearningFragment.this);



        getAllUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.fragment_learning, null);

        mSwipeRefreshLayout = root.findViewById(R.id.swiperefresh_items);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getAllUsers();

                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if(mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });
        return root;
    }
    private void generateDataList(List<Learners> learnerslist) {
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerview);
        learnersAdapter = new LearnersAdapter(getContext(), learnerslist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(learnersAdapter);
    }

    public void getAllUsers() {
        LearnersService service = ApiClient.getRetrofitInstance().create(LearnersService.class);
        Call<List<Learners>> call = service.getTopHoursLearners();

        call.enqueue(new Callback<List<Learners>>() {
            @Override
            public void onResponse(@NotNull Call<List<Learners>> call, @NotNull Response<List<Learners>> response) {
                if (response.body() != null) {
                    progressDialog.dismiss();
                    generateDataList(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Learners>> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("failure", Objects.requireNonNull(t.getLocalizedMessage()));

            }
        });
    }
}