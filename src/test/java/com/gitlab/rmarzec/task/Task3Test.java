package com.gitlab.rmarzec.task;

import com.gitlab.rmarzec.framework.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class Task3Test {

    @Test
    public void Task3Test(){
        DriverFactory driverFactory = new DriverFactory();
        WebDriver webDriver = driverFactory.initDriver();
        
    }
}
