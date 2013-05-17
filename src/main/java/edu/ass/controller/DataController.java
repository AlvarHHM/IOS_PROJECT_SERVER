/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ass.controller;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.appengine.api.rdbms.AppEngineDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Alvar
 */
@Controller
public class DataController {

    @RequestMapping("/data")
    public @ResponseBody
    String importData() throws SQLException {
        DriverManager.registerDriver(new AppEngineDriver());
        importTable("party");
        return "hello";
    }
    
    private void importTable(String table) throws SQLException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Connection c = DriverManager.getConnection("jdbc:google:rdbms://fyp-db:workflow-fyp-db/ios");

        ResultSet rs = c.createStatement().executeQuery("SHOW COLUMNS FROM "+table);
        List<String> column = new ArrayList<String>();
        while (rs.next()) {
            column.add(rs.getString("COLUMN_NAME"));
        }
        rs = c.createStatement().executeQuery("select * from "+table);
        while(rs.next()){
            Entity entity = new Entity(table);
            for(String col:column){
                entity.setProperty(col, rs.getObject(col));
            }
            datastore.put(entity);
        }
        
    }

    private void importCandidateList() throws SQLException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Connection c = DriverManager.getConnection("jdbc:google:rdbms://fyp-db:workflow-fyp-db/ios");

        ResultSet rs = c.createStatement().executeQuery("select * from ios.candidatelist");
        while (rs.next()) {
            Entity candidatelist = new Entity("candidatelist");
            candidatelist.setProperty("gcid", rs.getInt("gcid"));
            candidatelist.setProperty("listid", rs.getInt("listid"));
            candidatelist.setProperty("candidatenum", rs.getInt("candidatenum"));
            candidatelist.setProperty("candidatename", rs.getString("candidatename"));
            candidatelist.setProperty("partyid", rs.getInt("partyid"));
            datastore.put(candidatelist);
        }
    }
}
