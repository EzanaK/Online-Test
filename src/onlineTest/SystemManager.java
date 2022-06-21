package onlineTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SystemManager implements Manager, Serializable{
	
	private Map<Integer, Exam> examMap;
	private Map<String, Student> studentMap;
	private String[] letterGrades;
	private double[] cutoffs;
	
	public SystemManager() {
		examMap = new HashMap<>();
		studentMap = new HashMap<>();
	}
	
	@Override
	public boolean addExam(int examId, String title) {
		if (examMap.containsKey(examId)) {
			return false;
		} else {
			examMap.put(examId, new Exam(title));
			return true;
		}
	}
	
	@Override
	public void addTrueFalseQuestion(int examId, int questionNumber, String text, double points, boolean answer) {
		examMap.get(examId).addTrueFalseQuestion(questionNumber, text, points, answer);
	}

	@Override
	public void addMultipleChoiceQuestion(int examId, int questionNumber, String text, double points, String[] answer) {
		examMap.get(examId).addMultipleChoiceQuestion(questionNumber, text, points, answer);
	}

	@Override
	public void addFillInTheBlanksQuestion(int examId, int questionNumber, String text, double points,
			String[] answer) {
		examMap.get(examId).addFillInTheBlanksQuestion(questionNumber, text, points, answer);
	}

	@Override
	public String getKey(int examId) {
		return examMap.get(examId).getKey();
	}

	@Override
	public boolean addStudent(String name) {
		if (studentMap.containsKey(name)) {
			return false;
		} else {
			studentMap.put(name, new Student(name));
			return true;
		}
	}

	@Override
	public void answerTrueFalseQuestion(String studentName, int examId, int questionNumber, boolean answer) {
		studentMap.get(studentName).answerTrueFalseQuestion(examId, questionNumber, answer, examMap);
	}

	@Override
	public void answerMultipleChoiceQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		studentMap.get(studentName).answerMultipleChoiceQuestion(examId, questionNumber, answer, examMap);
	}

	@Override
	public void answerFillInTheBlanksQuestion(String studentName, int examId, int questionNumber, String[] answer) {
		studentMap.get(studentName).answerFillInTheBlankQuestion(examId, questionNumber, answer, examMap);
	}

	@Override
	public double getExamScore(String studentName, int examId) {
		return studentMap.get(studentName).getExamScore(examId);
	}

	@Override
	public String getGradingReport(String studentName, int examId) {
		return studentMap.get(studentName).getGradingReport(examId);
	}

	@Override
	public void setLetterGradesCutoffs(String[] letterGrades, double[] cutoffs) {
		this.letterGrades = letterGrades;
		this.cutoffs = cutoffs;
	}

	@Override
	public double getCourseNumericGrade(String studentName) {
		return studentMap.get(studentName).getCourseNumericGrade();
	}

	@Override
	public String getCourseLetterGrade(String studentName) {
		double numericGrade = getCourseNumericGrade(studentName);
		String letterGrade = new String();
		int i;
		for (i= 0; i < cutoffs.length; i++) {
			if (numericGrade >= cutoffs[i]) {
				letterGrade = letterGrades[i];
				break;
			}
		}
		return letterGrade;
	}

	@Override
	public String getCourseGrades() {
		ArrayList<String> listOfStudentGrades = new ArrayList<>();
		for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
			String studentGrades = (entry.getKey() + " " + getCourseNumericGrade(entry.getKey()) + " " + getCourseLetterGrade(entry.getKey()));
			listOfStudentGrades.add(studentGrades);
		}
		Collections.sort(listOfStudentGrades);
		StringBuffer strB = new StringBuffer();
		int i;
		for (i = 0; i < listOfStudentGrades.size(); i++) {
			strB.append(listOfStudentGrades.get(i) + "\n");
		}
		return strB.toString();
	}

	@Override
	public double getMaxScore(int examId) {
		double maxScore = 0;
		for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
			double examScore = getExamScore(entry.getKey(), examId);
			if (examScore > maxScore) {
				maxScore = examScore;
			}
		}
		return maxScore;
	}

	@Override
	public double getMinScore(int examId) {
		double minScore = 10000;
		for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
			double examScore = getExamScore(entry.getKey(), examId);
			if (examScore < minScore) {
				minScore = examScore;
			}
		}
		return minScore;
	}

	@Override
	public double getAverageScore(int examId) {
		double totalScore = 0;
		int numOfStudents = 0;
		for (Map.Entry<String, Student> entry : studentMap.entrySet()) {
			double examScore = getExamScore(entry.getKey(), examId);
			totalScore += examScore;
			numOfStudents++;
		}
		return totalScore / numOfStudents;
	}

	@Override
	public void saveManager(Manager manager, String fileName) {
		try {
			File file = new File(fileName);
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
			output.writeObject(manager);
			output.close();
		} catch (Exception e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
	}

	@Override
	public Manager restoreManager(String fileName) {
		File file = new File(fileName);
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
			Manager manager = (Manager) input.readObject();
			input.close();
			return manager;
		} catch (Exception e) {
			System.out.println("ERROR");
			System.out.println(e.getMessage());
		}
		return null;
	}
}

