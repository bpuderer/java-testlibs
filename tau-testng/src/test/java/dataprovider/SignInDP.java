package dataprovider;

import org.testng.annotations.DataProvider;

public class SignInDP {

    @DataProvider(name = "login-provider-cls")
    public static Object[][] loginData() {

        Object[][] data = new Object [3][3];

        data [0][0] = "user1"; data[0][1] = "password1"; data[0][2] = true;
        data [1][0] = "user2"; data[1][1] = "password2"; data[1][2] = false;
        data [2][0] = "user3"; data[2][1] = "password3"; data[2][2] = true;

        return data;
    }
}
