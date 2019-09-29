package stepdefinitions;

import com.jpm.ApiHelper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import models.UserPostCommentModel;
import models.UserPostModel;
import models.UserProfileModel;
import org.assertj.core.api.SoftAssertions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserPostsSteps extends ApiHelper {
    private UserPostModel userPostResponse;
    private UserPostModel[] userPosts;
    private UserPostCommentModel userPostCommentResponse;

    @Given("^a user has landed on our website$")
    public void a_user_has_landed_on_our_website() {
        Response r = givenConfig().expect().statusCode(200).when().get("/posts");
        userPosts = gson().fromJson((r.asString()), UserPostModel[].class);
    }

    @Then("^the user should be able to view existing posts$")
    public void the_user_should_be_able_to_view_existing_posts() {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(userPosts.length).as("There should be at least 1 user post on the site").isGreaterThanOrEqualTo(1);
        for (UserPostModel userPost : userPosts) {
            softly.assertThat(userPost.getId()).as("id should be a number and greater than 0").isNotNull().isNotNegative();
            softly.assertThat(userPost.getUserId()).as("userId should be a number and greater than 0").isNotNull().isNotNegative();
            softly.assertThat(userPost.getTitle()).as("Post title should not be empty").isNotNull().isNotEmpty();
            softly.assertThat(userPost.getBody()).as("Post body should not be empty").isNotNull().isNotEmpty();
        }
        softly.assertAll();
    }

    @Then("^the user should be able to view existing comments$")
    public void the_user_should_be_able_to_view_existing_comments() {
        Response r = givenConfig().expect().statusCode(200).when().get("/comments");
        UserPostCommentModel[] userPostComments = gson().fromJson((r.asString()), UserPostCommentModel[].class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(userPostComments.length).as("There should be at least 1 user comment on the site").isGreaterThanOrEqualTo(1);
        for (UserPostCommentModel userPostComment : userPostComments) {
            softly.assertThat(userPostComment.getId()).as("Comment id should be a number and greater than 0").isNotNull().isNotNegative();
            softly.assertThat(userPostComment.getPostId()).as("Comment postId should be a number and greater than 0").isNotNull().isNotNegative();
            softly.assertThat(userPostComment.getName()).as("Comment name should not be empty").isNotNull().isNotEmpty();
            softly.assertThat(userPostComment.getEmail()).as("Comment email should not be empty").isNotNull().isNotEmpty();
            softly.assertThat(userPostComment.getBody()).as("Comment body should not be empty").isNotNull().isNotEmpty();
        }
        softly.assertAll();
    }

    @Then("^the user should be able to view existing user profiles$")
    public void the_user_should_be_able_to_view_existing_user_profiles() {
        Response r = givenConfig().expect().statusCode(200).when().get("/users");
        UserProfileModel[] userProfiles = gson().fromJson((r.asString()), UserProfileModel[].class);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(userProfiles.length).as("There should be at least 1 user comment on the site").isGreaterThanOrEqualTo(1);
        for (UserProfileModel userProfile : userProfiles) {
            softly.assertThat(userProfile.getId()).as("User id should be a number and greater than 0").isNotNull().isNotNegative();
            softly.assertThat(userProfile.getEmail()).as("User email should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getName()).as("User name should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getPhone()).as("User phone should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getUsername()).as("User username should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getWebsite()).as("User website should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getAddress().getCity()).as("User address city should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getAddress().getStreet()).as("User address street should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getAddress().getZipcode()).as("User address zipcode should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getAddress().getSuite()).as("User address suite should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getAddress().getGeo().getLat()).as("User address geo latitude should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getAddress().getGeo().getLng()).as("User address geo longitude should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getCompany().getName()).as("User company name should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getCompany().getCatchPhrase()).as("User company catch phrase should be a non empty string").isNotNull().isNotEmpty();
            softly.assertThat(userProfile.getCompany().getBs()).as("User company bs should be a non empty string").isNotNull().isNotEmpty();
        }
        softly.assertAll();
    }

    @When("^the user (\\d+) creates a new post with \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_user_userId_creates_a_new_post_with_and(int userId, String postTitle, String postBody) {
        UserPostModel userPostRequestBody = UserPostModel.builder()
                .userId(userId)
                .title(postTitle)
                .body(postBody)
                .build();
        Response r = givenConfig().body(gson().toJson(userPostRequestBody))
                .expect().statusCode(201).log().ifError()
                .when().post("/posts");
        userPostResponse = gson().fromJson((r.asString()), UserPostModel.class);
    }

    @Then("^the post will be saved with correct (\\d+), \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_post_will_be_saved_with_correct_data(int expectedUserId, String expectedPostTitle, String expectedPostBody) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(userPostResponse.getId()).as("Post id should be returned and be positive").isNotNull().isPositive();
        softly.assertThat(userPostResponse.getUserId()).as("Post user should be returned and match the user id that posted it").isNotNull().isPositive().isEqualTo(expectedUserId);
        softly.assertThat(userPostResponse.getTitle()).as("Post title should be returned and match what the user sent").isNotNull().isEqualTo(expectedPostTitle);
        softly.assertThat(userPostResponse.getBody()).as("Post body should be returned and match what the user sent").isNotNull().isEqualTo(expectedPostBody);
        softly.assertAll();
    }

    @When("^a user adds a new comment against (\\d+) with \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\"$")
    public void the_user_adds_a_new_comment_against_with_and(int postId, String name, String email, String body) {
        UserPostCommentModel userPostCommentRequestBody = UserPostCommentModel.builder()
                .postId(postId)
                .name(name)
                .email(email)
                .body(body)
                .build();
        Response r = givenConfig().body(gson().toJson(userPostCommentRequestBody))
                .expect().statusCode(201).log().ifError()
                .when().post("/comments");
        userPostCommentResponse = gson().fromJson((r.asString()), UserPostCommentModel.class);
    }

    @Then("^the post will be saved with correct \"([^\"]*)\", \"([^\"]*)\" and \"([^\"]*)\" against correct (\\d+)$")
    public void the_post_will_be_saved_with_correct_user_email_com_and_against_correct(String expectedName, String expectedEmail, String expectedBody, int expectedPostId) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(userPostCommentResponse.getId()).as("Post comment id should be returned and be positive").isNotNull().isPositive();
        softly.assertThat(userPostCommentResponse.getPostId()).as("Post comment should be returned and match the correct post id").isNotNull().isPositive().isEqualTo(expectedPostId);
        softly.assertThat(userPostCommentResponse.getName()).as("Post comment name should be returned and match what the user sent").isNotNull().isEqualTo(expectedName);
        softly.assertThat(userPostCommentResponse.getEmail()).as("Post comment email should be returned and match what the user sent").isNotNull().isEqualTo(expectedEmail);
        softly.assertThat(userPostCommentResponse.getBody()).as("Post comment body should be returned and match what the user sent").isNotNull().isEqualTo(expectedBody);
        softly.assertAll();
    }

    @Then("^the user can create a new profile successfully using following data$")
    public void userRegistersWithBelowData(List<Map<String, String>> data) {
        UserProfileModel userProfileModel = generateUserProfileModel(data.get(0));

        givenConfig().body(gson().toJson(userProfileModel))
                .expect().statusCode(201).log().all()
                .when().post("/users");
    }

    /**
     * Generates a UserProfileModel from a map
     * @param m data map passed from the step
     * @return UserProfileModel
     */
    private static UserProfileModel generateUserProfileModel(Map<String, String> m) {
        HashMap<String, String> map = new HashMap<>();
        for (String e : m.keySet()) {
            if (m.get(e).equals("")) {
                map.put(e, null);
            } else {
                map.put(e, m.get(e));
            }
        }

        return UserProfileModel.builder()
                .name(map.get("name"))
                .username(map.get("username"))
                .email(map.get("email"))
                .phone(map.get("phone"))
                .website(map.get("website"))
                .address(UserProfileModel.Address.builder()
                        .street(map.get("address.street"))
                        .city(map.get("address.city"))
                        .suite(map.get("address.suite"))
                        .zipcode(map.get("address.zipcode"))
                        .geo(UserProfileModel.Geo.builder()
                                .lat(map.get("address.geo.lat"))
                                .lng(map.get("address.geo.lng"))
                                .build())
                        .build())
                .company(UserProfileModel.Company.builder()
                        .name(map.get("company.name"))
                        .catchPhrase(map.get("company.catchPhrase"))
                        .bs(map.get("company.bs"))
                        .build())
                .build();
    }

}
