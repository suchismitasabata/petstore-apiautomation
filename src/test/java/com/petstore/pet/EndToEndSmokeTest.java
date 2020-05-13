package com.petstore.pet;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.petstore.extentreports.ExtentTestNGReportBuilder;
import com.petstore.pojo.Category;
import com.petstore.pojo.PetPayload;
import com.petstore.pojo.Tag;
import com.petstore.utilities.Constants;
import com.petstore.utilities.LoggerLib;
import com.petstore.utilities.PropertiesUtiles;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class EndToEndSmokeTest extends ExtentTestNGReportBuilder{
	
	Path resourceDirectory = Paths.get("src","test","resources",Constants.configurationFile);
	Path testDataDirectory = Paths.get("src","test","resources",Constants.testdataFile);
	PropertiesUtiles propertiesUtiles = new PropertiesUtiles(resourceDirectory.toString());
	PropertiesUtiles testdata = new PropertiesUtiles(testDataDirectory.toString());
	
	Logger log = LoggerLib.writeLog(this.getClass().getName());
	@Test(priority = 1)
	public void createPetTest() throws IOException {
		
		RestAssured.baseURI = propertiesUtiles.getValue("base.url").toString();
		String postEndPoint = propertiesUtiles.getValue("endpoint.pet.post").toString();
		
		String propData = (String) testdata.getValue("pet.payload.post");
		String[] values = propData.split(",");
		Map<String, String> testdata= new HashMap<String, String>();
		for (int i=0;i<values.length;i++) {
			testdata.put(values[i].split(":")[0], values[i].split(":")[1]);
		}
		PetPayload petPayload = getPayload(Integer.parseInt(testdata.get("id")), Integer.parseInt(testdata.get("categoryid")), 
				"categoryName", "name", "url", Integer.parseInt(testdata.get("tagid")), "tagname", "status");
		System.out.println("PATH ---->    "+resourceDirectory.toString());
		Response response =
		  given().contentType(ContentType.JSON).body(petPayload).post(postEndPoint).
		  then().statusCode(200) .extract().response();
		  log.info(response.getStatusCode());
		  log.info(response.asString());
		  assertEquals(200, response.getStatusCode());
		  getReportLoggerTest().log(Status.INFO, "Sucessfully got POST response as "+response.getStatusCode());
		 
		
	}

	@Test(priority = 2)
	public void GetPetDetailsTest() {

		RestAssured.baseURI = propertiesUtiles.getValue("base.url").toString();
		String getEndPoint = propertiesUtiles.getValue("endpoint.pet.get").toString();

		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, getEndPoint.replace("{ID}", (String) testdata.getValue("pet.payload.get")));
		String responseBody = response.getBody().asString();

		log.info(response.getStatusCode());
		log.info("Response Body is =>  " + responseBody);
		assertEquals(200, response.getStatusCode());
		getReportLoggerTest().log(Status.INFO, "Sucessfully executed GET response as "+response.getStatusCode());

	}

	@Test(priority = 3)
	public void updatePetDetailsTest() {

		RestAssured.baseURI = propertiesUtiles.getValue("base.url").toString();
		String putEndPoint = propertiesUtiles.getValue("endpoint.pet.put").toString();
		String propData = (String) testdata.getValue("pet.payload.put");
		String[] values = propData.split(",");
		Map<String, String> testdata= new HashMap<String, String>();
		for (int i=0;i<values.length;i++) {
			testdata.put(values[i].split(":")[0], values[i].split(":")[1]);
		}
		System.out.println(testdata);
		PetPayload petPayload = getPayload(Integer.parseInt(testdata.get("id")), Integer.parseInt(testdata.get("categoryid")), 
				"categoryName", "name", "url", Integer.parseInt(testdata.get("tagid")), "tagname", "status");
		log.info("PATH ---->    "+resourceDirectory.toString());
		
		Response response = null;

		try {
			response = RestAssured.given().contentType(ContentType.JSON).body(petPayload).put(putEndPoint);
		} catch (Exception e) {
			e.printStackTrace();
		}

		log.info("Response :" + response.asString());
		log.info("Status Code :" + response.getStatusCode());
		
		assertEquals(200, response.getStatusCode());
		getReportLoggerTest().log(Status.INFO, "Sucessfully got PUT response as "+response.getStatusCode());
	}

	@Test(priority = 4)
	public void deletePetDetailsTest() {


		RestAssured.baseURI = propertiesUtiles.getValue("base.url").toString();
		String deleteEndPoint = propertiesUtiles.getValue("endpoint.pet.delete").toString();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.delete(deleteEndPoint.replace("{ID}", (String) testdata.getValue("pet.payload.delete")));
		log.info("Response :" + response.asString());
		log.info("Status Code :" + response.getStatusCode());
		assertEquals(200, response.getStatusCode());
		getReportLoggerTest().log(Status.INFO, "Sucessfully got DELETE response as "+response.getStatusCode());
	}
	
	public PetPayload getPayload(int id,int categoryId,String categoryName, String name,String url,int tagId, String tagname,String status) {
		PetPayload petPayload = new PetPayload();
		petPayload.setId(id);
		Category category = new Category();
		category.setId(categoryId);
		category.setName(categoryName);
		petPayload.setCatagory(category);
		petPayload.setName(name);
		List<String> urls = new ArrayList<String>();
		urls.add(url);
		petPayload.setPhotourls(urls);
		Tag tag1 = new Tag();
		tag1.setId(tagId);
		tag1.setName(tagname);
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(tag1);
		petPayload.setTags(tags);
		petPayload.setStatus(status);
		return petPayload;
	}
	
}
