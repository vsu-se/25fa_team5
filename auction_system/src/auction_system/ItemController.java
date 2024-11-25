package auction_system;

public class ItemController {
	private ItemManager itemManager;
	
	public ItemController(ItemManager itemManager) {
		this.itemManager = itemManager;
	}
	
	public Item createItem(String name, User user) {
		int id = itemManager.generateItemID();
		return new Item(id, name, user);
	}
}
