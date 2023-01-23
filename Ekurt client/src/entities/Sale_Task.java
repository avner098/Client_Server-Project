package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * The Sale_Task class represents a task in a sales management system. 
 * It contains properties for the task's id, region, percentage, description, product, and status. 
 * It also contains a ComboBox for closing a task.
 * @author matan turjeman
 *
 */
public class Sale_Task {
	private final  SimpleStringProperty id;
	private final  SimpleStringProperty region;
	private final  SimpleStringProperty percentage;
	private final  SimpleStringProperty description;
	private final  SimpleStringProperty Product;
	private final  SimpleStringProperty status;
	private final  SimpleStringProperty Open_Task;
	  /**
	   * Constructor for creating a new Sale_Task object
	 * @param id
	 * @param region
	 * @param percentage
	 * @param description
	 * @param status
	 * @param product
	 * @param data
	 */
	public Sale_Task(String id, String region, String percentage, String description, String status,String product, String open_Task) {
	        this.id = new SimpleStringProperty(id);
	        this.region = new SimpleStringProperty(region);
	        this.percentage = new SimpleStringProperty(percentage);
	        this.description = new SimpleStringProperty(description);
	        this.Product= new SimpleStringProperty(product);
	        this.status = new SimpleStringProperty(status);
	        this.Open_Task =new SimpleStringProperty(open_Task);
	    }

	 /**
     * Get the id of the Sale_Task.
     * 
     * @return 
     */
    public String getId() {
        return id.get();
    }

    /**
     * Set the id of the Sale_Task.
     * 
     * @param id 
     */
    public void setId(String id) {
        this.id.set(id);
    }

    /**
     * Get the region of the Sale_Task.
     * 
     * @return 
     */
    public String getRegion() {
        return region.get();
    }

    /**
     * Set the region of the Sale_Task.
     * 
     * @param region 
     */
    public void setRegion(String region) {
        this.region.set(region);
    }

    /**
     * Get the percentage of the Sale_Task.
     * 
     * @return 
     */
    public String getPercentage() {
        return percentage.get();
    }

    /**
     * Set the percentage of the Sale_Task.
     * 
     * @param percentage 
     */
    public void setPercentage(String percentage) {
        this.percentage.set(percentage);
    }


	    /**
	     * Get the Description of the Sale_Task.
	     * @return
	     */
	    public String getDescription() {
	        return description.get();
	    }

	    /**
	     *  Set the Description of the Sale_Task.
	     * @param description
	     */
	    public void setDescription(String description) {
	        this.description.set(description);
	    }
	    /**
	     * Get the Product of the Sale_Task.
	     * @return
	     */
	    public String getProduct() {
	        return Product.get();
	    }

	    /**
	     * Set the Product of the Sale_Task.
	     * @param product
	     */
	    public void setProduct(String product) {
	        this.Product.set(product);
	    }



	    /**
	     * Get the Status of the Sale_Task.
	     * @return
	     */
	    public String getStatus() {
	        return status.get();
	    }

	    /**
	     * Set the Status of the Sale_Task.
	     * @param status
	     */
	    public void setStatus(String status) {
	        this.status.set(status);
	    }
	    /**
	     * Get the open_Task of the Sale_Task.
	     * @return
	     */
	    public String getOpen_Task() {
	        return Open_Task.get();
	    }

	    /**
	     * Set the open_Task of the Sale_Task.
	     * @param status
	     */
	    public void setOpen_Task(String open_Task) {
	        this.Open_Task.set(open_Task);
	    }
}
