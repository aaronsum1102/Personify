
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Personify.integration.FileIO;

class FileIOTest {
	private FileIO file;
	private final ByteArrayOutputStream errOut = new ByteArrayOutputStream();

	@BeforeEach
	void setUp() {
		file = new FileIO();
		System.setErr(new PrintStream(errOut));
	}

	@AfterEach
	void tearDown() {
		file = null;
		System.setErr(System.err);
	}

	@Test
	void testReadingFileWithPathExistsInSystem() throws IOException {
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
		Path filePath = Paths.get("src/data", "fileForTesting.txt");
		Files.createFile(filePath, PosixFilePermissions.asFileAttribute(perms));
		int fileContentSize = file.readEachLineOfFile(filePath).size();
		final int CORRECT_CONTENT_SIZE = 0;
		Files.delete(filePath);
		assertEquals(CORRECT_CONTENT_SIZE, fileContentSize);
	}

	@Test
	void testReadingFileWithPathNotExistsInSystem() throws IOException {
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwx------");
		Path filePath = Paths.get("src/data", "fileForTesting.txt");
		Files.createFile(filePath, PosixFilePermissions.asFileAttribute(perms));
		Files.delete(filePath);
		assertThrows(FileNotFoundException.class, () -> file.readEachLineOfFile(filePath));
	}

	@Test
	void testReadingFileWithPathNoReadPermission() throws IOException {
		Set<PosixFilePermission> perms = PosixFilePermissions.fromString("-wx------");
		Path filePath = Paths.get("src/data", "fileForTesting.txt");
		Files.createFile(filePath, PosixFilePermissions.asFileAttribute(perms));
		file.readEachLineOfFile(filePath).size();
		String expectedResult = "Unable to read \"" + filePath.getFileName() + "\".\n";
		Files.delete(filePath);
		assertEquals(expectedResult, errOut.toString());
	}
}
