package View_Controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class AddPartController implements Initializable {
    Inventory inv;
    @FXML
    private Label addPartForm;
    @FXML
    private Label main;
    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outSourcedRadioButton;
    @FXML
    private TextField partID;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInventory;
    @FXML
    private TextField partCost;
    @FXML
    private TextField partMaxInv;
    @FXML
    private TextField partMinInv;
    @FXML
    private TextField partMachineIdCompanyName;
    @FXML
    private Text partMachineId;
    @FXML
    private Text partCompanyName;
    @FXML
    private Text unableToSaveForm;
    @FXML
    private Button cancelAddPartButtonSelected;


    private ToggleGroup inHouseOutSourcedToggle;


    Random random = new Random();

    /**
     * initializes Add Part Controller using an inventory
     * @param inv inventory object
     */
    public AddPartController(Inventory inv) {
        this.inv = inv;
    }

    /**
     * Create new In-House Part Object
     *
     */
    public void addInHouse() {
        int new_id = random.nextInt(100);
        this.partID.setText(String.valueOf(new_id));
        String new_name = this.partName.getText();
        double new_price = Double.valueOf(this.partCost.getText());
        int new_stock = Integer.valueOf(this.partInventory.getText());
        int new_inv_min = Integer.valueOf(this.partMinInv.getText());
        int new_inv_max = Integer.valueOf(this.partMaxInv.getText());
        Integer new_machineID = Integer.valueOf(this.partMachineIdCompanyName.getText());
        inv.addPart(new InHouse(new_id, new_name, new_price, new_stock, new_inv_min, new_inv_max, new_machineID));

    }

    /**
     * Create new OutSourced Part Object
     */
    public void addOutSourced() {
        int new_id = random.nextInt(100);
        this.partID.setText(String.valueOf(new_id));
        String new_name = this.partName.getText();
        double new_price = Double.valueOf(this.partCost.getText());
        int new_stock = Integer.valueOf(this.partInventory.getText());
        int new_inv_min = Integer.valueOf(this.partMinInv.getText());
        int new_inv_max = Integer.valueOf(this.partMaxInv.getText());
        String new_companyName = this.partMachineIdCompanyName.getText();
        inv.addPart(new OutSourced(new_id, new_name, new_price, new_stock, new_inv_min, new_inv_max, new_companyName));

    }

    /**
     * Checks if Outsourced radio button is selected.
     * Sets the Text to Company Name
     */
    public void addPartOutSourcedRadioButtonSelected() {
        if (outSourcedRadioButton.isSelected()) {
            this.partMachineId.setText("Company Name");

        }
    }

    /**
     *  Checks if In-House radio button is selected.
     *  Sets the Text to Machine ID
     */
    public void addPartInHouseRadioButtonSelected() {
        if (inHouseRadioButton.isSelected()) {
            this.partMachineId.setText("Machine ID");

        }
    }

    /**
     * Checks if user input contains valid data
     * Creates the Part Object
     * Redirects the user to Main Form upon clicking the Save Button
     *
     * @param event redirects user to Main Form.
     * @throws IOException
     */
    public void addPartFormSaveButtonSelected(ActionEvent event) throws IOException {
        if (inHouseRadioButton.isSelected() == false && outSourcedRadioButton.isSelected() == false || this.partName.getText().trim().length() == 0 || this.partCost.getText().trim().length() == 0 || this.partMinInv.getText().trim().length() == 0 || this.partMaxInv.getText().trim().length() == 0 || this.partMachineIdCompanyName.getText().trim().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error: Incomplete field(s).");
            alert.show();
            return;
        } else if (inHouseRadioButton.isSelected()) {
            if (isNumeric(this.partName.getText().trim()) == true || isNumeric(this.partCost.getText().trim()) == false || isNumeric(this.partInventory.getText().trim()) == false || isNumeric(this.partMinInv.getText().trim()) == false || isNumeric(this.partMaxInv.getText().trim()) == false || isNumeric(this.partMachineIdCompanyName.getText().trim()) == false) {
                dataErrorAlert();
            } else {
                int new_id = random.nextInt(100);
                this.partID.setText(String.valueOf(new_id));
                String new_name = this.partName.getText();
                double new_price = Double.valueOf(this.partCost.getText());
                int new_stock = Integer.valueOf(this.partInventory.getText());
                int new_inv_min = Integer.valueOf(this.partMinInv.getText());
                int new_inv_max = Integer.valueOf(this.partMaxInv.getText());
                Integer new_machineID = Integer.valueOf(this.partMachineIdCompanyName.getText());

                if (new_inv_min > new_inv_max) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Minimum inventory should be less than maximum inventory");
                    alert.show();
                    return;
                } else {
                    addInHouse();
                    changeToMainForm(event);
                }
            }
        } else if (outSourcedRadioButton.isSelected()) {
            if (isNumeric(this.partName.getText().trim()) == true || isNumeric(this.partCost.getText().trim()) == false || isNumeric(this.partInventory.getText().trim()) == false || isNumeric(this.partMinInv.getText().trim()) == false || isNumeric(this.partMaxInv.getText().trim()) == false || isNumeric(this.partMachineIdCompanyName.getText().trim()) == true) {
                dataErrorAlert();
            } else {
                int new_id = random.nextInt(100);
                this.partID.setText(String.valueOf(new_id));
                String new_name = this.partName.getText();
                double new_price = Double.valueOf(this.partCost.getText());
                int new_stock = Integer.valueOf(this.partInventory.getText());
                int new_inv_min = Integer.valueOf(this.partMinInv.getText());
                int new_inv_max = Integer.valueOf(this.partMaxInv.getText());
                String new_company_name = this.partMachineIdCompanyName.getText();
                if (new_inv_min > new_inv_max) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Minimum inventory should be less than maximum inventory");
                    alert.show();
                    return;
                }
                 else {
                    addOutSourced();
                    changeToMainForm(event);
                }
            }
        }
    }


    /**
     * Changes the stage to Main Form
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
     * Redirects the user to Main Form upon clicking the Cancel button
     * @param event redirects use to Main Form
     * @throws IOException
     */
    public void cancelAddPartButtonSelected(ActionEvent event) throws  IOException{
        changeToMainForm(event);
    }

    /**
     * Checks if the String is a number
     * @param strNum the string to be checked
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

    /**
     * Generates an information Alert
     */
    public void dataErrorAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("User input data error. Please ensure all user input has the correct values");
        alert.show();
        return;
    }


    /**
     * Initializes the toggle group for in-house and outsourced radio button
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partID.setText("");
        inHouseOutSourcedToggle = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(inHouseOutSourcedToggle);
        this.outSourcedRadioButton.setToggleGroup(inHouseOutSourcedToggle);
    }

}
