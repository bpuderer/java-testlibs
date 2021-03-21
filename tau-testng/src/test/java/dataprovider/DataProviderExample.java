package dataprovider;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderExample {

    // @Test(dataProvider = "loginData")
    // @Test(dataProvider = "login-provider")
    @Test(dataProvider = "login-provider-cls", dataProviderClass = SignInDP.class)
    public void login(String email, String password, boolean success) {
        System.out.println(email + password + success);
    }

    // @DataProvider
    @DataProvider(name = "login-provider")
    public Object[][] loginData() {

        Object[][] data = new Object [3][3];

        data [0][0] = "user1"; data[0][1] = "password1"; data[0][2] = true;
        data [1][0] = "user2"; data[1][1] = "password2"; data[1][2] = false;
        data [2][0] = "user3"; data[2][1] = "password3"; data[2][2] = true;

        return data;
    }

}
