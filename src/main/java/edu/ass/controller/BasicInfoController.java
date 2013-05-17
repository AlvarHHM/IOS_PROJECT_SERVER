/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ass.controller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.urlfetch.URLFetchServicePb;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Alvar
 */
@Controller
public class BasicInfoController {

    @RequestMapping("/district")
    public @ResponseBody
    String getAllDistrict() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("district");
        PreparedQuery pq = datastore.prepare(query);
        return new Gson().toJson(pq.asList(FetchOptions.Builder.withDefaults()));
    }
    
    @RequestMapping("/party")
    public @ResponseBody
    String getAllParty() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("party");
        PreparedQuery pq = datastore.prepare(query);
        return new Gson().toJson(pq.asList(FetchOptions.Builder.withDefaults()));
    }
    
    @RequestMapping("candidate")
    public @ResponseBody String getAllCandidate(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("candidatelist");
        PreparedQuery pq = datastore.prepare(query);
        return new Gson().toJson(pq.asList(FetchOptions.Builder.withDefaults()));
    }
    
    
    
    @RequestMapping(value="/test",method=RequestMethod.POST)
    public @ResponseBody
    String test(HttpServletRequest req,@RequestParam("user")String user) throws IOException {
        
        BufferedReader body = new BufferedReader(new InputStreamReader(req.getInputStream()));
        String line = null;
        String result = new String();
        while((line = body.readLine())!= null){
            result +=line;
        }
        Logger.getAnonymousLogger().warning(user);
        Logger.getAnonymousLogger().warning(req.getContentType());
        Logger.getAnonymousLogger().warning(req.getParameterMap().toString());
        Logger.getAnonymousLogger().warning(result);
        Logger.getAnonymousLogger().warning(req.getMethod());
        return result;
    }
}
