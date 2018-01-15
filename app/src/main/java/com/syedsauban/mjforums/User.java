package com.syedsauban.mjforums;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Syed on 22-10-2017.
 */

@IgnoreExtraProperties
public class User {
    public String name,email,deptName,year;
    ArrayList<Question> QuestionsAsked;
    ArrayList<Answer> AnswersWritten;
    public User(String name,String email)
    {
        this.name=name;
        this.email=email;

    }

    public String getDeptName() {
        return deptName;
    }

    public String getYear() {
        return year;
    }

    public void addQuestion(Question question)
    {
        QuestionsAsked.add(question);
    }

    public void addAnswer(Answer answer)
    {
        AnswersWritten.add(answer);
    }

    public ArrayList<Answer> getAnswersWritten() {
        return AnswersWritten;
    }

    public ArrayList<Question> getQuestionsAsked() {
        return QuestionsAsked;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public User()
    {

    }
}
