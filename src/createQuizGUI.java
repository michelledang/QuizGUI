/* ---PLAN---
		 * page1: intro page
		 * 			- ask user for number of questions
		 * 			- provide instructions
		 * 			- input teacher name
		 * page2: question page
		 * 			- show box for each question w/ a preview of question
		 * 			- show a 'done quiz' button
		 *			and right answer
		 * page3: page opens when a question box is clicked
		 * 			- radio buttons for type of question
		 * 			- submit button (will change question object?? and close page
		 * 			which will go back to page2)
		 * page 4: final closing page
		 * 			- says thank you
		 * 			- opens file???
		 */
//import required libraries
import javax.swing.*;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

//declare class
public class createQuizGUI {

	//declare GUI variables + objects
	static JLabel label1, label2;
	static JButton clickSubmit;
	static JTextField textBoxQ, textBox1, textBox2, textBox3, textBox4, textBoxA;
	static JPanel panel;
	static JFrame mainFrame, numQuesFrame;
	static JComboBox<Integer> numSelect, multSelect;
	static JComboBox<String> typeSelect, tfSelect;
	
	//declare String and int variables
	static String strSelect, typeStr;
	static int numQuestions, notNumQuestions, intAns, intSelect, quesNum; 

	//declare random access file 
	static RandomAccessFile quizFile;
	
	private static void guiApp() throws IOException {
		
		//creates new random access file called "quiz_entered"
		quizFile = new RandomAccessFile("quiz_entered","rw");

		//starts at beginning of file
		quizFile.seek(0);
		
		//Create/Set-up window
		JFrame mainFrame = new JFrame("Create Quiz GUI");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//JPanel used as a container for widgets
		panel = new JPanel(new GridLayout(10,1,20,5));

		//crreates new textboxes to enter multiple choice and short answer
		textBoxQ = new JTextField(15);
		textBox1 = new JTextField(15);
		textBox2 = new JTextField(15);
		textBox3 = new JTextField(15);
		textBox4 = new JTextField(15);
		textBoxA = new JTextField(15);
		
		//create new default font
		Font f = new Font("monospaced", Font.PLAIN, 24);
				
		//creates labels for questions and answers
		label1 = new JLabel("Please select amount of questions to create: ");
		label2 = new JLabel("Correct Answer: ");

		//creates submit button between questions
		clickSubmit = new JButton("Submit Number of Questions");

		//creates combo box to pick number of questions
		numSelect = new JComboBox<Integer>();
		
		for (int i = 1; i <= 50; i++) {
			
			numSelect.addItem(i);
		}
		
		//creates combo box to select multiple choice right answer
		multSelect = new JComboBox<Integer>();
		
		for (int i = 1; i <= 4; i++) {
			
			multSelect.addItem(i);
		}
		
		//creates combo box to pick type of question
		String[] pickType = new String[] {"multiple choice", "true or false", "short answer"};
		typeSelect = new JComboBox<String>(pickType);
		
		//creates combo box to select true or false answer
		String[] tfType = new String[] {"true", "false"};
		tfSelect = new JComboBox<String>(tfType);
		
		//create a new ButtonHandler instance
		ButtonHandler onClick = new ButtonHandler();
		
		//attach an event listener to the button
		clickSubmit.addActionListener(onClick);
		clickSubmit.setActionCommand("sub");
		
		//adds only first label, number of questions combo box and 
		//submit button
		panel.add(label1);
		panel.add(numSelect);
		panel.add(clickSubmit);
		
		//sets text for all text boxes
		textBoxQ.setText("Please enter a question");
		textBox1.setText("Please enter choice 1");
		textBox2.setText("Please enter choice 2");
		textBox3.setText("Please enter choice 3");
		textBox4.setText("Please enter choice 4");
		textBoxA.setText("Please enter an answer");
		
		//sets font for all text boxes, labels and combo boxes
		textBoxQ.setFont(f);
		textBox1.setFont(f);
		textBox2.setFont(f);
		textBox3.setFont(f);
		textBox4.setFont(f);
		textBoxA.setFont(f);
		
		label1.setFont(f);
		label2.setFont(f);
		
		clickSubmit.setFont(f);
		
		numSelect.setFont(f);
		typeSelect.setFont(f);
		multSelect.setFont(f);
		tfSelect.setFont(f);
		
		panel.setVisible(true);
				
		mainFrame.add(panel);
				
		//size the window
		mainFrame.setSize(750, 750);
		mainFrame.setVisible(true);		

	}
	
	//create custom event handler 
	//- this is what will happen when the button is clicked
	private static class ButtonHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			//only runs after button is clicked once
			if (clickSubmit.getActionCommand() == "sub") {
				//gets number of questions from combo box menu
				numQuestions = (int)numSelect.getSelectedItem();
				try {
					//writes total number of questions to file
					quizFile.writeChars(writeNumInt(numQuestions));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//notNumQuestions used to determine question number
				//by subtracting it from total numQuestions
				notNumQuestions = numQuestions -1;
				label1.setText("Please pick a type for question " + (numQuestions-notNumQuestions) + ": ");
						
				panel.remove(numSelect);
				panel.remove(clickSubmit);
				
				panel.add(typeSelect);
				clickSubmit.setActionCommand("subdos");
				clickSubmit.setText("Submit Type");
				panel.add(clickSubmit);
				
			}
			
			//runs when user is writing a new question
			else if (clickSubmit.getActionCommand() == "subdos") {
				label1.setText("Write Question " + (numQuestions-notNumQuestions) + ": ");
				typeStr = (String) typeSelect.getSelectedItem();
				
				if (typeStr == "multiple choice") {
					
					//sets up components to create a multiple choice question
					panel.remove(clickSubmit);
					panel.remove(typeSelect);
					
					textBoxQ.setText("Please enter a question");
					textBox1.setText("Please enter choice 1");
					textBox2.setText("Please enter choice 2");
					textBox3.setText("Please enter choice 3");
					textBox4.setText("Please enter choice 4");
					
					panel.add(textBoxQ);
					panel.add(textBox1);
					panel.add(textBox2);
					panel.add(textBox3);
					panel.add(textBox4);
					
					panel.add(label2);
					panel.add(multSelect);

					clickSubmit.setActionCommand("submult");
					clickSubmit.setText("Submit Question " + (numQuestions-notNumQuestions));
					panel.add(clickSubmit);
					
					notNumQuestions -= 1; 

				}
				
				else if (typeStr == "true or false") {
					
					//sets up components to create a true/false question
					panel.remove(clickSubmit);
					panel.remove(typeSelect);
					
					textBoxQ.setText("Please enter a statement");

					panel.add(textBoxQ);
					
					panel.add(label2);
					panel.add(tfSelect);
					
					clickSubmit.setActionCommand("subtf");
					clickSubmit.setText("Submit Question " + (numQuestions-notNumQuestions));
					panel.add(clickSubmit);

					notNumQuestions -= 1; 

				}
				
				else {
					
					//sets up components to create a short answer question
					panel.remove(clickSubmit);
					panel.remove(typeSelect);
					
					textBoxQ.setText("Please enter a question");
					textBoxA.setText("Please enter an answer");

					panel.add(textBoxQ);
					
					panel.add(label2);
					panel.add(textBoxA);

					clickSubmit.setActionCommand("subsrt");
					clickSubmit.setText("Submit Question " + (numQuestions-notNumQuestions));
					panel.add(clickSubmit);

					notNumQuestions -= 1; 

				}

			}
			
			//runs after submitting a multiple choice question and
			//writes question to file
			else if (clickSubmit.getActionCommand() == "submult") {
				
				//runs if the last question has been written
				//sends user to "end screen" layout instead of next question layout
				if (notNumQuestions < 0) {
					label1.setText("You have completed writing all the questions.");

					panel.remove(clickSubmit);
					panel.remove(label2);	
					
					panel.remove(textBoxQ);
					panel.remove(textBox1);
					panel.remove(textBox2);
					panel.remove(textBox3);
					panel.remove(textBox4);
					panel.remove(multSelect);
					
					try {
						//writing a '0' tells readQuizGUI that the question
						//is a multiple choice question
						quizFile.writeChars(writeFileInt(0));
						quizFile.writeChars(writeFile(textBoxQ.getText()));
						quizFile.writeChars(writeFile(textBox1.getText()));
						quizFile.writeChars(writeFile(textBox2.getText()));
						quizFile.writeChars(writeFile(textBox3.getText()));
						quizFile.writeChars(writeFile(textBox4.getText()));
						intSelect = (int) multSelect.getSelectedItem();
						quizFile.writeChars(writeFileInt(intSelect));
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					clickSubmit.setActionCommand("subfin");
					clickSubmit.setText("Submit Quiz and Close Program");
					panel.add(clickSubmit);


				}
				
				//runs when user has not completed last question
				//sends user to next question layout
				else {
					label1.setText("Please pick a type for question " + (numQuestions-notNumQuestions) + ": ");
					panel.remove(clickSubmit);
					panel.remove(label2);
					
					panel.remove(textBoxQ);
					panel.remove(textBox1);
					panel.remove(textBox2);
					panel.remove(textBox3);
					panel.remove(textBox4);
					panel.remove(multSelect);
					
					try {
						//writing a '0' tells readQuizGUI that the question
						//is a multiple choice question
						quizFile.writeChars(writeFileInt(0));
						quizFile.writeChars(writeFile(textBoxQ.getText()));
						quizFile.writeChars(writeFile(textBox1.getText()));
						quizFile.writeChars(writeFile(textBox2.getText()));
						quizFile.writeChars(writeFile(textBox3.getText()));
						quizFile.writeChars(writeFile(textBox4.getText()));
						intSelect = (int) multSelect.getSelectedItem();
						quizFile.writeChars(writeFileInt(intSelect));
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					panel.add(typeSelect);
					clickSubmit.setActionCommand("subdos");
					clickSubmit.setText("Submit Type");
					panel.add(clickSubmit);
				}
				
			}
			
			//runs after submitting a true/false question and
			//writes question to file
			else if (clickSubmit.getActionCommand() == "subtf") {
				
				//runs if the last question has been written
				//sends user to "end screen" layout instead of next question layout
				if (notNumQuestions < 0) {
					label1.setText("You have completed writing all the questions.");
					
					panel.remove(label2);	
					panel.remove(textBoxQ);
					panel.remove(tfSelect);
					
					try {
						//writing a '1' tells readQuizGUI that the question
						//is a true/false question
						quizFile.writeChars(writeFileInt(1));
						quizFile.writeChars(writeFile(textBoxQ.getText()));
						strSelect = (String) tfSelect.getSelectedItem();
						quizFile.writeChars(writeFile(strSelect));
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					clickSubmit.setActionCommand("subfin");
					clickSubmit.setText("Submit Quiz and Close Program");

				}
				
				//runs when user has not completed last question
				//sends user to next question layout
				else {
					label1.setText("Please pick a type for question " + (numQuestions-notNumQuestions) + ": ");
					panel.remove(label2);

					panel.remove(clickSubmit);
					panel.add(typeSelect);
					
					panel.remove(textBoxQ);
					panel.remove(tfSelect);
					
					try {
						//writing a '1' tells readQuizGUI that the question
						//is a true/false question
						quizFile.writeChars(writeFileInt(1));
						quizFile.writeChars(writeFile(textBoxQ.getText()));
						strSelect = (String) tfSelect.getSelectedItem();
						quizFile.writeChars(writeFile(strSelect));
						
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					clickSubmit.setActionCommand("subdos");
					clickSubmit.setText("Submit Type");
					panel.add(clickSubmit);
				}
				

			}
			
			//runs after submitting a short answer question and
			//writes question to file
			else if (clickSubmit.getActionCommand() == "subsrt") {
				
				//runs if the last question has been written
				//sends user to "end screen" layout instead of next question layout
				if (notNumQuestions < 0) {
					label1.setText("You have completed writing all the questions.");
					
					panel.remove(label2);	
					panel.remove(textBoxQ);
					panel.remove(textBoxA);
					
					try {
						//writing a '2' tells readQuizGUI that the question
						//is a short answer question
						quizFile.writeChars(writeFileInt(2));
						quizFile.writeChars(writeFile(textBoxQ.getText()));
						quizFile.writeChars(writeFile(textBoxA.getText()));
						
					} catch (IOException e1) {
					e1.printStackTrace();
					}
					
					clickSubmit.setActionCommand("subfin");
					clickSubmit.setText("Submit Quiz and Close Program");

				}
				
				//runs when user has not completed last question
				//sends user to next question layout
				else {
					label1.setText("Please pick a type for question " + (numQuestions-notNumQuestions) + ": ");
					panel.remove(label2);

					panel.remove(clickSubmit);
					panel.add(typeSelect);
					
					panel.remove(textBoxQ);
					panel.remove(textBoxA);
					
					try {
						//writing a '2' tells readQuizGUI that the question
						//is a short answer question
						quizFile.writeChars(writeFileInt(2));
						quizFile.writeChars(writeFile(textBoxQ.getText()));
						quizFile.writeChars(writeFile(textBoxA.getText()));

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					clickSubmit.setActionCommand("subdos");
					clickSubmit.setText("Submit Type");
					panel.add(clickSubmit);
				}

			}
			
			//closes program when "end screen" button is pressed
			else if (clickSubmit.getActionCommand() == "subfin") {
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
					e.printStackTrace();
				}
			}
		});
	}
	
	//sets a String to be a length of 50 chars
	//@param x - String being set to a length of 50
	//@return temp.toString(); - returns @param x at length 50
	public static String writeFile(String x) {
		StringBuffer temp;
		temp = new StringBuffer(x);
		temp.setLength(50);
		return temp.toString();

	}
	
	//sets an int to be a length of 50 chars
	//@param x - int being set to a length of 50
	//@return temp.toString(); - returns @param x at length 50
	public static String writeFileInt(int x) {
		String xStr = Integer.toString(x);
		StringBuffer temp;
		temp = new StringBuffer(xStr);
		temp.setLength(50);
		return temp.toString();

	}
	
	//sets the number of questions int to be a length of 50 chars
	//@param x - int number of questions being set to a length of 50
	//@return temp.toString(); - returns @param x at length 50
	public static String writeNumInt(int x) {
		String xStr = "";
		//puts a "0" in front of number of questions if it is less than 10
		//ex. "3" is changed to "03"
		if (x < 10) {
			xStr = "0" + Integer.toString(x);
		}
		else {
			xStr = Integer.toString(x);

		}
		StringBuffer temp;
		temp = new StringBuffer(xStr);
		temp.setLength(50);
		return temp.toString();

	}

}
