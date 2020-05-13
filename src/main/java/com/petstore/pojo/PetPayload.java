package com.petstore.pojo;

import java.util.ArrayList;
import java.util.List;

public class PetPayload {

	private int id;
	private Category catagory;
	private String name;
	private List<String> photoUrls;
	private List<Tag> tags;
	private String status;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Category getCatagory() {
		return catagory;
	}
	public void setCatagory(Category catagory) {
		this.catagory = catagory;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhotourls() {
		return photoUrls;
	}
	public void setPhotourls(List<String> photourls) {
		this.photoUrls = photourls;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
