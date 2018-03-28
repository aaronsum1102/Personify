
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MessengerTest {
	private Messenger messenger;

	@BeforeEach
	void setup() {
		messenger = new Messenger();
	}

	@AfterEach
	void tearDown() {
		messenger = null;
	}

	@Test
	void testAddMessage() {
		boolean result = messenger.add("Falcon Heavy rocks!");
		assertEquals(true, result);
		assertEquals(1, messenger.getMessages().size());
	}

	@Test
	void testAddMessageWithEmptyString() {
		boolean result = messenger.add("");
		assertEquals(false, result);
		assertEquals(0, messenger.getMessages().size());
	}

	@Test
	void testClearAllMessages() {
		assertEquals(true, messenger.add("This is a test case."));
		assertEquals(true, messenger.add("I'm a test."));
		assertEquals(true, messenger.add("I'm a test as well."));
		assertEquals(3, messenger.getMessages().size());
		messenger.clearMessages();
		assertEquals(0, messenger.getMessages().size());
	}
}
