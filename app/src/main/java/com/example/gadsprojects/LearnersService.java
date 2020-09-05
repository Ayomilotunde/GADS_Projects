package com.example.gadsprojects;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LearnersService {

    @GET("/api/skilliq")
    Call<List<Learners>> getTopSkillIQLearners();

    @GET("/api/hours")
    Call<List<Learners>> getTopHoursLearners();

}

