package Model;


/**
 *
 * @author carmenau
 */

/**
 * In-House Class inherits methods from the Part Class
 * Object to store data for In-House Parts
 */
public class InHouse extends Part {

    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);

    }

    /**
     * sets the machine ID
     * @param machineID Part's machine Id
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * gets the machine ID
     * @return Part machine ID
     */
    public int getMachineID(){
        return machineID;
    }


}

