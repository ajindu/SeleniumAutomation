package pages;

import helper.Endpoint;
import helper.Utility;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import java.io.File;
import java.util.List;

import static helper.Models.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ApiPage {

    String setUrl, global_id, customId;
    Response res;

    public void prepareUrlFor(String url){
        switch (url){
            case "GET_LIST_USERS":
                setUrl = Endpoint.GET_LIST_USERS;
                break;
            case "GET_USER_BY_ID":
                setUrl = Endpoint.GET_USER_BY_ID;
                break;
            case "CREATE_NEW_USER":
                setUrl = Endpoint.CREATE_NEW_USER;
                break;
            case "SEARCH_USER":
                setUrl = Endpoint.SEARCH_USER;
                break;
            case "DELETE_USER":
                setUrl = Endpoint.DELETE_USER;
                break;
            default:
                System.out.println("input right url");
        }
    }
    public void hitApiGetListUsers(){
        res = getListUsers(setUrl);
    }
    public void validationStatusCodeIsEquals(int status_code){
        assertThat(res.statusCode()).isEqualTo(status_code);
    }

    public void validationResponseBodyGetListUsers(){
        List<Object> id = res.jsonPath().getList("id");
        List<Object> name = res.jsonPath().getList("name");
        List<Object> email = res.jsonPath().getList("email");
        List<Object> gender = res.jsonPath().getList("gender");
        List<Object> status = res.jsonPath().getList("status");

        assertThat(id.get(0)).isNotNull();
        assertThat(name.get(0)).isNotNull();
        assertThat(email.get(0)).isNotNull();
        assertThat(gender.get(0)).isIn("female", "male");
        assertThat(status.get(0)).isIn("active", "inactive");
    }
    public void validationResponseJsonWithJSONSchema(String filename){
        File JSONFile = Utility.getJSONSchemaFile(filename);
        res.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(JSONFile));
    }
    public void hitApiPostCreateUser(){
        res = postCreateUser(setUrl);
//        System.out.println(res.getBody().asString());
    }

    public void hitApiPostCreateUserInvalidStatus(){
        res = postCreateUserInvalidStatus(setUrl);
//        System.out.println(res.getBody().asString());
    }

    public void validationResponseBodyCreateUser(){
        JsonPath jsonPathEvaluator = res.jsonPath();
        Integer id = jsonPathEvaluator.get("id");
        String name = jsonPathEvaluator.get("name");
        String email = jsonPathEvaluator.get("email");
        String gender = jsonPathEvaluator.get("gender");
        String status = jsonPathEvaluator.get("status");

        assertThat(id).isNotNull();
        assertThat(name).isNotNull();
        assertThat(email).isNotNull();
        assertThat(gender).isIn("female", "male");
        assertThat(status).isIn("active", "inactive");

        global_id = Integer.toString(id);
    }

    public void hitApiUpdateUser(){
        res = updateUser(setUrl, global_id);
    }

    public void validationResponseBodyUpdateUser(){
        JsonPath jsonPathEvaluator = res.jsonPath();
        Integer id = jsonPathEvaluator.get("id");
        String name = jsonPathEvaluator.get("name");
        String email = jsonPathEvaluator.get("email");
        String gender = jsonPathEvaluator.get("gender");
        String status = jsonPathEvaluator.get("status");

        assertThat(id).isNotNull();
        assertThat(name).isNotNull();
        assertThat(email).isNotNull();
        assertThat(gender).isIn("female", "male");
        assertThat(status).isIn("active", "inactive");
    }

    public void hitApiDeleteUser(){
        res = deleteUser(setUrl, global_id);
    }

    public void hitApiDeleteUserById(String id) {
        res = deleteUser(setUrl, id);
//        System.out.println(res.getBody().asString());
    }

    public void hitApiGetUserById(String id) {
        res = getUserById(setUrl, id);
//        System.out.println(res.getBody().asString());
    }

    public void validationResponseBodyGetUserById() {
        JsonPath jsonPathEvaluator = res.jsonPath();
        Integer id = jsonPathEvaluator.get("id");
        String name = jsonPathEvaluator.get("name");
        String email = jsonPathEvaluator.get("email");
        String gender = jsonPathEvaluator.get("gender");
        String status = jsonPathEvaluator.get("status");

        assertThat(id).isNotNull();
        assertThat(name).isNotNull();
        assertThat(email).isNotNull();
        assertThat(gender).isIn("female", "male");
        assertThat(status).isIn("active", "inactive");
    }
    public void hitApiSearchUserByName(String name) {
        res = searchUserByName(setUrl, name);
//        System.out.println(res.getBody().asString());
    }

    public void validationResponseBodySearchUserByName() {
        JsonPath jsonPathEvaluator = res.jsonPath();
        List<Object> name = jsonPathEvaluator.getList("name");
        assertThat(name).isNotEmpty();
    }
    public void hitApiSearchUserByGender(String gender) {
        res = searchUserByGender(setUrl, gender);
//        System.out.println(res.getBody().asString());
    }

    public void validationResponseBodySearchUserByGender() {
        JsonPath jsonPathEvaluator = res.jsonPath();
        List<Object> gender = jsonPathEvaluator.getList("gender");
        assertThat(gender).isNotEmpty();
    }

    public void hitApiSearchUserByStatus(String status) {
        res = searchUserByStatus(setUrl, status);
//        System.out.println(res.getBody().asString());
    }

    public void validationResponseBodySearchUserByStatus() {
        JsonPath jsonPathEvaluator = res.jsonPath();
        List<Object> status = jsonPathEvaluator.getList("status");
        assertThat(status).isNotEmpty();
    }



}
