package unlp.info.bd2.dto;

public class RouteSummaryDTO {
    private String routeName;
    private Long purchaseCount;
    private Double averagePrice;

    public RouteSummaryDTO(String routeName, Long purchaseCount, Double averagePrice) {
        this.routeName = routeName;
        this.purchaseCount = purchaseCount;
        this.averagePrice = averagePrice;
    }

    public String getRouteName() {
        return routeName;
    }

    public Long getPurchaseCount() {
        return purchaseCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }
}