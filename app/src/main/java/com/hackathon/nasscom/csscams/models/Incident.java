package com.hackathon.nasscom.csscams.models;

import android.location.Location;

import java.io.File;

/**
 * Created by tarun on 07/04/18.
 */

public class Incident {

    private String id;
    private String userName;
    private File photo;
    private String categoryId;
    private String title;
    private String description;
    private Location location;
    private String status;
    private String assignee;

    public String getId() {
        return id;
    }

    public Incident setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Incident setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public File getPhoto() {
        return photo;
    }

    public Incident setPhoto(File photo) {
        this.photo = photo;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Incident setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Incident setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Incident setDescription(String description) {
        this.description = description;
        return this;
    }

    public Location getLocation() {
        return location;
    }

    public Incident setLocation(Location location) {
        this.location = location;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Incident setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAssignee() {
        return assignee;
    }

    public Incident setAssignee(String assignee) {
        this.assignee = assignee;
        return this;
    }
}
