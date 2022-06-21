package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Grade implements Serializable {
	
	protected Map<Integer, Score> scoreMap;
	protected double earnedExamPoints;
	protected double totalExamPoints;
	
	public Grade() {
		scoreMap = new HashMap<>();
		earnedExamPoints = 0;
		totalExamPoints = 0;
	}
	
	public void answerTrueFalseQuestion(int questionNumber, boolean answer, Map<Integer, Question> questionMap) {
		Question question = questionMap.get(questionNumber);
		double earnedPoints;
		if (question.booleanAnswer == answer) {
			earnedPoints = question.points;
		} else {
			earnedPoints = 0;
		}
		earnedExamPoints += earnedPoints;
		totalExamPoints += question.points;
		scoreMap.put(questionNumber, new Score(questionNumber, earnedPoints, question.points));
	}
	
	public void answerMultipleChoiceQuestion(int questionNumber, String[] answer, Map<Integer, Question> questionMap) {
		Question question = questionMap.get(questionNumber);
		double earnedPoints = 0;
		int i,j;
		for (i = 0; i < answer.length; i++) {
			for (j = 0; j < question.stringAnswer.length; j++) {
				if (question.stringAnswer[j].equals(answer[i])) {
					earnedPoints += question.points / question.stringAnswer.length;
					break;
				}
				if (j == question.stringAnswer.length - 1) {
					earnedPoints -= question.points / question.stringAnswer.length;
				}
			}
		}
		if (answer.length < question.stringAnswer.length) {
			int difference = question.stringAnswer.length - answer.length;
			earnedPoints -= difference * (question.points / question.stringAnswer.length);
		}
		if (earnedPoints < 0) {
			earnedExamPoints += 0;
			totalExamPoints += question.points;
			scoreMap.put(questionNumber, new Score(questionNumber, 0, question.points));
		} else {
			earnedExamPoints += earnedPoints;
			totalExamPoints += question.points;
			scoreMap.put(questionNumber, new Score(questionNumber, earnedPoints, question.points));
		}
	}
	
	public void answerFillInTheBlankQuestion(int questionNumber, String[] answer, Map<Integer, Question> questionMap) {
		Question question = questionMap.get(questionNumber);
		double earnedPoints = 0;
		int i,j;
		for (i = 0; i < answer.length; i++) {
			for (j = 0; j < question.stringAnswer.length; j++) {
				if(question.stringAnswer[j].equals(answer[i])) {
					earnedPoints += question.points / question.stringAnswer.length;
					break;
				}
			}
		}	
		earnedExamPoints += earnedPoints;
		totalExamPoints += question.points;
		scoreMap.put(questionNumber, new Score(questionNumber, earnedPoints, question.points));
	}
	
	public String getGradingReport() {
		StringBuffer strB = new StringBuffer();
		for (Map.Entry<Integer, Score> entry : scoreMap.entrySet()) {
			strB.append("Question #" + entry.getKey() + " " + entry.getValue().earnedPoints + " points out of " + entry.getValue().totalPoints + "\n");
		}
		strB.append("Final Score: " + earnedExamPoints + " out of " + totalExamPoints);
		return strB.toString();
	}
	
}
