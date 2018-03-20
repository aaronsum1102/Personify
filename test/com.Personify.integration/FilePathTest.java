
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.FilePath;

class FilePathTest {
	private FilePath path;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
		path = null;
	}

	@Test
	void testFilePathWithNonEmptyName() {
		path = new FilePath("src", "test.txt");
		String expectedResult = "src/test.txt";
		String actualResult = path.getPathToFile().toString();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void testFilePathWithEmptyName() {
		assertThrows(IllegalArgumentException.class, () -> new FilePath("", ""));
	}
}
