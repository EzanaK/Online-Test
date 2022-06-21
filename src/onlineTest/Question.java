package onlineTest;

import java.io.Serializable;

public class Question implements Serializable {
	
	protected String questionType;
	protected String questionText;
	protected double points;
	protected boolean booleanAnswer;
	protected String[] stringAnswer;
	
	public Question(String text, double points, boolean answer, String type) {
		this.questionType = type;
		this.questionText = text;
		this.points = points;
		this.booleanAnswer = answer;
	}
	
	public Question(String text, double points, String[] answer, String type) {
		this.questionType = type;
		this.questionText = text;
		this.points = points;
		this.stringAnswer = answer;
	}
	
}
