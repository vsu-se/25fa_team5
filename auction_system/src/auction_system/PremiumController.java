package auction_system;

public class PremiumController {
	private Premium premium;

	public PremiumController() {
		this.premium = new Premium(0.0);
	}

	public double getBuyerPremium() {
		return this.premium.getBuyerPremium();
	}

	public void setBuyerPremium(double premium) {
		// validation if needed
		if (premium < 0 || premium > 100) {
			throw new IllegalArgumentException("Premium must be between 0 and 100");
		}
		this.premium.setBuyerPremium(premium);
	}
}
