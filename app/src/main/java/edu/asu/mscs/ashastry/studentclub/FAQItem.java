package edu.asu.mscs.ashastry.studentclub;

/**
 * Created by A on 4/30/2015.
 */
public class FAQItem {
    private String question;
    private String answer;
    int id;

    public FAQItem(String question, String answer, int id) {
        this.question = question;
        this.answer = answer;
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
