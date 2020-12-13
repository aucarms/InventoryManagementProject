package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author carmenau
 */

/**
 * Product Class
 * Object stores data for all Products
 * Contains methods associated with the Product
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int productID;
    private String productName;
    private double productPrice = 0.0;
    private int productStock = 0;
    private int productMinItems;
    private int productMaxItems;


    public Product(int productID, String productName, double productPrice, int productStock, int productMinItems, int productMaxItems) {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productMinItems = productMinItems;
        this.productMaxItems = productMaxItems;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public void setProductMinItems(int productMinItems) {
        this.productMinItems = productMinItems;
    }

    public void setProductMaxItems(int productMaxItems) {
        this.productMaxItems = productMaxItems;
    }

    public int getProductID() {
        return this.productID;
    }

    public String getProductName() {
        return this.productName;
    }

    public double getPrice() {
        return this.productPrice;
    }

    public int getProductStock() {
        return this.productStock;
    }

    public int getMinItems() {
        return this.productMinItems;
    }

    public int getMaxItems() {
        return this.productMaxItems;
    }

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);

    }

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }

}
