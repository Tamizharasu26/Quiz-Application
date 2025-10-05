import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


class Question{
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex){
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }
    public void displayQuestion(){
        System.out.println(questionText);
        for(int i=0;i<4;i++){
            System.out.println((i+1)+". "+options.get(i));
        }
    }
    public boolean checkAnswer(int userAnswer){
        if (userAnswer-1==correctOptionIndex){
            return true;
        }else{
            System.out.println("The correct answer was "+(correctOptionIndex+1)+". "+options.get(correctOptionIndex));
            return false;
        }
    }
}

public class QuizApp{
    public static List<Question> loadquestionsFromFile(String filename){
        List<Question> questions=new ArrayList<>();
        try(BufferedReader br=new BufferedReader(new FileReader(filename))){
            String line;
            while((line=br.readLine())!=null){
                String questionText=line.trim();
                if(questionText.isEmpty()) continue;
                List<String> options=new ArrayList<>();
                for(int i=0;i<4;i++){
                    options.add(br.readLine().trim());
                }
                int correctOptionIndex=Integer.parseInt(br.readLine().trim())-1;
                questions.add(new Question(questionText,options,correctOptionIndex));
                br.readLine();
            }
        }catch(IOException e){
            System.out.println("Error reading file: "+e.getMessage());
        }
        catch(Exception e){
            System.out.println("Error parsing file: "+e.getMessage());
        }
        return questions;
    }

    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        List<Question> questions=loadquestionsFromFile("questions.txt");
        if(questions.isEmpty()){
            System.out.println("No questions available. Please check questions.txt.");
            return;
        }
        Collections.shuffle(questions);
        int score=0;
        System.out.println("Welcome to the Quiz!");
        System.out.println("1. Start Quiz");
        System.out.println("2. View Match history");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        int ch=sc.nextInt();
        switch(ch){
            case 1:
                for(int i=0;i<questions.size();i++){
                    System.out.println("\nQuestion "+(i+1)+":");
                    questions.get(i).displayQuestion();
                    System.out.print("Enter your answer (1-4): ");
                    int userAnswer=sc.nextInt();
                    boolean flag=(questions.get(i).checkAnswer(userAnswer));
                    if(flag){
                        System.out.println("Correct!");
                        score++;
                    }
                    if(i!=questions.size()-1){
                        System.out.println("Ready for the next question? (1 to continue | 2 to quit)");
                        int choice=sc.nextInt();
                        if(choice==2){
                            System.out.println("You chose to quit. Your total score is: "+score+"/"+(i+1));
                            sc.close();
                            return;
                        }
                    }
                    if(i==questions.size()-1){
                        System.out.println("\nQuiz Over! Your total score is: "+score+"/"+(i+1));
                        System.out.println("Thank you for playing!");
                    }
                }
                sc.close();  
                break;
            case 2:
                System.out.println("Match history feature is under development.");
                return;
            case 3:
                System.out.println("Exiting the quiz. Goodbye!");
                return;
            default:
                System.out.println("Invalid choice. Exiting.");
                return;
        }
        
    }
}