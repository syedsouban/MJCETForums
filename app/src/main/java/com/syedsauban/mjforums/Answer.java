package com.syedsauban.mjforums;

import java.util.ArrayList;

/**
 * Created by Syed on 25-10-2017.
 */

public class Answer {
    private String AnswerString,AnswerWrittenBy;
            long timestamp;
    private ArrayList<String> tags;
    private String deptAndYear;

    private String UserKey,UserId,QuestionString,QuestionDetailsString,NameOfAsker;
    public Answer(long timestamp,String QuestionString, String QuestionDetailsString,String NameOfAsker, String AnswerString, String AnswerWrittenBy, String UserKey, String UserId, String deptAndYear, ArrayList<String> tags)
    {
        this.timestamp=timestamp;
        this.NameOfAsker=NameOfAsker;
        this.deptAndYear=deptAndYear;
        this.tags=tags;
        this.QuestionString=QuestionString;
        this.QuestionDetailsString=QuestionDetailsString;
        this.UserId=UserId;
        this.UserKey=UserKey;
        this.AnswerString=AnswerString;
        this.AnswerWrittenBy=AnswerWrittenBy;
    }
    public Answer(long timestamp,String QuestionString, String QuestionDetailsString,String NameOfAsker,ArrayList<String> tags)
    {
        this.timestamp=timestamp;
        this.NameOfAsker=NameOfAsker;
        this.QuestionString=QuestionString;
        this.QuestionDetailsString=QuestionDetailsString;
        this.tags=tags;
    }

    public Answer()
    {

    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getNameOfAsker() {
        return NameOfAsker;
    }

    public void setNameOfAsker(String nameOfAsker) {
        NameOfAsker = nameOfAsker;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public String getDeptAndYear() {
        return deptAndYear;
    }

    public void setDeptAndYear(String deptAndYear) {
        this.deptAndYear = deptAndYear;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getQuestionDetailsString() {
        return QuestionDetailsString;
    }

    public String getQuestionString() {
        return QuestionString;
    }

    public void setQuestionDetailsString(String questionDetailsString) {
        QuestionDetailsString = questionDetailsString;
    }

    public void setQuestionString(String questionString) {
        QuestionString = questionString;
    }

    public String getUserKey() {
        return UserKey;
    }

    public String getUserId() {
        return UserId;
    }


    public String getAnswerString() {
        return AnswerString;
    }

    public String getAnswerWrittenBy() {
        return AnswerWrittenBy;
    }

    public void setUserKey(String userKey) {
        UserKey = userKey;
    }

    public void setAnswerString(String answerString) {
        AnswerString = answerString;
    }

    public void setAnswerWrittenBy(String answerWrittenBy) {
        AnswerWrittenBy = answerWrittenBy;
    }


    public void setUserId(String userId) {
        UserId = userId;
    }
}

