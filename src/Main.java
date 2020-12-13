import Model.Inventory;
import View_Controller.MainFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author carmenau
 */
public class Main extends Application {

    @Override
    /** sets primary stage
     *
     */
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/MainForm.fxml"));
        MainFormController controller = new MainFormController(inv);
        loader.setController(controller);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");

        stage.setScene(scene);
        stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
