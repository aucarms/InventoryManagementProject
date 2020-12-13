package View_Controller;


import java.net.URL;
import java.util.ResourceBundle;

import Model.Product;
import com.sun.tools.javac.util.Name;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import Model.Inventory;
import Model.Part;
import View_Controller.AddPartController;
import View_Controller.MainFormController;
import Model.InHouse;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.util.Random;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.event.Event;

/**
 * FXML Controller class
 *
 * @author carmenau
 */
public class ModifyProductController implements Initializable {
    Inventory inv;
    Product selectedProduct;
    int indexSelectedProduct;
    @FXML private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    private Button cancelModify;
    @FXML
    private Button addPart;
    @FXML
    private Button removeAssociatedPart;
    @FXML
    private Button saveModifiedProduct;
    @FXML
    private TextField searchPartTextField;

    @FXML
    private TextField productId;
    @FXML
    private TextField productName;
    @FXML
    private TextField productInventory;
    @FXML
    private TextField productCost;
    @FXML
    private TextField productMaxInv;
    @FXML
    private TextField productMinInv;

    //configure existing associated part tableview
    @FXML
    private TableView<Part> associatedPartTableView;

    @FXML
    private TableColumn partIdColumn;
    @FXML
    private TableColumn partNameColumn;
    @FXML
    private TableColumn partStockColumn;
    @FXML
    private TableColumn partPriceColumn;
    @FXML
    private ObservableList<Product> productAssocPartsList = FXCollections.observableArrayList();

    //configure all parts in inventory tableview
    @FXML
    private TableView<Part> allPartsTableView;
    @FXML
    private TableColumn allPartIdColumn;
    @FXML
    private TableColumn allPartNameColumn;
    @FXML
    private TableColumn allPartInvColumn;
    @FXML
    private TableColumn allPartPriceColumn;



    /**
     * Initializes the controller class.
     *
     * @param indexSelectedProduct the index of the array where the selected product is located
     * @param selectedProduct the product selected by the user
     * @param inv the inventory object
     *
     */
    public ModifyProductController(int indexSelectedProduct, Product selectedProduct, Inventory inv) {
        this.inv = inv;
        this.selectedProduct = selectedProduct;
        this.indexSelectedProduct = indexSelectedProduct;
        this.associatedParts = selectedProduct.getAllAssociatedParts();

    }

    /**
     * initializes the text fields with existing product data and tableview with part data from inventory
     *
     *
     */
    public void initialize(URL url, ResourceBundle rb) {
        productId.setText(String.valueOf(this.selectedProduct.getProductID()));
        productName.setText(this.selectedProduct.getProductName());
        productInventory.setText(String.valueOf(this.selectedProduct.getProductStock()));
        productCost.setText(String.valueOf(this.selectedProduct.getPrice()));
        productMaxInv.setText((String.valueOf(this.selectedProduct.getMaxItems())));
        productMinInv.setText((String.valueOf(this.selectedProduct.getMinItems())));
        associatedPartTableView.setItems(selectedProduct.getAllAssociatedParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        allPartsTableView.setItems(inv.getAllParts());
        allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /**
     * returns an observable array list of all parts associated with the product the user selected
     * @param selectedProduct the product object user selected
     * @return the list of associated parts
     */

    public ObservableList<Part> getProductAssocPartsList(Product selectedProduct) {
        String productName = selectedProduct.getProductName();
        ObservableList<Part> searchResult = FXCollections.observableArrayList();
        for (Product product : inv.getAllProducts()) {

            if (product.getProductName().contains(productName)) {
                for (Part part : product.getAllAssociatedParts()) {
                    searchResult.add(part);
                }
                return searchResult;
            }

        }
        return null;
    }

    /**
     * Saves the modified product when user clicks the save button.
     * After saving the data, the scene will redirect to the Main Form.
     *
     * @param event redirects the user to the main form
     * @throws IOException
     */
    public void saveModifiedProduct(ActionEvent event) throws IOException {
        int new_id = Integer.valueOf(productId.getText());
        String new_name = productName.getText();
        int new_stock = Integer.valueOf(productInventory.getText());
        double new_price = Double.valueOf(productCost.getText());
        int new_inv_min = Integer.valueOf(productMinInv.getText());
        int new_inv_max = Integer.valueOf(productMaxInv.getText());
        Product product = new Product(new_id, new_name, new_price, new_stock, new_inv_min, new_inv_max);
        for (Part part : associatedParts) {
            product.addAssociatedPart(part);
        }

        inv.updateProduct(indexSelectedProduct, product);
        changeToMainForm(event);
    }

    /**
     *
     * this method removes the selected associated part from the product object
     */
    public void removeSelectedAssociatedPart() {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        if (associatedParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Part was selected");
            alert.show();
            return;
        } else if (associatedParts.contains(selectedPart)) {
            associatedParts.remove(selectedPart);
            associatedPartTableView.refresh();
        }
    }

    /**
     * this method adds the selected part to the product object's associated parts array list
     *
     */
    public void addPartToList(){
        Part selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an associated Part");
            alert.show();
        }
        else {
            associatedParts.add(selectedPart);
            associatedPartTableView.setItems(associatedParts);
            associatedPartTableView.refresh();
        }
        return;

    }

    /**
     * when the user clicks the cancel button
     * this method will redirect the user to the main form.
     * @param event redirects the user to the main form
     * @throws IOException
     */
    public void cancelModifyProduct(ActionEvent event) throws IOException {
        changeToMainForm(event);

    }

    /**
     * Sets the tableview for All Parts (inventory)
     *
     */
    public void getPartsTableView() {
        allPartsTableView.setItems(inv.getAllParts());
    }

    /**
     * Searches Part by Part Name or Part ID
     *
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
                allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                allPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                ObservableList<Part> searchResultPartList = FXCollections.observableArrayList();
                searchResultPartList.add(searchPartIdResults);
                allPartsTableView.setItems(searchResultPartList);
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
                allPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
                allPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                allPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
                allPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
                allPartsTableView.setItems(partNameSearch);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Search Result");
                alert.setHeaderText(null);
                alert.setContentText("Part not found in Inventory.");
                alert.show();
                return;
            }
        }
        allPartsTableView.refresh();


    }


    /**
     *
     * @param event redirects user to Main Form
     * @throws IOException
     */
    public void changeToMainForm(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        MainFormController controller = new MainFormController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        //get the stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    /**
     *  checks if the string is a number
     * @param strNum the string to be checked
     * @return boolean. True (if a number) or false (if not a number)
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


