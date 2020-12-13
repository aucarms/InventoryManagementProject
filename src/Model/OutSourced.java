package Model;

/**
 * Outsourced Class inherits methods from the Part Class
 * Object to store data for all Outsourced parts
 */
public class OutSourced extends Part {
    private String companyName;

    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        setId(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);

    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName(){
        return companyName;
    }

}

