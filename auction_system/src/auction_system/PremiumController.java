package auction_system;

public class PremiumController {
	private Premium premium;

	public PremiumController() {
		this.premium = new Premium(0.0);
	}

	public double getBuyerPremium() {
		return premium.getBuyerPremium();
	}

	public void setBuyerPremium(String premiumField) {
		try {
			double premiumValue = Double.parseDouble(premiumField);
			if (premiumValue < 0 || premiumValue > 100) {
				throw new IllegalArgumentException("Premium must be between 0 and 100");
			}
			premium.setBuyerPremium(premiumValue);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Please enter a valid numeric value for the premium");
		}
	}
}