package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *  The Sale_Employee class represents an employee in a sales management system. 
 * It contains properties for the employee's id, region, percentage, description, and product. 
 * It also contains a ComboBox for	sale status.
 * @author matan turjeman
 *
 */
public class Sale_Employee {
	private final  SimpleStringProperty id;
	private final  SimpleStringProperty region;
	private final  SimpleStringProperty percentage;
	private final  SimpleStringProperty description;
	private final SimpleStringProperty Product;
	private ComboBox<String> Status;
	private String Request;

	/**
	 * Constructor for creating a new Sale_Employee object.
	 * @param id
	 * @param region
	 * @param percentage
	 * @param description
	 * @param product
	 * @param data
	 */
	public Sale_Employee(String id, String region, String percentage, String description,String product, ObservableList data,String request) {
	
        this.id = new SimpleStringProperty(id);
        this.region = new SimpleStringProperty(region);
        this.percentage = new SimpleStringProperty(percentage);
        this.description = new SimpleStringProperty(description);
        this.Product=new SimpleStringProperty(product);
		this.Status = new ComboBox(data);
		this.Request=request;
	}

    /**
     * Get the request of the Sale_Employee.
     * @return
     */
    public String getRequest() {
		return Request;
	}

	/**
	 * Set the request of the Sale_Employee.
	 * @param request
	 */
	public void setRequest(String request) {
		Request = request;
	}

	/**
     * Get the id of the Sale_Employee.
     * @return
     */
    public String getId() {
        return id.get();
    }

    /**
     * Set the id of the Sale_Employee.
     * @param id
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Get the region of the Sale_Employee.
     * @return
     */
    public String getRegion() {
        return region.get();
    }

    /**
     *  Set the region of the Sale_Employee.
     * @param region
     */
    public void setRegion(String region) {
        this.region.set(region);
    }



    /**
     * Get the percentage of the Sale_Employee's sales.
     * @return
     */
    public String getPercentage() {
        return percentage.get();
    }

    /**
     * Set the percentage of the Sale_Employee's sales.
     * @param percentage
     */
    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }



    /**
     * Get the description of the Sale_Employee.
     * @return
     */
    public String getDescription() {
        return description.get();
    }

    /**
     * Set the description of the Sale_Employee.
     * @param description
     */
    public void setDescription(String description) {
        this.description.set(description);
    }
    /**
     * Get the product of the Sale_Employee.
     * @return
     */
    public String getProduct() {
        return Product.get();
    }

    /**
     * Set the product of the Sale_Employee.
     * @param product
     */
    public void setProduct(String product) {
        this.Product.set(product);
    }

	/**
	 * Get the status of the Sale_Employee.
	 * @return
	 */
	public ComboBox getStatus() {
		return  Status;
	}

	/**
	 * Set the status of the Sale_Employee.
	 * @param status
	 */
	public void setStatus(String status) {
		this.Status.setValue(status);
	}

}
