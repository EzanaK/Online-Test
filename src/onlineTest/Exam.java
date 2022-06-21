package onlineTest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Exam implements Serializable {
	
	protected String examTitle;
	protected Map<Integer, Question> questionMap;
	
	public Exam(String title) {
		this.examTitle = title;
		questionMap = new HashMap<>(); 
	}
	
	public void addTrueFalseQuestion(int questionNumber, String text, double points, boolean answer) {
		questionMap.put(questionNumber, new Question(text, points, answer, "TorF"));
	}

	public void addMultipleChoiceQuestion(int questionNumber, String text, double points, String[] answer) {
		questionMap.put(questionNumber, new Question(text, points, answer, "MultiChoice"));
	}

	public void addFillInTheBlanksQuestion(int questionNumber, String text, double points,
			String[] answer) {
		questionMap.put(questionNumber, new Question(text, points, answer, "FillBlanks"));
	}
	
	public String getKey() {
		StringBuffer strB = new StringBuffer();
		for (Map.Entry<Integer, Question> entry : questionMap.entrySet()) {
			Question question = entry.getValue();
			strB.append("Question Text: " + question.questionText + "\n");
			strB.append("Points: " + question.points + "\n");
			if (question.questionType.equals("TorF")) {
				if (question.booleanAnswer) {
					strB.append("Correct Answer: True\n");
				} else {
					strB.append("Correct Answer: False\n");
				}
			} else {
				strB.append("Correct Answer: [");
				Arrays.sort(question.stringAnswer);
				int i;
				for (i = 0; i < question.stringAnswer.length; i++) {
					strB.append(question.stringAnswer[i]);
					if(i != question.stringAnswer.length - 1) {
						strB.append(", ");
					}
				}
				strB.append("]\n");
			}
		}
		return strB.toString();
	}
	
}
