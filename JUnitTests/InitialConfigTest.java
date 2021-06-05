import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import sample.*;

import static org.testfx.api.FxAssert.verifyThat;

public class InitialConfigTest extends ApplicationTest {

    @Override
    public void start(Stage primaryStage) {
        (new Main()).start(primaryStage);
    }

    //Verifies the functionality of the Exit button

    @Test
    public void testExit() {
        verifyThat("Exit Game", NodeMatchers.isNotNull());
        clickOn("Exit Game");
    }

    //Verifies that buttons corresponding with transitions work properly

    @Test
    public void adithTransitions() {
        verifyThat("Start Game", NodeMatchers.isNotNull());
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        verifyThat("Next", NodeMatchers.isNotNull());
        clickOn("Next");
    }

    //Checks to see if proper amount of gold loads on Easy

    @Test
    public void adithEasy() {
        clickOn("Start Game");
        write("Name");
        clickOn("Easy");
        clickOn("Gun");
        clickOn("Next");
        verifyThat("100 G", NodeMatchers.isNotNull());
    }

    //Checks to see if proper amount of gold loads on Medium

    @Test
    public void adithMedium() {
        clickOn("Start Game");
        write("Name");
        clickOn("Medium");
        clickOn("Gun");
        clickOn("Next");
        verifyThat("50 G", NodeMatchers.isNotNull());
    }

    //Checks to see if proper amount of gold loads on Hard

    @Test
    public void adithHard() {
        clickOn("Start Game");
        write("Name");
        clickOn("Hard");
        clickOn("Gun");
        clickOn("Next");
        verifyThat("0 G", NodeMatchers.isNotNull());
    }

    // check to see if welcome screen has loaded the proper elements
    @Test
    public void testWelcomeScreen() {
        verifyThat("Start Game", NodeMatchers.isNotNull());
        verifyThat("Welcome to the Dungeon Crawler game!", NodeMatchers.isNotNull());
    }

    // test to make sure once start button pressed, configuration screen opens correctly
    @Test
    public void testWelcomeToConfigure() {
        clickOn("Start Game");
        verifyThat("Enter your name", NodeMatchers.isNotNull());
        verifyThat("Choose a difficulty level", NodeMatchers.isNotNull());
        verifyThat("Choose a weapon", NodeMatchers.isNotNull());
        clickOn("Easy");
        clickOn("Medium");
        clickOn("Hard");
        clickOn("Knife");
        clickOn("Gun");
        clickOn("Axe");
    }

    // test that game does not allow blank names to be used
    @Test
    public void testBlankName() {
        clickOn("Start Game");
        clickOn("Next");
        verifyThat("Name cannot be empty or only whitespace characters", NodeMatchers.isNotNull());
    }

    // check that game does not allow white space names
    @Test
    public void testWhiteSpaceName() {
        clickOn("Start Game");
        write(" ");
        clickOn("Next");
        verifyThat("Name cannot be empty or only whitespace characters", NodeMatchers.isNotNull());
    }

    //Not entering and picking anything
    @Test
    public void louis1() {
        clickOn("Start Game");
        clickOn("Next");
        verifyThat("Name cannot be empty or only whitespace characters", NodeMatchers.isNotNull());
        verifyThat("Must choose difficulty before moving on", NodeMatchers.isNotNull());
        verifyThat("Must choose weapon before moving on", NodeMatchers.isNotNull());
    }

    //To test if the text will change after picking and typing, when everything is filled,
    //the scene is changed and showing the correct amount of money based on the chosen difficulty
    @Test
    public void louis2() {
        clickOn("Start Game");
        write("text");
        clickOn("Next");
        verifyThat("Enter your name", NodeMatchers.isNotNull());
        verifyThat("Must choose difficulty before moving on", NodeMatchers.isNotNull());
        verifyThat("Must choose weapon before moving on", NodeMatchers.isNotNull());
        clickOn("Hard");
        clickOn("Next");
        verifyThat("Enter your name", NodeMatchers.isNotNull());
        verifyThat("Choose a difficulty level", NodeMatchers.isNotNull());
        verifyThat("Must choose weapon before moving on", NodeMatchers.isNotNull());
        clickOn("Gun");
        clickOn("Next");
        verifyThat("0 G", NodeMatchers.isNotNull());
    }
}
