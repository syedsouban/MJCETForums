package com.syedsauban.mjforums;

import java.util.ArrayList;

/**
 * Created by Syed on 25-10-2017.
 */

public class Question {
    private String QuestionString,QuestionAskedByEmail,QuestionDetails,NameOfAsker,UserKey;
    private ArrayList<String> Tags,Followers;
    long timestamp;
    boolean hasAnswers;
    int numberOfAnswers;

    public Question()
    {

    }


    Question(String QuestionString, String QuestionAskedByEmail,String NameOfAsker,String QuestionDetails,ArrayList<String> Tags,long timestamp,String UserKey)
    {
        this.UserKey=UserKey;
        hasAnswers=false;
        this.QuestionAskedByEmail=QuestionAskedByEmail;
        this.QuestionDetails=QuestionDetails;
        this.QuestionString=QuestionString;
        this.Tags=Tags;
        this.NameOfAsker=NameOfAsker;
        this.timestamp=timestamp;
    }

    public boolean isHasAnswers()
    {
        return hasAnswers;
    }

    public void setHasAnswers(boolean hasAnswers) {
        this.hasAnswers = hasAnswers;
    }

    public String getUserKey() {
        return UserKey;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getNameOfAsker() {
        return NameOfAsker;
    }

    public ArrayList<String> getTags() {
        return Tags;
    }

    public String getQuestionAskedByEmail() {
        return QuestionAskedByEmail;
    }

    public String getQuestionString() {
        return QuestionString;
    }

    public String getQuestionDetails() {
        return QuestionDetails;
    }

    public void setNameOfAsker(String nameOfAsker) {
        NameOfAsker = nameOfAsker;
    }

    public void setQuestionAskedByEmail(String questionAskedByEmail) {
        QuestionAskedByEmail = questionAskedByEmail;
    }

    public void setQuestionDetails(String questionDetails) {
        QuestionDetails = questionDetails;
    }

    public void setQuestionString(String questionString) {
        QuestionString = questionString;
    }

    public void setTags(ArrayList<String> tags) {
        Tags = tags;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
