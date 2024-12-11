package auction_system;

import javafx.scene.control.TextArea;

public class ReportController {
    private AuctionManager auctionManager;
    private PremiumController premiumController;
    private CommissionController commissionController;

    public ReportController(AuctionManager auctionManager, PremiumController premiumController, CommissionController commissionController) {
        this.auctionManager = auctionManager;
        this.premiumController = premiumController;
        this.commissionController = commissionController;
    }

    public void generateBuyerReport(String username, TextArea buyersReportArea) {
        Report report = new Report();
        buyersReportArea.setText(report.generateBuyerReport(auctionManager, username, premiumController.getBuyerPremium()));
    }

    public void generateSellerReport(String username, TextArea sellersReportArea) {
        Report report = new Report();
        sellersReportArea.setText(report.generateSellerReport(auctionManager, username, commissionController.getSellerCommission()));
    }
}
