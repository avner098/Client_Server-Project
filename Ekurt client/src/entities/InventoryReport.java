package entities;

/**
 * @author
 *
 *         this class receives the information for the inventory report
 */
public class InventoryReport {
	// private String cola, sprite, fanta, OrangeJuice, natureValley, waffles,
	// doritos, bamba, dateTime, machine;

	private int cola, sprite, fanta, OrangeJuice, natureValley, waffles, doritos, bamba;

	/**
	 * Constructor
	 * 
	 * @param Cola
	 * @param Sprite
	 * @param Fanta
	 * @param orangeJuice
	 * @param NatureValley
	 * @param Waffles
	 * @param Doritos
	 * @param Bamba
	 */
	public InventoryReport(String Cola, String Sprite, String Fanta, String orangeJuice, String NatureValley,
			String Waffles, String Doritos, String Bamba) {
		this.cola = Integer.parseInt(Cola);
		this.sprite = Integer.parseInt(Sprite);
		this.fanta = Integer.parseInt(Fanta);
		this.OrangeJuice = Integer.parseInt(orangeJuice);
		this.natureValley = Integer.parseInt(NatureValley);
		this.waffles = Integer.parseInt(Waffles);
		this.doritos = Integer.parseInt(Doritos);
		this.bamba = Integer.parseInt(Bamba);

	}

	/**
	 * getter for cola
	 * 
	 * @return cola
	 */
	public int getCola() {
		return cola;
	}

	/**
	 * add cola to the inventory counter
	 * 
	 * @param newcola
	 */
	public void addCola(int newcola) {
		this.cola += newcola;
	}

	/**
	 * getter for sprite
	 * 
	 * @return sprite
	 */
	public int getSprite() {
		return sprite;
	}

	/**
	 * add sprite to the inventory counter
	 * 
	 * @param newsprite
	 */
	public void addSprite(int newsprite) {
		this.sprite += newsprite;
	}

	/**
	 * getter for fanta
	 * 
	 * @return fanta
	 */
	public int getFanta() {
		return fanta;
	}

	/**
	 * add fanta to the inventory counter
	 * 
	 * @param newfanta
	 */
	public void addFanta(int newfanta) {
		this.fanta += newfanta;
	}

	/**
	 * getter for Orange Juice
	 * 
	 * @return range Juice
	 */
	public int getOrangeJuice() {
		return OrangeJuice;
	}

	/**
	 * add Orange Juice to the inventory counter
	 * 
	 * @param neworangeJuice
	 */
	public void addOrangeJuice(int neworangeJuice) {
		OrangeJuice += neworangeJuice;
	}

	/**
	 * getter for natureValley
	 * 
	 * @return natureValley
	 */
	public int getNatureValley() {
		return natureValley;
	}

	/**
	 * add natureValley to the inventory counter
	 * 
	 * @param newnatureValley
	 */
	public void addNatureValley(int newnatureValley) {
		this.natureValley += newnatureValley;
	}

	/**
	 * getter for waffles
	 * 
	 * @return waffles
	 */
	public int getWaffles() {
		return waffles;
	}

	/**
	 * add waffles to the inventory counter
	 * 
	 * @param newwaffles
	 */
	public void addWaffles(int newwaffles) {
		this.waffles += newwaffles;
	}

	/**
	 * getter for doritos
	 * 
	 * @return doritos
	 */
	public int getDoritos() {
		return doritos;
	}

	/**
	 * add doritos to the inventory counter
	 * 
	 * @param newdoritos
	 */
	public void addDoritos(int newdoritos) {
		this.doritos += newdoritos;
	}

	/**
	 * getter for bamba
	 * 
	 * @return bamba
	 */
	public int getBamba() {
		return bamba;
	}

	/**
	 * add bamba to the inventory counter
	 * 
	 * @param newbamba
	 */
	public void addBamba(int newbamba) {
		this.bamba += newbamba;
	}

}
