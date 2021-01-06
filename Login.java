package com.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("login")
public class Login {
	
	private String username;
	private String password;
	private String email;
	private String type;
	
	
	@POST
    public  Login login(Login login){
    	if(verifyLoginData("business.commercial_agents",login)) login.setType("agent");
    	if(verifyLoginData("business.administrators",login)) login.setType("admin");
    	return login;

    }
    
    private boolean verifyLoginData(String table,Login login){
    	boolean isLoginSuccessful=true;
    	try (DBConnector connector = new DBConnector()) {
            connector.getConnection();
           String query="SELECT username,password,email FROM "+ table+ " WHERE email=? AND password=?;";
           PreparedStatement toInsert=connector.configurePreparedStatement(query);
           toInsert.setString(1,login.getEmail());
           toInsert.setString(2,login.getPassword());
           ResultSet results=toInsert.executeQuery();
           results.next();
           login.setUsername(results.getString("username"));

        } catch (Exception e) {
        	  isLoginSuccessful=false;
        }
		return isLoginSuccessful;

    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
