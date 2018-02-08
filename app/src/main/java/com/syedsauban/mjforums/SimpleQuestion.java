package com.syedsauban.mjforums;

/**
 * Created by Syed on 13-11-2017.
 */

public class SimpleQuestion {
    private String question,questionDetails;
    public SimpleQuestion(String question,String questionDetails)
    {
        this.question=question;
        this.questionDetails=questionDetails;
    }
    public SimpleQuestion()
    {

    }

    public String getQuestion() {
        return question;
    }

    public String getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestionDetails(String questionDetails) {
        this.questionDetails = questionDetails;
    }
}
