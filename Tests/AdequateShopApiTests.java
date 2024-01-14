package Tests;

import api.GetRequests;
import api.PostRequests;
import helpers.UniqueEmailGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class AdequateShopApiTests {
    private static String email;
    private static String password;
    private static String name;

    @BeforeTest
    public static void credentials() {
        name = "Peter";
        password = "222233";
    }



    @Test
    public static void RegisterLoginAndGetUser() throws IOException {

        PostRequests postRequests = new PostRequests();
        GetRequests getRequests = new GetRequests();
        //Generate unique email address and registration user
        UniqueEmailGenerator uniqueemail = new UniqueEmailGenerator();
        email = uniqueemail.getUniqueRandomEmail();
        postRequests.createAccount(name,email,password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));
        //Login user
        postRequests.login(email, password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));
        String token = postRequests.getAccessToken();
        String id = postRequests.getUserId();
        //Get User
        getRequests.getUser(id,token);
        Assert.assertTrue(getRequests.getResponseCode ().contains("200"));
        Assert.assertTrue(getRequests.getAuthMessage().contains("success"));

    }

}
