package com.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("admin")
public class Admin extends User{

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List <LimitedUser> viewCommercialAgents() {

		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			connector.executeQuery("SELECT * " + "FROM commercial_agents;");
			
			return getCommercialAgentsData(connector.getResults());

		} catch (Exception e) {
			return null;
		}
	}

	private List <LimitedUser> getCommercialAgentsData(ResultSet resultSet) throws SQLException {

		List <LimitedUser> agents=new LinkedList <LimitedUser>();
		while (resultSet.next()) {
			LimitedUser agent=new LimitedUser();
			agent.setFirstName(resultSet.getString("first_name"));
			agent.setLastName(resultSet.getString("last_name"));
			agent.setUsername(resultSet.getString("username"));
			agent.setEmail(resultSet.getString("email"));;
			agents.add(agent);
		}
		return agents;
	}
	
	@POST
	public Information addCommercialAgent(User agent) {
		Information success=new Information();
		if(verifyAgentData(success,agent)!=null) return success;
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "INSERT INTO " + "commercial_agents(first_name, last_name, username, email, password)"
					+ "VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement toInsert = connector.configurePreparedStatement(query);
			toInsert.setString(1, agent.getFirstName());
			toInsert.setString(2, agent.getLastName());
			toInsert.setString(3, agent.getUsername());
			toInsert.setString(4, agent.getEmail());
			toInsert.setString(5, agent.getPassword());
			toInsert.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully added commercial agent.");
			return success;

		}
		catch (SQLIntegrityConstraintViolationException ex) {
    		success.setTypeError();
			success.setMessage("Commercial agent with such email already exists. Please try again.");
			return success;
    	}
		catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in adding commercial agent, please try again.");
			return success;
		}
	}

	private Information verifyAgentData(Information success, User agent){
		Validation validation=new Validation();
		StringBuilder message=new StringBuilder();
		if(!validation.validateMail(agent.getEmail())) {
			message.append("Error, invalid email address. ");
		}
		if(!validation.validateUsername(agent.getUsername())){
			message.append("Error, invalid username. ");
		}
		
		if(validation.isStringLonger(agent.getFirstName())) {
			message.append("Error, first name must be up to 50 characters. ");
		}
		if(validation.isStringLonger(agent.getLastName())){
			message.append("Error, last name must be up to 50 characters. ");
		}
		if(validation.isStringLonger(agent.getPassword())){
			message.append("Error, password must be up to 50 characters.");
		}
		if(message.length() != 0) {
			success.setTypeError();
			success.setMessage(message.toString());
			return success;
		}
		
		return null;
	}
	
	@DELETE
	@Path("remove/{username}/{email}")
	public Information removeCommercialAgent(@PathParam("username")String username,@PathParam("email") String email) {
		Information success=new Information();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM commercial_agents WHERE email=? AND username=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setString(1, email);
			toRemove.setString(2, username);
			toRemove.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully deleted commercial agent.");
			return success;

		} catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in deleting commercial agent, please try again.");
			return success;
		}

	}

	@PUT
	@Path("edit/{username}/{email}")
	public Information editCommercialAgent(@PathParam("username")String username,@PathParam("email") String email,User agent) {
		Information success=new Information();
		if(verifyAgentData(success,agent)!=null) return success;
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "UPDATE commercial_agents\n"
					+ "SET first_name = ?, last_name=?,username=?, email=?, password=?\n"
					+ "WHERE email=? AND username=?;";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setString(6, email);
			toUpdate.setString(7, username);
			toUpdate.setString(1, agent.getFirstName());
			toUpdate.setString(2, agent.getLastName());
			toUpdate.setString(3, agent.getUsername());
			toUpdate.setString(4, agent.getEmail());
			toUpdate.setString(5, agent.getPassword());
			toUpdate.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully updated commercial agent.");
			return success;
		} catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in updating commercial agent, please try again.");
			return success;
		}
	}



	@POST
	@Path("add/product")
	public Information addProduct(Product product) {
		Information success=new Information();
		if(verifyProductData(success,product)!=null) return success;
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "INSERT INTO " + "products(product_id,type,price,availability,quantity)"
					+ "VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement toInsert = connector.configurePreparedStatement(query);
			toInsert.setInt(1, product.getId());
			toInsert.setString(2, product.getType());
			toInsert.setDouble(3, product.getPrice());
			toInsert.setBoolean(4, product.isAvailability());
			toInsert.setInt(5, product.getQuantity());
			toInsert.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully added product.");
			return success;

		} catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in adding product, please try again.");
			return success;
		}
	}

	@DELETE
	@Path("remove/product/{id}")
	public Information removeProduct(@PathParam("id")int id) {
		Information success=new Information();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM products WHERE product_id=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setInt(1, id);
			toRemove.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully deleted product.");
			return success;
		} catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in deleting product, please try again.");
			return success;
		}
	}

	@PUT
	@Path("update/product/{id}")
	public Information editProduct(@PathParam("id")int id,Product product) {
		Information success=new Information();
		if(verifyProductData(success,product)!=null) return success;
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "UPDATE products\n" + "SET product_id = ?, type=?,price=?, availability=?, quantity=?\n"
					+ "WHERE product_id=?;";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setInt(6, id);
			toUpdate.setInt(1, product.getId());
			toUpdate.setString(2, product.getType());
			toUpdate.setDouble(3, product.getPrice());
			toUpdate.setBoolean(4, product.isAvailability());
			toUpdate.setInt(5, product.getQuantity());
			toUpdate.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully updated product.");
			return success;
		} catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in updating product, please try again.");
			return success;
		}
	}
	
	private Information verifyProductData(Information success, Product product){
		Validation validation=new Validation();
		String message=new String();
		if(validation.isStringLonger(product.getType())){
			message="Error, product type must be up to 50 characters.";
		}
		if(message.length() != 0) {
			success.setTypeError();
			success.setMessage(message);
			return success;
		}
		
		return null;
	}
		@Path("{username}/{email}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public User getCommercialAgent(@PathParam("username")String username,@PathParam("email")String email) {

			try (DBConnector connector = new DBConnector()) {
				User agent=new User();
				connector.getConnection();
				String query = ("SELECT * " + "FROM commercial_agents WHERE email=? AND username=?;");
				PreparedStatement toReturn = connector.configurePreparedStatement(query);
				toReturn.setString(1, email);
				toReturn.setString(2, username);
				ResultSet result =toReturn.executeQuery();
				result.next();
				agent.setFirstName(result.getString("first_name"));
				agent.setLastName(result.getString("last_name"));
				agent.setEmail(result.getString("email"));
				agent.setUsername(result.getString("username"));
				agent.setPassword(result.getString("password"));
				return agent;

			} catch (Exception e) {
				return null;
			}
		}
		
		
		@Path("product/{id}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Product getProduct(@PathParam("id")int id) {
			try (DBConnector connector = new DBConnector()) {
				Product product=new Product();
				connector.getConnection();
				String query = ("SELECT * " + "FROM products WHERE product_id=?;");
				PreparedStatement toReturn = connector.configurePreparedStatement(query);
				toReturn.setInt(1, id);
				ResultSet result =toReturn.executeQuery();
				result.next();
				product.setId(result.getInt("product_id"));
				product.setPrice(result.getDouble("price"));
				product.setAvailability(result.getBoolean("availability"));
				product.setQuantity(result.getInt("quantity"));
				product.setType(result.getString("type"));
				
				return product;

			} catch (Exception e) {
				return null;
			}
		}
		
		@GET
		@Path("sale")
		@Produces(MediaType.APPLICATION_JSON)
		public List <Sale> viewSales() {

			try (DBConnector connector = new DBConnector()) {
				connector.getConnection();
				connector.executeQuery("SELECT * " + "FROM sales;");
				
				return printSalesData(connector.getResults());

			} catch (Exception e) {
				return null;
			}
		}
		
		@GET
		@Path("sale/{from}/{to}")
		@Produces(MediaType.APPLICATION_JSON)
		public List <Sale> viewSales(@PathParam("from")String startDate,@PathParam("to")String endDate) {

			try (DBConnector connector = new DBConnector()) {
				connector.getConnection();
	    		String query = "SELECT * FROM sales WHERE date BETWEEN ? AND ?;";
	    		PreparedStatement toRemove = connector.configurePreparedStatement(query);
	    		toRemove.setString(1,startDate);
	    		toRemove.setString(2,endDate);
	    		ResultSet result =toRemove.executeQuery();
	    		return printSalesData(result);

			} catch (Exception e) {
				return null;
			}
		}

}
