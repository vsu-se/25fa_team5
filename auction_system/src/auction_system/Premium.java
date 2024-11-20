package auction_system;

import javafx.scene.control.TextField;

public class Premium {
	private double buyerPremium;
	
	public Premium(double buyerPremium) {
		this.buyerPremium = buyerPremium;
	}
	
	public double getBuyerPremium() {
		return buyerPremium;
	}

	public void setBuyerPremium(double buyerPremium) {
		this.buyerPremium = buyerPremium;
	}
}
