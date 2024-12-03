package auction_system;

public class CommissionController {
	private Commission commission;

	public CommissionController() {
		this.commission = new Commission(0.0);
	}

	public double getSellerCommission() {
        return this.commission.getSellerCommission();
	}

	public void setSellerCommission(String commissionField) {
		try {
			double commissionValue = Double.parseDouble(commissionField);
			if (commissionValue < 0 || commissionValue > 100) {
				throw new IllegalArgumentException("Commission must be between 0 and 100");
			}
			commission.setSellerCommission(commissionValue);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Please enter a valid numeric value for the commission");
		}
	}
}
