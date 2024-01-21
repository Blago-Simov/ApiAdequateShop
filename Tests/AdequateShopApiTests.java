package Tests;

import api.GetRequests;
import api.PostRequests;
import helpers.UniqueEmailGenerator;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class AdequateShopApiTests {
    private static String password;
    private static String name;

    private static String email;

    @BeforeTest
    public static void credentials() {
        name = "Peter";
        password = "222233";
    }



    @Test
    public static void SuccessfulUserRegistration() throws IOException {

        PostRequests postRequests = new PostRequests();
        //Generate unique email address and registration user
        UniqueEmailGenerator uniqueEmail = new UniqueEmailGenerator();
        String email = uniqueEmail.getUniqueRandomEmail();
        postRequests.createAccount(name, email,password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));


    }

    @Test
    public static  void SuccessfulRegistrationAndLogin() throws IOException {
        PostRequests postRequests = new PostRequests();
        UniqueEmailGenerator uniqueEmail = new UniqueEmailGenerator();
        String email = uniqueEmail.getUniqueRandomEmail();
        postRequests.createAccount(name, email,password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));
        //Login user
        postRequests.login(email, password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));
    }
    @Test
    public  static  void SuccessfulLoginAndGetUser() throws IOException {
        PostRequests postRequests = new PostRequests();
        GetRequests getRequests = new GetRequests();
        //Generate unique email address and registration user
        UniqueEmailGenerator uniqueEmail = new UniqueEmailGenerator();
        String email = uniqueEmail.getUniqueRandomEmail();
        postRequests.createAccount(name, email,password);
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
        Assert.assertTrue(getRequests.getAuthMessage().contains(name));
    }

    @Test
    public static void invalidPassword() throws IOException {

        PostRequests postRequests = new PostRequests();
        //Generate unique email address and registration user
        UniqueEmailGenerator uniqueEmail = new UniqueEmailGenerator();
        String email = uniqueEmail.getUniqueRandomEmail();
        postRequests.createAccount(name, email,password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));
        //Login user with invalid password
        postRequests.login(email, "212345");
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("invalid"));
    }

    @Test
    public static  void invalidEmail() throws IOException {
        PostRequests postRequests = new PostRequests();
        //Generate unique email address and registration user
        UniqueEmailGenerator uniqueEmail = new UniqueEmailGenerator();
        String email = uniqueEmail.getUniqueRandomEmail();
        postRequests.createAccount(name, email,password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("success"));
        //Login user with invalid password
        postRequests.login("123@", password);
        Assert.assertTrue(postRequests.getResponseCode().contains("200"));
        Assert.assertTrue(postRequests.getAuthMessage().contains("invalid"));

    }


}
