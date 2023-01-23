package entities;

/**
 *This class receives the information for each item selected by the customer
	The class is implemented with a singleton method.
 *
 */
public class ShoppingCart {
	
	/**
	 *set the amount of each product to 0. 
	 */
	private int cola = 0; 
	private int sprite = 0;
	private int fanta = 0;
	private int orange = 0;
	private int bamba = 0;
	private int nature = 0;
	private int waffle = 0;
	private int doritos = 0;
	private int price = 0;
	

	/**
	 * Physical or remote order.
	 */
	private String machineStatus = null;  
	/**
	 * the order number
	 */
	private String orderNumber; 
	/**
	 * the machine name.
	 */
	private String machine; 
	/**
	 * false if there is no current active order
	 */
	private boolean exist = false;  
	/**
	 * instance of the class
	 */
	private static ShoppingCart instance = null; 
	
	/**
	 * Private constructor to prevent external instantiation
	 */
	private ShoppingCart() {
	}

	
	/**
	 * Public static method to get the instance of the singleton
	 * 
	 * @return instance of the singleton
	 */
	public static ShoppingCart getInstance() {
		if (instance == null) {
			instance = new ShoppingCart();
		}
		return instance;
	}

	/**
	 * set all product to 0
	 */
	public void emptycart() {
		cola = 0;
		sprite = 0;
		fanta = 0;
		orange = 0;
		bamba = 0;
		nature = 0;
		waffle = 0;
		doritos = 0;
		price = 0;
		exist = false;
		try {
			if(!machineStatus.equals("pysical")) {
				machine=null;
			}
		}catch(Exception e) {
			machine=null;
		}
		
	}
	  /**
	* Return the order number
	*/
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * Set the order number
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * Return the machine name
	 */
	public String getMachine() {
		return machine;
	}

	/**
	 * Set the machine name
	 */
	
	public void setMachine(String machine) {
		this.machine = machine;
	}
	
	/**
	 * Return true if the order is exist and false if not
	 */
	public boolean isExist() {
		return exist;
	}

	/**
	 * Set true if the order is exist and false if not
	 */
	public void setExist(boolean exist) {
		this.exist = exist;
	}


	/**
	 * return the amount of the product cola
	 */
	public int getCola() {
		return cola;
	}
	
	/**
	 *  Set the amount of the product cola
	 */
	public void setCola(int amount) {
		this.cola += amount;
	}

	/**
	 * Return the amount of the product sprite
	 */
	public int getSprite() {
		return sprite;
	}


	/**
	 * Set the amount of the product sprite
	 */
	public void setSprite(int amount) {
		this.sprite += amount;
	}

	/**
	 * Return the amount of the product fanta
	 */
	public int getFanta() {
		return fanta;
	}

	/**
	 * Set the amount of the product fanta
	 */
	public void setFanta(int amount) {
		this.fanta += amount;
	}

	/**
	 * Return the amount of the product orangeJuice
	 */
	public int getOrange() {
		return orange;
	}

	
	/**
	 * Set the amount of the product orangeJuice
	 */
	public void setOrange(int amount) {
		this.orange += amount;
	}

	/**
	 * Return the amount of the product bamba
	 */
	public int getBamba() {
		return bamba;
	}

	/**
	 * Set the amount of the product bamba
	 */
	public void setBamba(int amount) {
		this.bamba += amount;
	}

	/**
	 * Return the amount of the product natureVlley
	 */
	public int getNature() {
		return nature;
	}

	/**
	 * Set the amount of the product natureVlley
	 */
	public void setNature(int amount) {
		this.nature += amount;
	}

	/**
	 * Return the amount of the product waffle
	 */
	public int getWaffle() {
		return waffle;
	}

	/**
	 * Set the amount of the product waffle
	 */
	public void setWaffle(int amount) {
		this.waffle += amount;
	}

	/**
	 * Return the amount of the product doritos
	 */
	public int getDoritos() {
		return doritos;
	}

	/**
	 * Set the amount of the product doritos
	 */
	public void setDoritos(int amount) {
		this.doritos += amount;
	}

	/**
	 * Return the price of the order
	 */
	public int getPrice() {
		return price;
	}

	/**
	 * Set the price of the order
	 */
	public void setPrice(int price1) {
		this.price += price1;
	}
	
	/**
	 * Set the machine's status(physical or remote)
	 */
	public void setMachineStatus(String machineStatus) {
		this.machineStatus = machineStatus;
	}
	
	/**
	 * Return the machine's status(physical or remote)
	 */
	public String getMachineStatus() {
		return machineStatus;
	}

}
