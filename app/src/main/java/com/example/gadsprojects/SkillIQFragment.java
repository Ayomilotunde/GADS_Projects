package com.example.gadsprojects;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SkillIQFragment extends Fragment {

    private RecyclerView recyclerView;

    LearnersAdapter learnersAdapter;
    ViewGroup root;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



       /* recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
*/
        getAllUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        root = (ViewGroup) inflater.inflate(R.layout.fragment_skill_i_q, null);
        return root;






    }

    private void generateDataList(List<Learners> learnerslist) {
        recyclerView = root.findViewById(R.id.recyclerview);
        learnersAdapter = new LearnersAdapter(getContext(), learnerslist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(learnersAdapter);
    }

    public void getAllUsers(){

        LearnersService service = ApiClient.getRetrofitInstance().create(LearnersService.class);
        Call<List<Learners>> call = service.getTopSkillIQLearners();


        call.enqueue(new Callback<List<Learners>>() {
            @Override
            public void onResponse(@NotNull Call<List<Learners>> call, @NotNull Response<List<Learners>> response) {


                if(response.body() != null) {
                    generateDataList(response.body());
                }



            }

            @Override
            public void onFailure(Call<List<Learners>> call, Throwable t) {
                Log.e("failure", Objects.requireNonNull(t.getLocalizedMessage()));

            }
        });
    }
}