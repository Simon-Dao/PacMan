package Main_Package;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author Simon Dao
 */
public class Control 
{    
    private Player pacMan = new Player();
    private Map m;
    private int velocity;
    
       
    /**
     * 
     * @return 
     */
    
       
    
    public void setSceneControls(Scene scene, Stage primaryStage)
    {
           
         scene.addEventFilter(KeyEvent.KEY_PRESSED, keys ->
        {
            if(keys.getCode() == KeyCode.ESCAPE)
            {
                primaryStage.close();
                System.exit(0);
            }   
        });
         
        
    }
}
