package View_Controller;


import java.net.URL;
import java.util.ResourceBundle;

import Model.OutSourced;
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
public class ModifyPartController implements Initializable {
    Inventory inv;
    Part selectedPart;
    int indexSelectedPart;

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
    private Text partCompanyNameID;
    @FXML
    private Text unableToSaveForm;
    @FXML
    private Button cancelModifyPart;
    @FXML
    private Button saveModifyPart;

    private ToggleGroup inHouseOutSourcedToggle;

    /**
     * initializes modify part controller
     *
     * @param indexSelectedPart the index of the array where the selected part is located
     * @param selectedPart the part object selected by user
     * @param inv the inventory object
     */
    public ModifyPartController(int indexSelectedPart, Part selectedPart, Inventory inv) {
        this.inv = inv;
        this.selectedPart = selectedPart;
        this.indexSelectedPart = indexSelectedPart;

    }


    /**
     * Initializes the form with the part's data
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getSavedData();
        inHouseOutSourcedToggle = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(inHouseOutSourcedToggle);
        this.outSourcedRadioButton.setToggleGroup(inHouseOutSourcedToggle);
    }

    /**
     * gets all saved data of the part
     */
    public void getSavedData() {
        partID.setText(String.valueOf(this.selectedPart.getId()));
        partName.setText(this.selectedPart.getName());
        partInventory.setText(String.valueOf(this.selectedPart.getStock()));
        partCost.setText(String.valueOf(this.selectedPart.getPrice()));
        partMaxInv.setText((String.valueOf(this.selectedPart.getMax())));
        partMinInv.setText((String.valueOf(this.selectedPart.getMin())));
        if (this.selectedPart instanceof InHouse) {
            partMachineIdCompanyName.setText((String.valueOf(((InHouse) this.selectedPart).getMachineID())));
            inHouseRadioButton.setSelected(true);
        } else if (this.selectedPart instanceof OutSourced) {
            partMachineIdCompanyName.setText(((OutSourced) this.selectedPart).getCompanyName());
            outSourcedRadioButton.setSelected(true);
        }
    }

    /**
     * Saves the modified part information.
     * Redirects the user back to the main form upon clicking the save button
     *
     * @param event redirects user to main form
     * @throws IOException
     */
    public void saveButtonPushed(Event event) throws IOException {
        if (inHouseRadioButton.isSelected()) {
            if (isNumeric(this.partName.getText().trim()) == true || isNumeric(this.partCost.getText().trim()) == false || isNumeric(this.partInventory.getText().trim()) == false || isNumeric(this.partMinInv.getText().trim()) == false || isNumeric(this.partMaxInv.getText().trim()) == false || isNumeric(this.partMachineIdCompanyName.getText().trim()) == false) {
                dataErrorAlert();
            } else {
                int new_partId = Integer.valueOf(partID.getText());
                String new_partName = partName.getText();
                int new_partInventory = Integer.valueOf(partInventory.getText());
                double new_partCost = Double.valueOf(partCost.getText());
                int new_partMaxInv = Integer.valueOf(partMaxInv.getText());
                int new_partMinInv = Integer.valueOf(partMinInv.getText());
                int new_machineID = Integer.valueOf(partMachineIdCompanyName.getText());
                if (new_partMinInv > new_partMaxInv) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Minimum inventory should be less than maximum inventory");
                    alert.show();
                    return;
                } else {
                    inv.updatePart(indexSelectedPart, new InHouse(new_partId, new_partName, new_partCost, new_partInventory, new_partMaxInv, new_partMinInv, new_machineID));
                    loadMainForm(event);
                }
            }
        } else if (outSourcedRadioButton.isSelected()) {
            if (isNumeric(this.partName.getText().trim()) == true || isNumeric(this.partCost.getText().trim()) == false || isNumeric(this.partInventory.getText().trim()) == false || isNumeric(this.partMinInv.getText().trim()) == false || isNumeric(this.partMaxInv.getText().trim()) == false || isNumeric(this.partMachineIdCompanyName.getText().trim()) == true) {
                dataErrorAlert();
            } else {
                int new_partId = Integer.valueOf(partID.getText());
                String new_partName = partName.getText();
                int new_partInventory = Integer.valueOf(partInventory.getText());
                double new_partCost = Double.valueOf(partCost.getText());
                int new_partMaxInv = Integer.valueOf(partMaxInv.getText());
                int new_partMinInv = Integer.valueOf(partMinInv.getText());
                String new_companyName = partMachineIdCompanyName.getText();
                if (new_partMinInv > new_partMaxInv) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Minimum inventory should be less than maximum inventory");
                    alert.show();
                    return;
                } else {
                    inv.updatePart(indexSelectedPart, new OutSourced(new_partId, new_partName, new_partCost, new_partInventory, new_partMaxInv, new_partMinInv, new_companyName));
                    loadMainForm(event);
                }
            }
        }
    }


    /**
     *  checks if OutSourced radio button is selcted
     */
    public void modifyPartOutSourcedRadioButtonSelected()
    {
        if (outSourcedRadioButton.isSelected()) {
            this.partCompanyNameID.setText("Company Name");

        }
    }

    /**
     * checks if In-House radio button is selected
     */
    public void modifyPartInHouseRadioButtonSelected()
    {
        if (inHouseRadioButton.isSelected()) {
            this.partCompanyNameID.setText("Machine ID");

        }
    }

    /**
     * Cancels modifying part.
     * Redirects user back to the Main Form.
     * @param event redirects user to the Main Form.
     * @throws IOException
     */
    public void cancelModifyPart(ActionEvent event) throws  IOException{
        loadMainForm(event);

    }
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
     * Generates an information alert.
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
     * Sets the stage window as Main Form
     * @param event loads the Main Form
     * @throws IOException
     */
    public void loadMainForm(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainForm.fxml"));
        MainFormController controller = new MainFormController(inv);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);


        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
