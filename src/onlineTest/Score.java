package onlineTest;

import java.io.Serializable;

public class Score implements Serializable {
	
	protected int questionNumber;
	protected double earnedPoints;
	protected double totalPoints;
	
	public Score(int questionNumber, double earnedPoints, double points) {
		this.questionNumber = questionNumber;
		this.earnedPoints = earnedPoints;
		this.totalPoints = points;
	}
	
}
