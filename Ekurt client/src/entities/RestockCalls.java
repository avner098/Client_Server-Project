package entities;

/**
 * this class receives the information for the restoke calls the regional
 * manager need to send to the operation worker
 * 
 * @author
 *
 */
public class RestockCalls {

	/**
	 * restoke number
	 */
	private int restockNum;
	/**
	 * machine name
	 */
	private String machineName;
	/**
	 * region
	 */
	private String region;
	/**
	 * low bar
	 */
	private String low_bar;
	/**
	 * list of product to restoke
	 */
	private String ProductsToRestock;
	/**
	 * call status
	 */
	private String status;

	/**
	 * Constructor
	 * 
	 * @param RestockNum
	 * @param machineName
	 * @param region
	 * @param low_bar
	 * @param productsToRestock
	 * @param status
	 */
	public RestockCalls(String RestockNum, String machineName, String region, String low_bar, String productsToRestock,
			String status) {
		this.restockNum = Integer.parseInt(RestockNum);
		this.machineName = machineName;
		this.region = region;
		this.low_bar = low_bar;
		this.ProductsToRestock = productsToRestock;
		this.status = status;
	}

	/**
	 * getter for restoke number
	 * 
	 * @return
	 */
	public int getRestockNum() {
		return restockNum;
	}

	/**
	 * setter for restoke number
	 * 
	 * @param restockNum
	 */
	public void setRestockNum(int restockNum) {
		this.restockNum = restockNum;
	}

	/**
	 * getter for machine Name
	 * 
	 * @return
	 */
	public String getMachineName() {
		return machineName;
	}

	/**
	 * setter for machine Name
	 * 
	 * @param machineName
	 */
	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	/**
	 * getter for region
	 * 
	 * @return
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * setter for region
	 * 
	 * @param region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * getter for low_bar
	 * 
	 * @return
	 */
	public String getLow_bar() {
		return low_bar;
	}

	/**
	 * setter for low_bar
	 * 
	 * @param low_bar
	 */
	public void setLow_bar(String low_bar) {
		this.low_bar = low_bar;
	}

	/**
	 * getter for Products To Restock
	 * 
	 * @return
	 */
	public String getProductsToRestock() {
		return ProductsToRestock;
	}

	/**
	 * setter for Products To Restock
	 * 
	 * @param productsToRestock
	 */
	public void setProductsToRestock(String productsToRestock) {
		this.ProductsToRestock = productsToRestock;
	}

	/**
	 * getter for status
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * setter for status
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
