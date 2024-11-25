package auction_system;

public class UserController {
	private UserManager userManager;
	
	public UserController(UserManager userManager) {
		this.userManager = userManager;
	}
	
	public User createUser(String name) {
		int id = userManager.generateUserID();
		return new User(id, name);
	}
}
