package pt.iade.gardenmarket.appgarden.models.views;

public interface PurchasedAdSummaryView extends AdSummaryView {
    int getTransactionId();
    String getPurchaseDate();
}
