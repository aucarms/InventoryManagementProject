package View_Controller;


import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Inventory;
import Model.Part;
import Model.Product;



import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

import javax.swing.*;


/**
 *
 * @author carmenau
 */
public class MainFormController implements Initializable {
    Inventory inv;


    //items from products pane
    @FXML
    private Button addProduct;
    @FXML
    private Button modifyProduct;
    @FXML
    private Button deleteProduct;

    // items from parts pane
    @FXML
    private Button addPart;
    @FXML
    private Button modifyPart;
    @FXML
    private Button deletePart;
    @FXML
    private Label MainForm;
    @FXML
    private Button exitApplication;
    @FXML
    private TextField searchPartTextField;
    @FXML
    private TextField searchProductTextField;

    // configure part table
    @FXML private TableView<Part> partTableView;
    @FXML private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    @FXML private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();

    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    private Integer id;

    // configure product table
    @FXML private TableView<Product> productTableView;
    @FXML private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    @FXML private ObservableList<Product> productInventorySearch = FXCollections.observableArrayList();
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productStockColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;

    /**
     *  initializes the main form controller using an inventory
     * @param inv inventory object
     */
    public MainFormController(Inventory inv) {

        this.inv = inv;

    }
    @Override
    /**
     * initializes the Part Tableview with all Parts in inventory
     * initializes the Product Tableview with all Products in inventory
     */
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.refresh();
        partsInventory.addAll(inv.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(inv.getAllParts());


        productTableView.refresh();
        productInventory.addAll(inv.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        productTableView.setItems(inv.getAllProducts());


    }

    /**
     * Gets all parts in the inventory and sets tableview
     */

    public void getPartsTableView() {
        partTableView.setItems(inv.getAllParts());
    }

    /**
     * Gets all products in the inventory and sets tableview
     */
    public void getProductsTableView(){
        productTableView.setItems(inv.getAllProducts());
    }




    /**
     *  Changes the screen to Add Product Form upon clicking
     *  the add button
     *
     * @param event changes the screen to Add Product Form
     * @throws IOException
     */
    public void changeScreenAddProductButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProductForm.fxml"));
        AddProductController controller = new AddProductController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        //get the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();

    }


    /**
     * Changes the stage to Modify Product Form upon clicking the Modify button
     * @param event sets stage as the Modify Product Form
     * @throws IOException
     */
    public void changeScreenModifyProductButtonPushed(ActionEvent event) throws IOException {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (productInventory.isEmpty() || selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Product selected.");
            alert.show();
            return;
        } else if (inv.getAllProducts().contains(selectedProduct)) {
            int indexSelectedProduct = inv.getAllProducts().indexOf(selectedProduct);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProductForm.fxml"));
            ModifyProductController controller = new ModifyProductController(indexSelectedProduct, selectedProduct, inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //get the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }
    }


    /**
     * Changes the stage to Add Part Form upon clicking
     * the add button
     * @param event changes the stage to Add Part Form
     * @throws IOException
     */
    public void changeScreenAddPartButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPartForm.fxml"));
        AddPartController controller = new AddPartController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        //get the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();

    }

    /**
     * Changes the screen to Modify Part Form
     * upon clicking the Modify button
     * @param event changes the stage to Modify Part Form
     * @throws IOException
     */
    public void changeScreenModifyPartButtonPushed(ActionEvent event) throws IOException {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (partsInventory.isEmpty() || selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Part was selected");
            alert.show();
            return;
        } else if (inv.getAllParts().contains(selectedPart)) {
            int indexSelectedPart = inv.getAllParts().indexOf(selectedPart);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPartForm.fxml"));
            ModifyPartController controller = new ModifyPartController(indexSelectedPart, selectedPart, inv);
            loader.setController(controller);
            Parent root = loader.load();
            Scene scene = new Scene(root);

            //get the stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();

        }
    }

    /**
     * Exits the Inventory System upon clicking the exit button
     * @param event exits the application
     */
    public void exitButtonPushed(ActionEvent event) {
        Platform.exit();
    }



    /**
     * Deletes the selected Part object from inventory upon clicking the delete button
     */
    public void deletePartButton() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (partsInventory.isEmpty() || selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Part was selected");
            alert.show();
            return;
        }
        else if (inv.getAllParts().contains(selectedPart)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("You are about to remove the selected Part from Inventory.");
            alert.setContentText("Proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                inv.deletePart(selectedPart);
                partsInventory.remove(selectedPart);
                partTableView.refresh();
            }
            else {
                return;
            }
        }

    }

    /**
     * Deletes the Selected Product object from inventory upon clicking the delete button
     *
     */
    public void deleteProductButton() {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (productInventory.isEmpty() || selectedProduct == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Product selected");
            alert.show();
            return;
        }
        if (selectedProduct.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Unable to delete product from Inventory. Please remove all associated parts from the selected Product.");
            alert.show();
            return;
        }
        else if (inv.getAllProducts().contains(selectedProduct)) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("You are about to remove the selected Product from Inventory.");
            alert.setContentText("Proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                inv.deleteProduct((selectedProduct));
                productInventory.remove(selectedProduct);
                productTableView.refresh();
            } else {
                return;
            }
        }
    }

    /**
     * Searches the inputted Part Name or Part ID
     * returns the Part data if it  found in the inventory.
     */
    public void searchPartTableView() {
        String searchPart = searchPartTextField.getText();
        if (searchPartTextField.getText().isEmpty()) {
            getPartsTableView();
        }
        else if (isNumeric(searchPart)){
            int searchPartId = Integer.valueOf(searchPart);
            Part searchPartIdResults = inv.lookupPart(searchPartId);
            if (searchPartIdResults != null) {
                partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                ObservableList<Part> searchResultPartList = FXCollections.observableArrayList();
                searchResultPartList.add(searchPartIdResults);
                partTableView.setItems(searchResultPartList);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("Part not found in Inventory.");
                alert.show();
                return;
            }

        }
        else {
            ObservableList<Part> partNameSearch = inv.lookupPart(searchPart);
            if (partNameSearch.size() > 0) {

                partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                partTableView.setItems(partNameSearch);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("Part not found in Inventory.");
                alert.show();
                return;
            }
        }
        partTableView.refresh();


    }

    /**
     * Searches the inputted Product Name or Product ID.
     * Returns the product data if found in inventory
     */
    public void searchProductTableView() {
        String searchProduct = searchProductTextField.getText();
        if (searchProductTextField.getText().isEmpty()) {
            getProductsTableView();
        }
        else if (isNumeric(searchProduct)){
            int searchProductId = Integer.valueOf(searchProduct);
            Product searchProductIdResults = inv.lookupProduct(searchProductId);
            if (searchProductIdResults != null) {
                productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
                productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
                productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
                productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
                ObservableList<Product> searchResultProductList = FXCollections.observableArrayList();
                searchResultProductList.add(searchProductIdResults);
                productTableView.setItems(searchResultProductList);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("Product not found in Inventory.");
                alert.show();
                return;
            }

        }
        else {
            ObservableList<Product> productNameSearch = inv.lookupProduct(searchProduct);
            if (productNameSearch.size() > 0) {
                productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
                productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
                productStockColumn.setCellValueFactory(new PropertyValueFactory<>("productStock"));
                productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
                productTableView.setItems(productNameSearch);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("Product not found in Inventory.");
                alert.show();
                return;
            }
        }
        partTableView.refresh();


    }

    /**
     * Checks if the string is a number
     * @param strNum string to be checked
     * @return boolean. True if a number False if not a number
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

}



