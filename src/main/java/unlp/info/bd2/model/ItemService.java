package unlp.info.bd2.model;


public class ItemService {

    Long id;

    private int quantity;

    private Purchase purchase;

    private Serv service;

    public ItemService(int quantity, Serv managedService, Purchase purchase) {
    	this.quantity = quantity;
    	this.service = managedService;
    	this.purchase = purchase;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
