package Testing2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * This class is used to create a file, as well as the user can add data to the file
 * Or remove data to the file. For example, user can type add "sky and mountain". 
 * The "sky and mountain" will be add to the .txt file. Other than this, the related
 * Message will also print on the console. Furthermore, the program also can delete the 
 * content which added before. For example, user type delete 2. The program will remove 
 * the second item in the programs. This program can no limit items unless you type exit
 * to quit the program. Other than those basic commands, the program can execute sort with
 * alphabet or search by keywords. This program still have a lot of places to improve. 
 * The command format is given by the example interaction below:

>	Welcome to TextBuddy.  mytestfile2.txt is ready for use.
>	Command: add fly to the mountain and sky
>	added to  mytestfile2.txt: "fly to the mountain and sky" 
>	Command: add it's a beautiful night	
>	added to  mytestfile2.txt: "it's a beautiful night" 
>	Command: add a pencil case
>	added to  mytestfile2.txt: "a pencil case" 
>	Command: display
>	1. fly to the mountain and sky
>	2. it's a beautiful night
>	3. a pencil case
>	Command: sort
>	Command: display
>	1. a pencil case
>	2. fly to the mountain and sky
>	3. it's a beautiful night
>	Command: search and
>	Command: 2
>	Command: delete 2
>	deleted from  mytestfile2.txt: fly to the mountain and sky
>	Command: display
>	1. a pencil case
>	2. it's a beautiful night
>	Command: exit

 * @author Zhou Cihang (A0126410X) group F11
 */



public class TextBuddy {
	
	
	//Display Messages
	private static final String WELCOME_ERROR_MESSAGE = "Please enter your input file argument and try again.";	
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADDED= "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String ERROR_COMMAND_INPUT = "Unrecognized command type";
	private static final String ERROR_COMMAND_FORMAT = "Please enter a valid command";
	//Other declaration
	private ArrayList<String> listContents;
	private String listName;
	private static Scanner sc;
	
	//Command types
	private static final String EXIT_COMMAND = "exit";
	private static final String SORT_COMMAND = "sort";
	private static final String SEARCH_COMMAND = "search";
	private static final String CLEAR_COMMAND  = "clear";
	private static final String DELETE_COMMAND = "delete";
	private static final String ADD_COMMAND = "add";
	private static final String DISPLAY_COMMAND = "display";
	/**
	 * @param args
	 *            The program input arguments.
	 */
	public static void main(String[] args) { 
		
		if (args.length == 1) {
			TextBuddy textBuddy = new TextBuddy(args[0]);
			displayToUser(String.format(WELCOME_MESSAGE, args[0]));
			sc = new Scanner(System.in);
			while(true){
				System.out.print("Command: ");
				String userInput = sc.nextLine();
				textBuddy.processCommand(userInput);
			}
		} else {
			displayToUser(WELCOME_ERROR_MESSAGE);
		}
	}
	
	/**
	 * This method will process commands continuously until exit command is
	 * issued.
	 * 
	 * @param userInput
	 *            : The command string of the user to be used for the program.
	 * 
	 */
	public void processCommand(String userInput) {
		// TODO Auto-generated method stub
		String firstWordOfInput = userInput.trim().split("\\s+")[0];
		String removeFirstWord = userInput.replace(firstWordOfInput, "").trim(); 
		try{
			switch(firstWordOfInput){
			//case "add":
				case ADD_COMMAND:
					add(removeFirstWord);
					break;
				case DISPLAY_COMMAND: 
					display();
					break;
				case DELETE_COMMAND: 
					delete(new Integer(removeFirstWord));
					break;
				case CLEAR_COMMAND: 
					clear();
					break;
				case EXIT_COMMAND : 
					close();
					break;
				case SORT_COMMAND:
					sort();
					break;
				case SEARCH_COMMAND:
					search(removeFirstWord);
					break;
				default: 
					displayToUser(ERROR_COMMAND_INPUT);
					break;
			}
		} catch(NumberFormatException e){
			displayToUser(ERROR_COMMAND_FORMAT);
		}
	}

	/**
	 * This function will search the keyword in the corresponding inputFileName
	 * Path.
	 * 
	 * @param str
	 *            : The keyword which user want to search 
	 *            for.
	 */
	private void search(String str) {
		// TODO Auto-generated method stub
		for(int i=0; i<listContents.size(); i++){
			if(listContents.get(i).contains(str)){
				System.out.println(i);
			}
		}
	}

	/**
	 * This function will sort text lines alphabetically
	 * 
	 *
	 */
	private void sort() {
		// TODO Auto-generated method stub
		Collections.sort(listContents, String.CASE_INSENSITIVE_ORDER);
		for(int i = 1; i < listContents.size(); i++){
			System.out.print(i + ". " );
			displayToUser(listContents.get(i));
		}
	}
	
	/**
	 * This function will close the  corresponding File Name
	 * Path.
	 *
	 */
	private void close() {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(listName));
			for(int i = 1; i < listContents.size(); i++){
				bw.write(listContents.get(i));
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (IOException e) {
			System.out.println("Sorry but the file failed to save properly");
		}
		sc.close();
		System.exit(0);
	}

	/**
	 * This function will clear the text in the corresponding File Name
	 * Path.
	 *
	 */
	private void clear() {
		// TODO Auto-generated method stub
		listContents = new ArrayList<String>();
		listContents.add("");	// dummy string added so that the index can start from 1
		displayToUser(String.format(MESSAGE_CLEAR, listName));
	}

	/**
	 * This function will delete line of the text in the corresponding inputFileName
	 * Path.
	 * 
	 * @param numberOfDelete
	 *            : The number of line which user want
	 *            to delete.
	 */
	private void delete(int numberOfDelete) {
		// TODO Auto-generated method stub
		if (numberOfDelete < 1 || numberOfDelete >= listContents.size()){
			displayToUser("The text that you want to delete does not exist");
		} else {
			String deletedText = listContents.remove(numberOfDelete);
			displayToUser(String.format(MESSAGE_DELETED, listName, deletedText));
		}
	}

	/**
	 * This function will process the Display command and output the text file
	 * content.
	 * 
	 */
	private void display() {
		// TODO Auto-generated method stub
		if(listContents.size() == 1){	// fileContents is considered empty if it only contains the dummy string
			displayToUser(listName + " is empty");
		} else {
			for(int i = 1; i < listContents.size(); i++){
				System.out.print(i + ". " );
				displayToUser(listContents.get(i));
			}
		}
	}

	/**
	 * This function process the Add command and insert the text to the end of
	 * the file.
	 * 
	 * @param removeFirstWord
	 *            : The string which has removed the first keywords,
	 *            and it to be used for the program.
	 *           
	 */
	private void add(String removeFirstWord) {
		// TODO Auto-generated method stub
		listContents.add(removeFirstWord);
		displayToUser(String.format(MESSAGE_ADDED, listName, removeFirstWord));
	}
	
	/**
	 * This constructor will create a file based on the inputFileName if the file
	 * does not exists.
	 * 
	 * @param inputFileName
	 *            : The path of the text file to be used for the program.
	 */
	public TextBuddy(String fileName){
		listContents = new ArrayList<String>();
		listContents.add("");
		this.listName = fileName;
		try {
			sc = new Scanner(new File(fileName));
			while(sc.hasNextLine()){
				listContents.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			throw new Error("File not found");
		}
	}

	/**
	 * This function will display the message to the user using the output
	 * console.
	 * 
	 * @param message
	 *            : the message to be displayed.
	 */
	private static void displayToUser(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}
}
