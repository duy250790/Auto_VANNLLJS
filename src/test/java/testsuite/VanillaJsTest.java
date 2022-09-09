package testsuite;

import base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;
import page.vannillajs.VanillaJsPage;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedMap;

public class VanillaJsTest extends BaseTest {

    WebDriver driver;
    Actions actions;
    String URL = "https://todomvc.com/examples/vanillajs/";
    VanillaJsPage vanillaJsPage;
    String[] listTask = {"Thuc Day", "Danh Rang","Tap The Duc","Nau An", "AnSang", "Di Lam"};
    ArrayList <String> listTaskActive = null;
    ArrayList <String> listTaskCompleted = null;
    ArrayList <String> listTaskAll = null;

    @BeforeClass
    void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        vanillaJsPage = new VanillaJsPage(driver);
        vanillaJsPage.negative(URL);
    }

    @DataProvider(name = "ListTask")
    Object [][] dataListTask(){
        return new Object[][]{
                {"Thuc Day", "Danh Rang", "AnSang", "Di Lam"},
                {"Lam ve", "Chay bo", "Nau Com", "Don nha","An toi", "Lam Bai"},
        };
    }

    @Test
    void TC01_AddMultiTask(){
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);

        //Step 2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        //Step 3: Check all is default checked
        Assert.assertTrue(vanillaJsPage.checkSelect("All"));

        //Step 4: select Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));

        //Step 5: Check view list task tab Active
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());

        //Step 6: select tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));

        //Step 7: Check view list task tab Completed
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());
    }

    @Test
    void TC02_CompleteFirstTask() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);

        //Step 1.2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        //Step 2: Complete first task
        String firstTask = vanillaJsPage.getlistTaskAll().get(0);
        vanillaJsPage.completeTask(firstTask);

        //verify view task complete in tab ALL
        Assert.assertEquals(vanillaJsPage.getTaskComplete_TabALL(),vanillaJsPage.getListTaskCompleted());

        //Step 4: select Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));

        //Step 5: Check view list task tab Active
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());

        //Step 6: select tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));

        //Step 7: Check view list task tab Completed
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @Test
    void TC03_CompleteLastTask() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);

        //Step 1.2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        //Step 2: Complete first task
        String firstTask = vanillaJsPage.getlistTaskAll().get(vanillaJsPage.getlistTaskAll().size()-1);
        vanillaJsPage.completeTask(firstTask);

        //verify view task complete
        Assert.assertEquals(vanillaJsPage.getTaskComplete_TabALL(),vanillaJsPage.getListTaskCompleted());

        //Step 4: select Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));

        //Step 5: Check view list task tab Active
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());

        //Step 6: select tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));

        //Step 7: Check view list task tab Completed
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @Test
    void TC04_CompleteMultiTask() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);

        //Step 1.2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        // check clear complete button do not display
        Assert.assertFalse(vanillaJsPage.isDisplayClearComplete());

        //Step 2: Complete first and last task
        listTaskAll = vanillaJsPage.getlistTaskAll();
        String lastTask = listTaskAll.get(listTaskAll.size()-1);
        String firstTask = listTaskAll.get(0);

        vanillaJsPage.completeTask(firstTask);
        vanillaJsPage.completeTask(lastTask);

        //verify view task complete
        Assert.assertEquals(vanillaJsPage.getTaskComplete_TabALL(),vanillaJsPage.getListTaskCompleted());

        //verify ClearComplete is displayed
        Assert.assertTrue(vanillaJsPage.isDisplayClearComplete());

        //Step 4: select Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());

        //Step 5: select tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @Test
    void TC05_RemoveTaskTodo_TabALL() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);
        listTaskAll = vanillaJsPage.getlistTaskAll();

        //Remove task in mid
        String taskRemoved = listTaskAll.get(listTaskAll.size()/2-1);
        vanillaJsPage.removeTask(taskRemoved);

        //Remove task first
        taskRemoved = listTaskAll.get(0);
        vanillaJsPage.removeTask(taskRemoved);

        //Remove last task
        taskRemoved = listTaskAll.get(listTaskAll.size()-1);
        vanillaJsPage.removeTask(taskRemoved);

        //Verify task in tab ALL
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getlistTaskAll());

        //Click tab Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());

        //Click tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @Test
    void TC06_RemoveTaskTodo_TabActive() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);
        listTaskAll = vanillaJsPage.getlistTaskAll();

        //Step 2: Click tab Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());

        //Step 3: Remove task in mid
        String taskRemoved = listTaskAll.get(listTaskAll.size()/2-1);
        vanillaJsPage.removeTask(taskRemoved);

        //Step 4: Remove task first
        taskRemoved = listTaskAll.get(0);
        vanillaJsPage.removeTask(taskRemoved);

        //Step 5:Remove last task
        taskRemoved = listTaskAll.get(listTaskAll.size()-1);
        vanillaJsPage.removeTask(taskRemoved);

        //Step 6: Verify view task after remove
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());


        //Step 7: Select tab ALL
        vanillaJsPage.selectTab("All");
        Assert.assertTrue(vanillaJsPage.checkSelect("All"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getlistTaskAll());

        //Step 8: Click tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

        //Step 9: Click tab Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskActive());
    }

    @Test
    void TC07_RemoveAllTaskComplete() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);
        listTaskActive = vanillaJsPage.getListTaskActive();

        //Step 1.2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        // check clear complete button do not display
        Assert.assertFalse(vanillaJsPage.isDisplayClearComplete());

        //Step 2: Complete first task
        String lastTask = listTaskActive.get(listTaskActive.size()-1);
        String firstTask = listTaskActive.get(0);
        String midTask = listTaskActive.get(listTaskActive.size()/2);

        vanillaJsPage.completeTask(firstTask);
        vanillaJsPage.completeTask(midTask);
        vanillaJsPage.completeTask(lastTask);

        //get list complete
        listTaskCompleted = vanillaJsPage.getListTaskCompleted();


        //verify view task complete
        Assert.assertEquals(vanillaJsPage.getListTaskCompleted(),listTaskCompleted);

        //verify ClearComplete is displayed
        Assert.assertTrue(vanillaJsPage.isDisplayClearComplete());

        //Remove all list task complete
        vanillaJsPage.removeTaskcomplete();


        listTaskActive = vanillaJsPage.getListTaskActive();
        Assert.assertEquals(vanillaJsPage.getTodoList(),listTaskActive);

        //Click tab Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));

        //Verify list task in tab Active
        Assert.assertEquals(vanillaJsPage.getTodoList(),listTaskActive);

        //Click tab Complete
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @Test
    void TC08_RemoveNotAllTaskComplete() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);
        listTaskAll = vanillaJsPage.getListTaskActive();

        //Step 1.2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        // check clear complete button do not display
        Assert.assertFalse(vanillaJsPage.isDisplayClearComplete());

        //Step 2: Complete first task
        String lastTask = listTaskAll.get(listTaskAll.size() - 1);
        String firstTask = listTaskAll.get(0);
        String midTask = listTaskAll.get(listTaskAll.size() / 2);

        vanillaJsPage.completeTask(firstTask);
        vanillaJsPage.completeTask(midTask);
        vanillaJsPage.completeTask(lastTask);

         //verify ClearComplete is displayed
        Assert.assertTrue(vanillaJsPage.isDisplayClearComplete());

        //Remove a some task complete
        vanillaJsPage.removeTask(lastTask);
        vanillaJsPage.removeTask(firstTask);

        //Verify task in tab ALL
        Assert.assertEquals(vanillaJsPage.getlistTaskAll(),vanillaJsPage.getTodoList());

        //Click tab Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));
        Assert.assertEquals(vanillaJsPage.getListTaskActive(),vanillaJsPage.getTodoList());

        //Click tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @Test
    void TC09_RemoveBothTaskTodoAndComplete() {
        //Step 1: input multi task
        vanillaJsPage.inputMultiTask(listTask);
        listTaskAll = vanillaJsPage.getListTaskActive();

        //Step 1.2: check view task beforce added
        Assert.assertTrue(vanillaJsPage.getTodoList().equals(Arrays.asList(listTask)));

        // check clear complete button do not display
        Assert.assertFalse(vanillaJsPage.isDisplayClearComplete());

        //Step 2: Complete first task
        String lastTask = listTaskAll.get(listTaskAll.size() - 1);
        String firstTask = listTaskAll.get(0);
        String midTask = listTaskAll.get(listTaskAll.size() / 2);

        vanillaJsPage.completeTask(firstTask);
        vanillaJsPage.completeTask(midTask);

        //verify ClearComplete is displayed
        Assert.assertTrue(vanillaJsPage.isDisplayClearComplete());

        //Remove a some task complete
        vanillaJsPage.removeTask(lastTask);
        vanillaJsPage.removeTask(midTask);
        vanillaJsPage.removeTask(firstTask);

        //Verify task in tab ALL
        Assert.assertEquals(vanillaJsPage.getlistTaskAll(),vanillaJsPage.getTodoList());

        //Click tab Active
        vanillaJsPage.selectTab("Active");
        Assert.assertTrue(vanillaJsPage.checkSelect("Active"));
        Assert.assertEquals(vanillaJsPage.getListTaskActive(),vanillaJsPage.getTodoList());

        //Click tab Completed
        vanillaJsPage.selectTab("Completed");
        Assert.assertTrue(vanillaJsPage.checkSelect("Completed"));
        Assert.assertEquals(vanillaJsPage.getTodoList(),vanillaJsPage.getListTaskCompleted());

    }

    @AfterClass
    void teardown(){
        driver.quit();
    }

}
