/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ass.controller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.gson.Gson;
import java.util.Date;

/**
 *
 * @author Alvar
 */
@Controller
public class LoginController {

    @RequestMapping("login")
    public @ResponseBody
    String login(@RequestParam("username") String username,@RequestParam("password") String password) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter usernameFilter = new  FilterPredicate("username",FilterOperator.EQUAL,username);
        Query q = new Query("Account").setFilter(usernameFilter);
        PreparedQuery pq = datastore.prepare(q);
        Entity result = pq.asSingleEntity();
        return result == null?null:new Gson().toJson(result);
    }
}
