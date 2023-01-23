package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 * This class represents a sale. It contains information about the sale such as the id, region, percentage, description, product, and status.
 * It also contains a ComboBox for sending a request to an employee.
 * @author matan turjeman
 *
 */
public class Sale {
	private final  SimpleStringProperty id;
	private final  SimpleStringProperty region;
	private final  SimpleStringProperty percentage;
	private final  SimpleStringProperty description;
	private final  SimpleStringProperty Product;
	private final  SimpleStringProperty status;
	private ComboBox<String> Send_Request_To_Employee;
	  /**
	   * Constructor for creating a new Sale object.
	 * @param id
	 * @param region
	 * @param percentage
	 * @param description
	 * @param status
	 * @param product
	 * @param data
	 */
	public Sale(String id, String region, String percentage, String description, String status,String product, ObservableList<String> data) {
	        this.id = new SimpleStringProperty(id);
	        this.region = new SimpleStringProperty(region);
	        this.percentage = new SimpleStringProperty(percentage);
	        this.description = new SimpleStringProperty(description);
	        this.Product= new SimpleStringProperty(product);
	        this.status = new SimpleStringProperty(status);
	        this.Send_Request_To_Employee = new ComboBox<String>(data);
	    }

	    /**
	     *  Get the id of the sale.
	     * @return
	     */
	    public String getId() {
	        return id.get();
	    }

	    /**
	     * Set the id of the sale.
	     * @param id
	     */
	    public void setId(String id) {
	        this.id.set(id);
	    }

	    /**
	     * Get the region of the sale.
	     * @return
	     */
	    public String getRegion() {
	        return region.get();
	    }

	    /**
	     * Set the region of the sale.
	     * @param region
	     */
	    public void setRegion(String region) {
	        this.region.set(region);
	    }



	    /**
	     * Get the percentage of the sale.
	     * @return
	     */
	    public String getPercentage() {
	        return percentage.get();
	    }

	    /**
	     * Set the percentage of the sale.
	     * @param percentage
	     */
	    public void setPercentage(String percentage) {
	        this.percentage.set(percentage);
	    }



	    /**
	     * Get the description of the sale.
	     * @return
	     */
	    public String getDescription() {
	        return description.get();
	    }

	    /**
	     * Set the description of the sale.
	     * @param description
	     */
	    public void setDescription(String description) {
	        this.description.set(description);
	    }
	    /**
	     * Get the product of the sale.
	     * @return
	     */
	    public String getProduct() {
	        return Product.get();
	    }

	    /**
	     * Set the product of the sale.
	     * @param product
	     */
	    public void setProduct(String product) {
	        this.Product.set(product);
	    }



	    /**
	     * Get the status of the sale.
	     * @return
	     */
	    public String getStatus() {
	        return status.get();
	    }

	    /**
	     * Set the Status of the sale.
	     * @param status
	     */
	    public void setStatus(String status) {
	        this.status.set(status);
	    }
		/**
		 * get the Combobox options of requestToEmployee
		 * @return
		 */
		public ComboBox<String> getSend_Request_To_Employee() {
			return  Send_Request_To_Employee;
		}

		/**
		 * set the Combobox options of requestToEmployee
		 * @param Send_Request_To_Employee
		 */
		public void setSend_Request_To_Employee(String Send_Request_To_Employee) {
			this.Send_Request_To_Employee.setValue(Send_Request_To_Employee);
		}



}
