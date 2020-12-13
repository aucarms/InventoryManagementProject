package View_Controller;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import java.io.IOException;
import Model.Part;
import Model.Inventory;
import Model.Product;
import javafx.scene.control.Alert;


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
import javafx.collections.ObservableList;

import javax.swing.*;


/**
 * FXML Controller class
 *
 * @author carmenau
 */


public class AddProductController implements Initializable {
    Inventory inv;

    @FXML private Label addProductForm;
    @FXML private Label main;
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outSourcedRadioButton;
    @FXML private TextField productID;
    @FXML private TextField productName;
    @FXML private TextField productInventory;
    @FXML private TextField productCost;
    @FXML private TextField productMaxInv;
    @FXML private TextField productMinInv;
    @FXML private Text unableToSaveForm;
    @FXML private Button cancelAddProductButtonSelected;
    @FXML private Button saveAddProductButtonSelected;
    @FXML private Button removeAssociatedPart;
    @FXML private Button addAssociatedPart;
    @FXML private TextField searchPartTextField;

    // configure part table

    @FXML private TableView<Part> partTableView;
    @FXML private TableView<Part> allAssociatedPartTableView;
    @FXML private ObservableList<Part> partsInventory = FXCollections.observableArrayList();
    @FXML private ObservableList<Part> partsInventorySearch = FXCollections.observableArrayList();
    @FXML private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partStockColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    private Integer id;

    @FXML private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    @FXML private TableColumn<Part, Integer> associatedPartStockColumn;
    @FXML private TableColumn<Part, Double> associatedPartPriceColumn;

    Random random = new Random();

    /**
     * Initializes Add Product Controller using an inventory
     * @param inv inventory object
     */
    public AddProductController (Inventory inv){
        this.inv = inv;
    }



    /**
     * Saves and adds the product object to the inventory
     * @param event redirects user to Main Form
     * @throws IOException
     */
    public void addProduct(ActionEvent event) throws IOException {
        int new_id = random.nextInt(100);
        this.productID.setText(String.valueOf(new_id));
        String new_name = this.productName.getText();
        double new_price = Double.valueOf(this.productCost.getText());
        int new_stock = Integer.valueOf(this.productInventory.getText());
        int new_inv_min = Integer.valueOf(this.productMinInv.getText());
        int new_inv_max = Integer.valueOf(this.productMaxInv.getText());
        Product product = new Product(new_id, new_name, new_price, new_stock, new_inv_min, new_inv_max);
        for (Part part : associatedParts) {
            product.addAssociatedPart(part);
        }
        inv.addProduct(product);
        changeToMainForm(event);
    }

    /**
     * Removes the selected part upon clicking remove associated part
     *
     */
    public void removeSelectedAssociatedPart() {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (associatedParts.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No Part was selected");
            alert.show();
            return;
        } else if (associatedParts.contains(selectedPart)) {
            associatedParts.remove(selectedPart);
            allAssociatedPartTableView.refresh();
        }
    }


    /**
     * Redirects the user to the main form upon clicking Cancel
     * @param event redirects user to the main form
     * @throws IOException
     */
    public void cancelAddProduct(ActionEvent event) throws IOException {
        changeToMainForm(event);

    }

    /**
     * Searches Part by Part Name or Part ID.
     * Shows the Part data if found in inventory
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
     * Adds the selected part to the Product object's associated parts array list
     *
     */
    public void addPartToList(){
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an associated Part");
            alert.show();
        }
        else {
            associatedParts.add(selectedPart);
            allAssociatedPartTableView.setItems(associatedParts);
            allAssociatedPartTableView.refresh();
        }
        return;

    }

    /**
     * Initializes the Part Tableview and Associated Part Tableview
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partTableView.refresh();
        partsInventory.addAll(inv.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.setItems(inv.getAllParts());
        getPartsTableView();
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allAssociatedPartTableView.setItems(associatedParts);
        allAssociatedPartTableView();

    }

    /**
     * Gets all Parts in Inventory
     * Sets the Parts Tableview with all Parts' Data
     */
   public void getPartsTableView() {
        partTableView.setItems(inv.getAllParts());
    }

    /**
     * Sets the Associated Parts Tableview
     */
    public void allAssociatedPartTableView(){

        allAssociatedPartTableView.setItems(associatedParts);
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
