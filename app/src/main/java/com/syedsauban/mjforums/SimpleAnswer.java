package com.syedsauban.mjforums;

/**
 * Created by Syed on 13-11-2017.
 */

public class SimpleAnswer {
    String question,answer;
    SimpleAnswer(String question,String answer)
    {
        this.question=question;
        this.answer=answer;
    }
    public SimpleAnswer()
    {}

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
