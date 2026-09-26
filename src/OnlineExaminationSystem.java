import java.util.ArrayList;
import java.util.List;

abstract class ExamQuestion {
    protected int questionNumber;
    protected String questionText;

    public ExamQuestion(int questionNumber, String questionText) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public abstract boolean evaluateAnswer(String answer);
}

class MultipleChoiceQuestion extends ExamQuestion {
    private String correctAnswer;

    public MultipleChoiceQuestion(int questionNumber, String questionText, String correctAnswer) {
        super(questionNumber, questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluateAnswer(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }
}

class TrueFalseQuestion extends ExamQuestion {
    private boolean correctAnswer;

    public TrueFalseQuestion(int questionNumber, String questionText, boolean correctAnswer) {
        super(questionNumber, questionText);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluateAnswer(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;
    }
}

class ExamStudent {
    private String name;

    public ExamStudent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class ExamAttempt {
    private ExamStudent student;
    private OnlineExamination examination;
    private List<String> answers;
    private boolean submitted;
    private int score;

    public ExamAttempt(ExamStudent student, OnlineExamination examination) {
        this.student = student;
        this.examination = examination;
        this.answers = new ArrayList<>();
        this.submitted = false;
        this.score = 0;
    }

    public void answerQuestion(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Answers cannot be changed after submission.");
            return;
        }

        while (answers.size() < questionNumber) {
            answers.add(null);
        }

        answers.set(questionNumber - 1, answer);

        System.out.println("Question " + questionNumber +
                " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) {
            System.out.println("Attempt has already been submitted.");
            return;
        }

        score = 0;

        for (int i = 0; i < examination.getQuestions().size(); i++) {
            if (i < answers.size() && answers.get(i) != null) {
                if (examination.getQuestions().get(i)
                        .evaluateAnswer(answers.get(i))) {
                    score++;
                }
            }
        }

        submitted = true;

        System.out.println("Examination '" + examination.getTitle()
                + "' submitted successfully.");

        System.out.println("Result for '" + examination.getTitle()
                + "' attempt: " + score + "/"
                + examination.getQuestions().size() + " correct");
    }
}

class OnlineExamination {
    private String title;
    private List<ExamQuestion> questions;
    private ExamAttempt submittedAttempt;

    public OnlineExamination(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
        this.submittedAttempt = null;
    }

    public String getTitle() {
        return title;
    }

    public List<ExamQuestion> getQuestions() {
        return questions;
    }

    public void addQuestion(ExamQuestion question) {
        questions.add(question);
    }

    public ExamAttempt startExam(ExamStudent student) {
        if (submittedAttempt != null) {
            System.out.println("A submitted attempt already exists for this examination.");
            return null;
        }

        System.out.println("Examination '" + title
                + "' started by " + student.getName() + ".");

        return new ExamAttempt(student, this);
    }

    public void saveSubmittedAttempt(ExamAttempt attempt) {
        if (submittedAttempt == null) {
            submittedAttempt = attempt;
        }
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {

        ExamStudent student = new ExamStudent("Anwesha");

        OnlineExamination exam = new OnlineExamination("Math Quiz");

        exam.addQuestion(new MultipleChoiceQuestion(
                1,
                "What is 2 + 2?",
                "A"
        ));

        exam.addQuestion(new MultipleChoiceQuestion(
                2,
                "What is 3 + 3?",
                "B"
        ));

        ExamAttempt attempt = exam.startExam(student);

        if (attempt != null) {
            attempt.answerQuestion(1, "A");
            attempt.answerQuestion(2, "C");

            attempt.submit();

            exam.saveSubmittedAttempt(attempt);
        }
    }
}