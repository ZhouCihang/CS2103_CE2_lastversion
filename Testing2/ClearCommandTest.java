package Testing2;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class ClearCommandTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	@Test
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	public void Test() {
	    TextBuddy test = new TextBuddy("mytextfile.txt");
	    test.processCommand("clear");
	    assertEquals("all content deleted from mytextfile.txt", outContent.toString());
	}
}
