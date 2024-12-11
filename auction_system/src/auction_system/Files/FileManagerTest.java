package auction_system;

import javafx.scene.control.TextArea;
import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
	private FileManager fileManager;
	
	@BeforeEach
	void setUp() {
		fileManager = new FileManager();
		new File("registered_user_data.txt").delete();
		new File("bid_info.txt").delete();
		new File("admin_data.txt").delete();
		new File("credentials.txt").delete();
	}

	@AfterEach
	void reset() {
		new File("registered_user_data.txt").delete();
		new File("bid_info.txt").delete();
		new File("admin_data.txt").delete();
		new File("credentials.txt").delete();
	}

	@Test
	void testSaveRegisteredUserDataSuccess() {
		TextArea itemListArea = new TextArea("Item1\nItem2");
		fileManager.saveRegisteredUserData("testUser", itemListArea);

		File file = new File("registered_user_data.txt");
		assertTrue(file.exists());

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			assertEquals("testUser: Item1", reader.readLine());
			assertEquals("testUser: Item2", reader.readLine());
		} catch (IOException e) {
			fail("IOException should not occur.");
		}
	}

	@Test
	void testSaveBidInfoSuccess() {
		Bid bid = new Bid(1, 100.0, LocalDateTime.now(), new User("testUser"));
		fileManager.saveBidInfo(bid);

		File file = new File("bid_info.txt");
		assertTrue(file.exists());

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			assertEquals(bid.toString(), reader.readLine());
		} catch (IOException e) {
			fail("IOException should not occur.");
		}
	}

	@Test
	void testSaveBidInfoFail() {
		assertThrows(Exception.class, () -> fileManager.saveBidInfo(null));
	}

	@Test
	void testLoadRegisteredUserDataSuccess() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("registered_user_data.txt"))) {
			writer.write("testUser: Item1\n");
			writer.write("testUser: Item2\n");
		} catch (IOException e) {
			fail("IOException should not occur.");
		}

		TextArea itemListArea = new TextArea();
		fileManager.loadRegisteredUserData("testUser", itemListArea);

		assertEquals("Item1\nItem2\n", itemListArea.getText());
	}

	@Test
	void testLoadRegisteredUserDataFail() {
		TextArea itemListArea = new TextArea();
		fileManager.loadRegisteredUserData("nonexistentUser", itemListArea);

		assertEquals("", itemListArea.getText());
	}

	@Test
	void testSaveAdminDataSuccess() {
		List<String> categories = Arrays.asList("Category1", "Category2");
		fileManager.saveAdminData(categories, 10.0, 5.0);

		File file = new File("admin_data.txt");
		assertTrue(file.exists());

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			assertEquals("Categories", reader.readLine());
			assertEquals("- Category1", reader.readLine());
			assertEquals("- Category2", reader.readLine());
		} catch (IOException e) {
			fail("IOException should not occur.");
		}
	}

	@Test
	void testSaveAdminDataFail() {
		assertThrows(Exception.class, () -> fileManager.saveAdminData(null, -1, -1));
	}

	@Test
	void testIsUsernameTakenSuccess() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt"))) {
			writer.write("testUser:password:userType\n");
		} catch (IOException e) {
			fail("IOException should not occur.");
		}

		assertTrue(FileManager.isUsernameTaken("testUser"));
	}

	@Test
	void testIsUsernameTakenFail() {
		assertFalse(FileManager.isUsernameTaken("nonexistentUser"));
	}

	@Test
	void testSaveCredentialsSuccess() {
		fileManager.saveCredentials("testUser", "password", "userType");

		File file = new File("credentials.txt");
		assertTrue(file.exists());

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			assertEquals("testUser:password:userType", reader.readLine());
		} catch (IOException e) {
			fail("IOException should not occur.");
		}
	}

	@Test
	void testSaveCredentialsFail() {
		assertThrows(Exception.class, () -> fileManager.saveCredentials(null, null, null));
	}

	@Test
	void testLoadCredentialsSuccess() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("credentials.txt"))) {
			writer.write("testUser:password:userType\n");
		} catch (IOException e) {
			fail("IOException should not occur.");
		}

		assertTrue(fileManager.loadCredentials("testUser", "password", "userType"));
	}

	@Test
	void testLoadCredentialsFail() {
		assertFalse(fileManager.loadCredentials("nonexistentUser", "wrongPassword", "wrongType"));
	}

}
