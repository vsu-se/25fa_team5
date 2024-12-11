package auction_system;

import java.util.ArrayList;

public class Report {

    public Report() {

    }

    public String generateSellerReport(AuctionManager auctionManager, String username, double commission) {
        String firstHalf = "";
        String name;
        String price;
        String commissionPercent = "Seller's commission: " + commission + "%\n";
        String shipping;
        double totalWinningBids = 0;
        double totalShippingCost = 0;
        double totalSellersCommissions = 0;
        double totalProfits = 0;
        for(Auction a : auctionManager.getUserSoldAuctions(username)) {
            name = "Item name: " + a.getItem().getName() + "\n";
            price = "Price: $" + String.format("%.2f", calculateCommissionPrice(a, commission)) + "\n";
            shipping = "Shipping: $" + a.getShippingCost() + "\n";
            firstHalf += name + price + commissionPercent + shipping + "\n";
            totalWinningBids += a.getWinningBid().getBidValue();
            totalShippingCost += a.getShippingCost();
            totalSellersCommissions += calculateCommission(a, commission);
        }
        String totalWinningBidsStr = "Total winning bids: $" + String.format("%.2f", totalWinningBids) + "\n";
        String totalShippingCostStr = "Total shipping costs: $" + String.format("%.2f", totalShippingCost) + "\n";
        String totalSellersCommissionsStr = "Total seller's commissions: $" + String.format("%.2f", totalSellersCommissions) + "\n";
        String totalProfitsStr = "Total profits: $" + String.format("%.2f", totalWinningBids - totalSellersCommissions) + "\n";
        String secondHalf = "-----------------------\n" + totalWinningBidsStr + totalShippingCostStr + totalSellersCommissionsStr + totalProfitsStr;
        return firstHalf + secondHalf;
    }

    public String generateBuyerReport(AuctionManager auctionManager, String username, double premium) {
        String firstHalf = "";
        String name;
        String price;
        String premiumPercent = "Buyer's premium: " + premium + "%\n";
        String shipping;
        double totalAmountBought = 0;
        double premiumsPaid = 0;
        double totalShippingCost = 0;
        for(Auction a : auctionManager.getUserWonAuctions(username)) {
            name = "Item name: " + a.getItem().getName() + "\n";
            price = "Price: $" + String.format("%.2f", calculatePrice(a, premium)) + "\n";
            shipping = "Shipping: $" + a.getShippingCost() + "\n";
            firstHalf += name + price + premiumPercent + shipping + "\n";
            premiumsPaid += calculatePremium(a, premium);
            totalAmountBought += Double.parseDouble(price.substring(8));
            totalShippingCost += a.getShippingCost();
        }
        String totalAmountBoughtStr = "Total amount bought: $" + String.format("%.2f", totalAmountBought) + "\n";
        String premiumsPaidStr = "Total buyer's premiums paid: $" + String.format("%.2f", premiumsPaid) + "\n";
        String totalShippingCostStr = "Total shipping cost paid: $" + String.format("%.2f", totalShippingCost) + "\n";
        String secondHalf = "-----------------------\n" + totalAmountBoughtStr + premiumsPaidStr + totalShippingCostStr;
        return firstHalf + secondHalf;
    }

    public double calculatePrice(Auction auction, double premium) {
        double winningBidAmount = auction.getWinningBid().getBidValue();
        double shippingCost = auction.getShippingCost();
        return winningBidAmount + shippingCost + calculatePremium(auction, premium);
    }

    public double calculatePremium(Auction auction, double premium) {
        double winningBidAmount = auction.getWinningBid().getBidValue();
        double shippingCost = auction.getShippingCost();
        double premiumPercent = premium / 100;
        double premiumCost = (winningBidAmount + shippingCost) * premiumPercent;
        return premiumCost;
    }

    public double calculateCommissionPrice(Auction auction, double commission) {
        double winningBidAmount = auction.getWinningBid().getBidValue();
        double shippingCost = auction.getShippingCost();
        return winningBidAmount + shippingCost + calculateCommission(auction, commission);
    }

    public double calculateCommission(Auction auction, double commission) {
        double winningBidAmount = auction.getWinningBid().getBidValue();
        double shippingCost = auction.getShippingCost();
        double commissionPercent = commission / 100;
        double commissionCost = (winningBidAmount + shippingCost) * commissionPercent;
        return commissionCost;
    }

    public String toString() {
        return "";
    }
}
