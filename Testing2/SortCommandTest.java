package Testing2;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Test;

public class SortCommandTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	@Test
	public void test() {
		
		TextBuddy test = new TextBuddy("mytextfile.txt");
	    test.processCommand("add Fly to the moon");
	    test.processCommand("add Touch the sky");
	    test.processCommand("add can i have your number");
	    test.processCommand("sort");
	    test.processCommand("display");
	    
	    assertEquals("1.can i have your number\n"+"2.Fly to the moon\n"+"3.Touch the sky", outContent.toString());
	}

}
