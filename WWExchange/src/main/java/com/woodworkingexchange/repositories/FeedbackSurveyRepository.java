package com.woodworkingexchange.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.woodworkingexchange.domain.FeedbackSurvey;

public interface FeedbackSurveyRepository extends JpaRepository<FeedbackSurvey, Long>
{

}
