package auction_system;

public class CommissionController {
	private Commission commission;
	
	public CommissionController() {
		this.commission = new Commission(0.0);
	}
	
	public double getSellerCommission() {
        return this.commission.getSellerCommission();
	}
	
	public void setSellerCommission(double commission) {
		// validation if needed 
		if (commission < 0 || commission > 100 ) {
			throw new IllegalArgumentException("Commission must be between 0 and 100");
		}
		this.commission.setSellerCommission(commission);
	}
}
