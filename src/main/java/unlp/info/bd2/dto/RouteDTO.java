package unlp.info.bd2.dto;

public class RouteDTO {
    private String routeName;
    private Long purchaseCount;
    private Double averagePrice;

    public RouteDTO(String routeName, Long purchaseCount, Double averagePrice) {
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