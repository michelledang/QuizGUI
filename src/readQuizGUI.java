import javax.swing.*;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.RandomAccessFile;

//declare class
public class readQuizGUI {

	//declare GUI objects + variables
	static JLabel titleLabel, rightLabel, rightLabel2;
	static JButton button;
	static JPanel panel;
	static JRadioButton choice1, choice2, choice3, choice4;
	static JComboBox<String> tfSelect;
	static JTextField srtAnsBox;
	static ButtonGroup multRadio;

	//declare String + int variables
	static int numQuestions, notNumQuestions, quesNum, typeFromFile, ansLen;
	static String ansStr, ansIntStr;
	static String[] rightAnswers, userAnswers;

	//declare random access file
	static RandomAccessFile quizFile;
	
	private static void guiApp() throws IOException {
		
		//sets quizFile to 'quiz_entered'
		quizFile = new RandomAccessFile("quiz_entered","rw");

		//starts at beginning of file
		quizFile.seek(0);
		
		//reads total number of questions from file
		numQuestions = Integer.parseInt(readNumStr());
		
		//calculates question number from numQuestions
		notNumQuestions = numQuestions - 1;
		quesNum = numQuestions - notNumQuestions;
		
		//variable used to hold the length of a short answer
		ansLen = 0;
		
		//array used to hold all the right question answers from the file
		rightAnswers = new String [50];
		
		//array used to hold all the answered entered by the user
		userAnswers = new String [50];
		
		//Create/Set-up window
		JFrame frame = new JFrame("Quiz GUI");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanel used as a container for widgets
		panel = new JPanel(new GridLayout(6,1,20,5));
		
		//labels
		titleLabel = new JLabel("Welcome to your Quiz!");
		rightLabel = new JLabel("");
		rightLabel2 = new JLabel("");

		//buttons
		button = new JButton("Click to start Quiz");
		button.setActionCommand("quiz");
		
		//true/false combo box
		String[] tfType = new String[] {"true", "false"};
		tfSelect = new JComboBox<String>(tfType);
		
		//radio buttons
		multRadio = new ButtonGroup();
		
		choice1 = new JRadioButton("", true);
		choice2 = new JRadioButton("", false);
		choice3 = new JRadioButton("", false);
		choice4 = new JRadioButton("", false);
		
		multRadio.add(choice1);
		multRadio.add(choice2);
		multRadio.add(choice3);
		multRadio.add(choice4);
		
		//short ans box
		srtAnsBox = new JTextField();
				
		//create a new ButtonHandler instance
		ButtonHandler onClick = new ButtonHandler();
		
		//attach an event listener to the button
		button.addActionListener(onClick);
		
		//new font
		Font f = new Font("monospaced", Font.PLAIN, 24);

		//sets components for welcome screen
		panel.add(titleLabel);
		panel.add(button);
		
		//sets font for all components
		titleLabel.setFont(f);
		button.setFont(f);
		srtAnsBox.setFont(f);
		rightLabel.setFont(f);
		rightLabel2.setFont(f);
		
		choice1.setFont(f);
		choice2.setFont(f);
		choice3.setFont(f);
		choice4.setFont(f);
		
		tfSelect.setFont(f);
	
		frame.add(panel);
		
		//size the window
		frame.setSize(600, 750);
		frame.setVisible(true);
	}
	
	//create custom event handler 
	//- this is what will happen when the button is clicked
	private static class ButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {

			//runs when user is answering a question
			if (button.getActionCommand() == "quiz") {
				
				titleLabel.setText("Question " + quesNum + ": "  );

				try {
					typeFromFile = Integer.parseInt(readIntStr());
				} catch (NumberFormatException | IOException e2) {
					e2.printStackTrace();
				}

				//when type is '0', a multiple choice question layout is set-up
				if (typeFromFile == 0) {
					
					try {
						
						//sets up question layout from question and
						//choices read from file
						titleLabel.setText(readStr());
						choice1.setText(readStr());
						choice2.setText(readStr());
						choice3.setText(readStr());
						choice4.setText(readStr());
						ansIntStr = readIntStr();
						
						rightAnswers[quesNum] = ansIntStr;
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//sets up components for question layout
					choice1.setVisible(true);
					choice2.setVisible(true);
					choice3.setVisible(true);
					choice4.setVisible(true);
					
					multRadio.add(choice1);
					multRadio.add(choice2);
					multRadio.add(choice3);
					multRadio.add(choice4);
					
					panel.add(choice1);
					panel.add(choice2);
					panel.add(choice3);
					panel.add(choice4);
					
					panel.remove(button);
					button.setActionCommand("checkmult");
					button.setText("Submit Answer");
					panel.add(button);
										
				}
				
				//when type is '1', a true/false question layout is set-up
				else if (typeFromFile == 1) {
					
					try {
						//sets up question layout from question read from file
						titleLabel.setText(readStr());						
						rightAnswers[quesNum] = readStrTF();
												
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//sets up components for question layout
					tfSelect.setVisible(true);
					panel.add(tfSelect);
					
					panel.remove(button);
					button.setActionCommand("checktf");
					button.setText("Submit Answer");
					panel.add(button);
					
				}
				//when type is '2', a short answer question layout is set-up
				else  {
					
					try {
						//sets up question layout from question read from file
						titleLabel.setText(readStr());						
						rightAnswers[quesNum] = readStr();
						
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					//sets up components for question layout
					srtAnsBox.setVisible(true);
					panel.add(srtAnsBox);
					
					panel.remove(button);
					button.setActionCommand("checksrt");
					button.setText("Submit Answer");
					panel.add(button);
					
				}

			}
			
			//runs after user has completed a multiple choice question
			else if (button.getActionCommand() == "checkmult") {
				
				//adds user answer to userAnswers array
				if (choice1.isSelected()) {
					userAnswers[quesNum] = "1";
				}
				else if (choice2.isSelected()) {
					userAnswers[quesNum] = "2";
				}
				else if (choice3.isSelected()) {
					userAnswers[quesNum] = "3";
				}
				else {
					userAnswers[quesNum] = "4";
				}
				
				//removes mult-choice question components
				choice1.setVisible(false);
				choice2.setVisible(false);
				choice3.setVisible(false);
				choice4.setVisible(false);
				
				multRadio.remove(choice1);
				multRadio.remove(choice2);
				multRadio.remove(choice3);
				multRadio.remove(choice4);
				
				panel.remove(choice1);
				panel.remove(choice2);
				panel.remove(choice3);
				panel.remove(choice4);
				
				//runs if user has not completed last question
				//sends user to next question layout
				if (quesNum != numQuestions) {
					
					titleLabel.setText("Answer has been submitted!");
					panel.remove(button);
					button.setActionCommand("quiz");
					button.setText("Next Question");
					panel.add(button);
				}
				
				//runs if user has completed their last question
				//sends user to 'check answers' layout
				else {
					
					titleLabel.setText("Answer has been submitted!");
					panel.remove(button);
					button.setActionCommand("answers");
					button.setText("Check Answers");
					panel.add(button);
				}

				quesNum += 1;

			}
			
			//runs after a true/false question has been submitted
			else if (button.getActionCommand() == "checktf") {
				
				//adds user answer to userAnswers array
				if (tfSelect.getSelectedItem() == "true") {
					userAnswers[quesNum] = "true";
				}
				
				else {
					userAnswers[quesNum] = "false";
				}
				
				//removes true/false question components
				panel.remove(tfSelect);
				tfSelect.setVisible(false);
				
				//runs if user has not completed last question
				//sends user to next question layout
				if (quesNum != numQuestions) {
					
					titleLabel.setText("Answer has been submitted!");
					panel.remove(button);
					button.setActionCommand("quiz");
					button.setText("Next Question");
					panel.add(button);
				}
				
				//runs if user has completed their last question
				//sends user to 'check answers' layout
				else {
					
					titleLabel.setText("Answer has been submitted!");					
					panel.remove(button);
					button.setActionCommand("answers");
					button.setText("Check Answers");
					panel.add(button);
				}
				
				quesNum += 1;

			}
			
			else if (button.getActionCommand() == "checksrt") {
				
				//adds user answer to userAnswers array
				userAnswers[quesNum] = srtAnsBox.getText();
				
				//gets length of user's short answer
				ansLen = userAnswers[quesNum].length();
				
				if (ansLen == 0) {
					userAnswers[quesNum] = "no answer was entered";
				}
				
				else {
					//only uses answer read from file at the length of the
					//user-submitted answer, instead of the entire '50' chars.
					rightAnswers[quesNum] = cutSrtAns(rightAnswers[quesNum], ansLen);
				}
				
				//removes short answer question components
				panel.remove(srtAnsBox);
				srtAnsBox.setVisible(false);
				
				//runs if user has not completed last question
				//sends user to next question layout
				if (quesNum != numQuestions) {
					
					titleLabel.setText("Answer has been submitted!");
					panel.remove(button);
					button.setActionCommand("quiz");
					button.setText("Next Question");
					panel.add(button);
				}
				
				//runs if user has completed their last question
				//sends user to 'check answers' layout
				else {
					
					titleLabel.setText("Answer has been submitted!");
					panel.remove(button);
					button.setActionCommand("answers");
					button.setText("Check Answers");
					panel.add(button);
				}
				
				quesNum += 1;

				
			}
			
			//runs when user has completed all questions and
			//wants to know their mark
			else if (button.getActionCommand() == "answers") {

				int numRightAns = 0;
				double percentRight = 0.0;
				
				//array holds questions that were answered right
				int[] quesAnsRight = new int[numQuestions];
				int num = 0;
				
				//loop compares all user's answers with the right answers from file
				for (int i = 1; i <= numQuestions; i++) {
					
					//runs if user answer matches file answer
					if (rightAnswers[i].equals(userAnswers[i]) || rightAnswers[i].equalsIgnoreCase(userAnswers[i])) {
						//adds to number of questions answered right
						numRightAns += 1;
						
						//writes question number to array
						quesAnsRight[num] = i;
						
						//adds one to index of quesAnsRight array
						num += 1;
					}
					
				}
				
				//calculates percentage of right answers
				percentRight = (double)numRightAns / numQuestions;
				percentRight = (int) (percentRight * 100);
				
				titleLabel.setText("Percentage of right answers: " + percentRight + "%");
				
				//variable for 'right answers' label
				String rightAnsStr = "";
				
				//variable for question number 
				String quesNumCorrect = "";
				
				//loop goes through quesAnsRight array and adds numbers
				//of correctly answered questions to the 'right answers' label
				for (int i = 0; i < numRightAns; i++) {
					
					rightAnsStr += ", ";
					quesNumCorrect = Integer.toString(quesAnsRight[i]);
					if (i == 0) {
						rightAnsStr = "";
					}
					rightAnsStr = rightAnsStr + quesNumCorrect;
					
				}
				
				//sets labels to list questions answered correctly
				rightLabel.setText("Question(s) answered correctly: ");
				rightLabel2.setText(rightAnsStr);
				panel.add(rightLabel);
				panel.add(rightLabel2);
				
				//add exit button
				panel.remove(button);
				button.setActionCommand("close");
				button.setText("Exit Program");
				panel.add(button);
			}
			
			//runs when user clicks last program button
			else if (button.getActionCommand() == "close") {
				//closes program
				System.exit(1);

			}

		}
	}
	
	public static void main(String[] args) {
		
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				
				try {
					guiApp();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	//reads '50' char length String from file
	//returns String read
	public static String readStr() throws IOException {
    	char choice1[] = new char[50];
    	String str = "";

    	for(int j=0;j<50;j++){

    	  choice1[j]=quizFile.readChar();
    	  str = str + Character.toString(choice1[j]);

    	}
    	System.out.println(str);
		return str;
	}
	
	//shortens short answer from file to match the length of user's answer
	//@param ans - short answer from file being shortened
	//@param len - length of user's short answer
	//@return str - String ans at length of user's answer
	public static String cutSrtAns(String ans, int len) {
		String str = "";
		
		for (int i = 0; i < len; i++) {
			
			str = str + ans.charAt(i);
			
		}
		
		return str;
	}
	
	//reads true/false answer from file
	//returns String "true" or "false"
	public static String readStrTF() throws IOException {
    	char choice1[] = new char[50];
    	char tOrF = 't';
    	
    	//reads all 50 chars so file 'seeker' is at the right position
    	for(int j=0;j<50;j++){
    		
    		choice1[j]=quizFile.readChar();
      	}
    	
    	//sets char to first char read from file answer
    	tOrF = choice1[0];
    	
    	if (tOrF == 't') {
        	System.out.println("true");
    		return "true";
    	}
    	
    	else {
        	System.out.println("false");
    		return "false";
    	}
    	
	}
	
	//reads a one digit int from file
	//returns String of single digit int
	public static String readIntStr() throws IOException {
    	char choice1[] = new char[50];
    	String str = "";
    	
    	for(int j=0;j<50;j++){

    	  choice1[j]=quizFile.readChar();
    	}
    	str = Character.toString(choice1[0]);
  	  	   	
		return str;
	}
	
	//reads 2 digit total number of questions from file
	//returns String of 2 digit number
	public static String readNumStr() throws IOException {
    	char choice1[] = new char[50];
    	//int int1 = 0;
    	String str = "";
    	
    	for(int j=0;j<50;j++){

    	  choice1[j]=quizFile.readChar();
    	}
    	//TODO change for 2 digit numbers
    	if (Character.toString(choice1[0]) == "0") {
    		str = Character.toString(choice1[1]);
    	}
    	else {
      	  	str = Character.toString(choice1[0]) + Character.toString(choice1[1]) ;
    	}
  	  	   	
		return str;
	}
	

}
