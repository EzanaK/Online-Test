package onlineTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {
	
	protected Map<Integer, Grade> gradeMap;
	
	public Student(String name) {
		gradeMap = new HashMap<>();
	}
		
	public void answerTrueFalseQuestion(int examId, int questionNumber, boolean answer, Map<Integer, Exam> examMap) {
		if (!gradeMap.containsKey(examId)) {
			gradeMap.put(examId, new Grade());
		}
		Grade grade = gradeMap.get(examId);
		grade.answerTrueFalseQuestion(questionNumber, answer, examMap.get(examId).questionMap);
	}
	
	public void answerMultipleChoiceQuestion(int examId, int questionNumber, String[] answer, Map<Integer, Exam> examMap) {
		if (!gradeMap.containsKey(examId)) {
			gradeMap.put(examId, new Grade());
		}
		Grade grade = gradeMap.get(examId);
		grade.answerMultipleChoiceQuestion(questionNumber, answer, examMap.get(examId).questionMap);
	}

	public void answerFillInTheBlankQuestion(int examId, int questionNumber, String[] answer, Map<Integer, Exam> examMap) {
		if (!gradeMap.containsKey(examId)) {
			gradeMap.put(examId, new Grade());
		}
		Grade grade = gradeMap.get(examId);
		grade.answerFillInTheBlankQuestion(questionNumber, answer, examMap.get(examId).questionMap);
	}

	public double getExamScore(int examId) {
		return gradeMap.get(examId).earnedExamPoints;
	}

	public String getGradingReport(int examId) {
		return gradeMap.get(examId).getGradingReport();
	}
	
	public double getCourseNumericGrade() {
		double totalScores = 0;
		int numOfExams = 0;
		double examPerecentage;
		for (Map.Entry<Integer, Grade> entry : gradeMap.entrySet()) {
			examPerecentage = (entry.getValue().earnedExamPoints / entry.getValue().totalExamPoints) * 100;
			totalScores += examPerecentage;
			numOfExams++;
		}
		return totalScores / numOfExams;
	}
	
}
