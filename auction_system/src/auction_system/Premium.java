package auction_system;

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
