package Testing2;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class SearchCommandTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	@Test
	public void test() {
		
		TextBuddy test = new TextBuddy("mytextfile.txt");
		
	    test.processCommand("add little brown fox haha");
	    test.processCommand("add jumped over the moon");
	    test.processCommand("add alarma haha");
	    test.processCommand("search ha");
	    
	    assertEquals("1.\n"+ "3.", outContent.toString());
	}
}
