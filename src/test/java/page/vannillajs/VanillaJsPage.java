package page.vannillajs;

import base.BaseTest;
import net.bytebuddy.asm.Advice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VanillaJsPage  {
    WebDriver driver;
    Actions actions;
    private ArrayList <String> listTaskActive ;
    private ArrayList <String> listTaskCompleted;
    private ArrayList <String> listTaskAll;

    public ArrayList<String> getListTaskActive() {
        return listTaskActive;
    }

    public ArrayList<String> getListTaskCompleted() {
        return listTaskCompleted;
    }

    public ArrayList<String> getlistTaskAll() {
        return listTaskAll;
    }

    public VanillaJsPage(WebDriver driver) {
        this.driver = driver;
    }

    private By todoTxt = By.xpath("//input[@class = 'new-todo']");
    private By todoList = By.xpath("//ul[@class = 'todo-list']//div//label");
    private By todoCheckbox = By.xpath("//input[@type='checkbox']");
    private By todoComplete = By.xpath("//ul[@class = 'todo-list']//li[@class = 'completed']");
    private By countTodoTxt = By.xpath("//span[@class='todo-count']/strong");
    private String filterLocator = "//ul[@class = 'filters']/li/a[text() = '%s']";

    private String taskView = ("//ul[@class = 'todo-list']//div//label[text()='%s']");
    private String taskLocator = ("//ul[@class = 'todo-list']//div//label[text()='%s']//preceding-sibling::input");
    private By clearCompleteBtn = By.className("clear-completed");
    private String removeLocator = ("//ul[@class = 'todo-list']//label[text()='%s']//following-sibling::button");


    public void negative(String URL){
        driver.navigate().to(URL);
    }

    public void inputAndEnter(String text){
        driver.findElement(todoTxt).sendKeys(text+ Keys.ENTER);
    }

    public String viewInput(){
        return driver.findElement(todoList).getText();
    }

    public void checkedTodo(){
        driver.findElement(todoCheckbox).click();
    }

    public void removeTask ( String task){
        String taskTxt = taskView;
        taskTxt = String.format(taskTxt,task);

        String removeBtn = removeLocator;
        removeBtn = String.format(removeBtn,task);

        actions = new Actions(driver) ;
        actions.moveToElement(driver.findElement(By.xpath(taskTxt))).perform();

        driver.findElement(By.xpath(removeBtn)).click();
        listTaskAll.remove(task);
        if(listTaskCompleted.contains(task)){
            listTaskCompleted.remove(task);
        }else  listTaskActive.remove(task);
    }

    public ArrayList<String> getTaskComplete_TabALL(){
        ArrayList<String> list = new ArrayList<>();
        List<WebElement> elements = driver.findElements(todoComplete);
        elements.forEach(e -> list.add(e.getText()));
        return  list;
    }

    public void completeTask( String taskName){
        String task = taskLocator;
        task = String.format(taskLocator,taskName);
        driver.findElement(By.xpath(task)).click();
        listTaskCompleted.add(taskName);
        listTaskActive.remove(taskName);
    }

    public void selectTab (String tab){
        String selectTab = filterLocator;
        selectTab = String.format(selectTab,tab);
        driver.findElement(By.xpath(selectTab)).click();
    }

    public boolean checkSelect(String value){
        String selectTab = filterLocator;
        selectTab = String.format(selectTab,value);
        if(driver.findElement(By.xpath(selectTab)).getAttribute("class").equalsIgnoreCase("selected")){
            return true;}
        else  return false;
    }

    public void inputMultiTask(String[]  listTask){
        listTaskCompleted = new ArrayList<>();
        listTaskAll = new ArrayList<>();
        listTaskActive = new ArrayList<>();
        for (int i = 0; i < listTask.length; i++){
            driver.findElement(todoTxt).sendKeys(listTask[i]+Keys.ENTER);
            listTaskAll.add(listTask[i]);
            listTaskActive.add(listTask[i]);
        }
    }

    public ArrayList<String> getTodoList(){
        ArrayList<String> list = new ArrayList<>();
        List<WebElement> elements = driver.findElements(todoList);
        elements.forEach(e -> list.add(e.getText()));
        return  list;
    }

    public boolean isDisplayClearComplete(){
        return driver.findElement(clearCompleteBtn).isDisplayed();
    }

    public void removeTaskcomplete(){
        driver.findElement(clearCompleteBtn).click();
        listTaskCompleted = new ArrayList<>();
    }
}
