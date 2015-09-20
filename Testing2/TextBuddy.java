package Testing2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class TextBuddy {
	
	private static final String WELCOME_ERROR_MESSAGE = "Please enter your input file argument and try again.";	
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADDED= "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETED = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	//private static final String File_ERROR_MESSAGE = "File not found";
	
	private ArrayList<String> listContents;
	private String listName;
	private static Scanner sc;
	
	
	public static void main(String[] args) { 
		if (args.length == 1) {
			TextBuddy textBuddy = new TextBuddy(args[0]);
			displayToUser(String.format(WELCOME_MESSAGE, args[0]));
			sc = new Scanner(System.in);
			while(true){
				System.out.println("Command: ");
				String userInput = sc.nextLine();
				textBuddy.processCommand(userInput);
			}
		} else {
			displayToUser(WELCOME_ERROR_MESSAGE);
		}
	}
	
	public void processCommand(String userInput) {
		// TODO Auto-generated method stub
		String firstWordOfInput = userInput.trim().split("\\s+")[0];
		String removeFirstWord = userInput.replace(firstWordOfInput, "").trim(); 
		try{
			switch(firstWordOfInput){
			case "add": 
				add(removeFirstWord);
				break;
			case "display": 
				display();
				break;
			case "delete": 
				delete(new Integer(removeFirstWord));
				break;
			case "clear": 
				clear();
				break;
			case "exit": 
				close();
				break;
			default: 
				displayToUser("Unrecognized command type");
				break;
			}
		} catch(NumberFormatException e){
			displayToUser("Please use a valid number");
		}
	}

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

	private void clear() {
		// TODO Auto-generated method stub
		listContents = new ArrayList<String>();
		listContents.add("");	// dummy string added so that the index can start from 1
		displayToUser(String.format(MESSAGE_CLEAR, listName));
	}

	private void delete(int numberOfDelete) {
		// TODO Auto-generated method stub
		if (numberOfDelete < 1 || numberOfDelete >= listContents.size()){
			displayToUser("The text that you want to delete does not exist");
		} else {
			String deletedText = listContents.remove(numberOfDelete);
			displayToUser(String.format(MESSAGE_DELETED, listName, deletedText));
		}
	}

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

	private void add(String removeFirstWord) {
		// TODO Auto-generated method stub
		listContents.add(removeFirstWord);
		displayToUser(String.format(MESSAGE_ADDED, listName, removeFirstWord));
	}

	public TextBuddy(String fileName){
		listContents = new ArrayList<String>();
		listContents.add("");	// dummy string added so that the index can start from 1
		this.listName = fileName;
		try {
			sc = new Scanner(new File(fileName));
			while(sc.hasNextLine()){
				listContents.add(sc.nextLine());
			}
		} catch (FileNotFoundException e) {
			//displayToUser(File_ERROR_MESSAGE);
			throw new Error("File not found");
		}
	}

	private static void displayToUser(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
	}
}
