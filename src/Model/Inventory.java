package Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author carmenau
 */

/**
 * Inventory Class
 * stores all parts and products in an observable array list
 */
public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public Inventory(){
        allParts = FXCollections.observableArrayList();
        allProducts =FXCollections.observableArrayList();


    }





    public void addPart(Part newPart) {
        this.allParts.add(newPart);
    }

    public void addProduct(Product newProduct) {

        this.allProducts.add(newProduct);
    }

    public Part lookupPart(int partID) {
        for (Part part : allParts) {
            if (part.getId() == partID) {
                return part;
            }

        }
        return null;
    }

    public Product lookupProduct(int productID) {
        for (Product product : allProducts) {
            if (product.getProductID() == productID) {
                return product;
            }

        }
        return null;
    }

    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResult = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                searchResult.add(part);
            }
            return searchResult;
        }
        return null;
    }

    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResult = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getProductName().contains(productName)) {
                searchResult.add(product);
            }
            return searchResult;
        }
        return null;
    }

    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public ObservableList<Part> getAllParts() {


        return allParts;
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
