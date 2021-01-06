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

@Path("commercial")
public class CommercialAgent extends User{
	@POST
	@Path("client")
	public Information addClient(Client client){
		Information success=new Information();
		if(verifyClientData(success,client)!=null) return success;
    	try (DBConnector connector = new DBConnector()) {
            connector.getConnection();
           String query="INSERT INTO " +
                   "clients(client, email, address,number)"+
                   "VALUES (?, ?, ?, ?); ";
           PreparedStatement toInsert=connector.configurePreparedStatement(query);
           toInsert.setString(1,client.getClientName());
           toInsert.setString(2,client.getEmail());
           toInsert.setString(3,client.getAddress());
           toInsert.setString(4,client.getPhoneNumber());
           toInsert.execute();
           success.setTypeMesssage();
		   success.setMessage("Successfully added client.");
		   return success;

        } 
    	catch (SQLIntegrityConstraintViolationException ex) {
    		success.setTypeError();
			success.setMessage("Client with such email already exists. Please try again.");
			return success;
    	}
    	catch (Exception e) {
        	success.setTypeError();
			success.setMessage("Error in adding client, please try again.");
			return success;
        }
    }
	
	private Information verifyClientData(Information success, Client client){
		Validation validation=new Validation();
		StringBuilder message=new StringBuilder();
		if(!validation.validateMail(client.getEmail())) {
			message.append("Error, invalid email address. ");
		}
		
		if(validation.isAddressLonger(client.getAddress())) {
			message.append("Error, address must be up to 200 characters. ");
		}
		if(validation.isStringLonger(client.getClientName())){
			message.append("Error, client name must be up to 50 characters. ");
		}
		if(!validation.validateNumber(client.getPhoneNumber())){
			message.append("Error, invalid phone number.");
		}
		if(message.length() != 0) {
			success.setTypeError();
			success.setMessage(message.toString());
			return success;
		}
		
		return null;
	}

	@DELETE
	@Path("client/{email}")
    public Information removeClient(@PathParam("email")String email){
		Information success=new Information();
    	try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM clients WHERE email=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setString(1,email);
			toRemove.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully removed client.");
			return success;

		} catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in removing client, please try again.");
			return success;
		}

    }
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("client/{email}")
	public Client getClient(@PathParam("email")String email) {
		try (DBConnector connector = new DBConnector()) {
			Client client=new Client();
			connector.getConnection();
			String query = ("SELECT * FROM clients WHERE email=?;");
			PreparedStatement toReturn = connector.configurePreparedStatement(query);
			toReturn.setString(1, email);
			ResultSet result =toReturn.executeQuery();
			result.next();
			client.setClientName(result.getString("client"));
			client.setEmail(result.getString("email"));
			client.setAddress(result.getString("address"));
			client.setPhoneNumber(result.getString("number"));
			
			
			return client;

		} catch (Exception e) {
			return null;
		}
	}
	
	@PUT
	@Path("client/{email}")
   public Information editClient(@PathParam("email")String email, Client client){
		Information success=new Information();
		if(verifyClientData(success,client)!=null) return success;
		try (DBConnector connector = new DBConnector()) {
    		connector.getConnection();
			String query = "UPDATE clients\n"
					+ "SET client=?, email=?, address=?,number=?\n"
					+ "WHERE email=?;";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setString(5,email);
			toUpdate.setString(1, client.getClientName());
			toUpdate.setString(2, client.getEmail());
			toUpdate.setString(3, client.getAddress());
			toUpdate.setString(4, client.getPhoneNumber());
			toUpdate.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully edited client.");
			return success;
		} 
		catch (SQLIntegrityConstraintViolationException ex) {
    		success.setTypeError();
			success.setMessage("Client with such email already exists. Please try again.");
			return success;
    	}
		catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in editing client, please try again.");
			return success;
		}
    }
	
	private Information verifySaleData(Information success,Sale sale){
		Validation validation=new Validation();
		StringBuilder message=new StringBuilder();
		if(validation.isStringLonger(sale.getClientName())){
			message.append("Error, client must be up to 50 characters. ");
		}
		if(!validation.isValidDate(sale.getDate())){
			message.append("Error, invalid date.");
		}
		if(message.length() != 0) {
			success.setTypeError();
			success.setMessage(message.toString());
			return success;
		}
		
		return null;
	}
	
	@PUT
	@Path("sales/{email}/{id}")
	public Information editSale(@PathParam("email")String email, @PathParam("id")int saleId,Sale sale){
		Information success=new Information();
		if(verifySaleData(success,sale)!=null) return success;
		try (DBConnector connector = new DBConnector()) {
    		connector.getConnection();
			String query = "UPDATE sales\n"
					+ "SET sale_id=?,agent_email=?, client=?, date=?, sum=?, product_id=?, quantity_sold=?\n"
					+ "WHERE agent_email=? AND sale_id=?;";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setString(8,email);
			toUpdate.setInt(9,saleId);
			toUpdate.setInt(1,sale.getId());
			toUpdate.setString(2,sale.getAgentEmail());
			toUpdate.setString(3,sale.getClientName());
			toUpdate.setString(4,sale.getDate());
			toUpdate.setDouble(5,sale.getSum());
			toUpdate.setInt(6,sale.getProductId());
			toUpdate.setInt(7,sale.getQuantitySold());
			toUpdate.execute();
			success.setTypeMesssage();
			success.setMessage("Successfully edited sale.");
			return success;
		} 
		catch (SQLIntegrityConstraintViolationException ex) {
    		success.setTypeError();
			success.setMessage("Sale with such id already exists. Please try again.");
			return success;
    	}
		catch (Exception e) {
			success.setTypeError();
			success.setMessage("Error in editing sale, please try again.");
			return success;
		}
    }
	
	@POST
	@Path("sale")
	public Information addSale(Sale sale){
		Information success=new Information();
		if(verifySaleData(success,sale)!=null) return success;
    	try (DBConnector connector = new DBConnector()) {
            connector.getConnection();
           String query="INSERT INTO " +
                   "sales(sale_id, agent_email, client, date, sum, product_id, quantity_sold)"+
                   "VALUES (?, ?, ?, ?, ?, ?, ?); ";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setInt(1,sale.getId());
			toUpdate.setString(2,sale.getAgentEmail());
			toUpdate.setString(3,sale.getClientName());
			toUpdate.setString(4,sale.getDate());
			toUpdate.setDouble(5,sale.getSum());
			toUpdate.setInt(6,sale.getProductId());
			toUpdate.setInt(7,sale.getQuantitySold());
			toUpdate.execute();
           success.setTypeMesssage();
		   success.setMessage("Successfully added sale.");
		   return success;

        } 
    	catch (SQLIntegrityConstraintViolationException ex) {
    		success.setTypeError();
			success.setMessage("Sale with such id already exists. Please try again.");
			return success;
    	}
    	catch (Exception e) {
        	success.setTypeError();
			success.setMessage("Error in adding sale, please try again.");
			return success;
        }
    }

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("sales/{email}/{id}")
 public Sale getSale( @PathParam("email")String email, @PathParam("id")int saleId) {
	try (DBConnector connector = new DBConnector()) {
		Sale sale=new Sale();
		connector.getConnection();
		String query = ("SELECT * FROM sales WHERE sale_id=? AND agent_email=?;");
		PreparedStatement toReturn = connector.configurePreparedStatement(query);
		toReturn.setInt(1, saleId);
		toReturn.setString(2, email);
		ResultSet result =toReturn.executeQuery();
		result.next();
		sale.setId(result.getInt("sale_id"));
		sale.setAgentEmail(result.getString("agent_email"));
		sale.setClientName(result.getString("client"));
		sale.setProductId(result.getInt("product_id"));
		sale.setDate(result.getString("date"));
		sale.setQuantitySold(result.getInt("quantity_sold"));
		sale.setSum(result.getDouble("sum"));
		
		return sale;

	} catch (Exception e) {
		return null;
	}
}


@DELETE
@Path("sales/{email}/{id}")
public Information removeSale(@PathParam("email")String email,@PathParam("id")int id){
	Information success=new Information();
	try (DBConnector connector = new DBConnector()) {
		connector.getConnection();
		String query = "DELETE FROM sales WHERE sale_id=? AND agent_email=?;";
		PreparedStatement toRemove = connector.configurePreparedStatement(query);
		toRemove.setInt(1,id);
		toRemove.setString(2,email);
		toRemove.execute();
		success.setTypeMesssage();
		success.setMessage("Successfully removed sale.");
		return success;

	} catch (Exception e) {
		success.setTypeError();
		success.setMessage("Error in removing sale, please try again.");
		return success;
	}

}

@GET
@Path("client")
@Produces(MediaType.APPLICATION_JSON)
public List <Client> viewClients() {
	try (DBConnector connector = new DBConnector()) {
		connector.getConnection();
		connector.executeQuery("SELECT * " + "FROM clients;");
		
		return printClientsData(connector.getResults());

	} catch (Exception e) {
		return null;
	}
}

private List <Client> printClientsData(ResultSet result) throws SQLException {
	List <Client> clients=new LinkedList <Client>();

	while (result.next()) {
		Client client=new Client();
		client.setClientName(result.getString("client"));
		client.setEmail(result.getString("email"));
		client.setAddress(result.getString("address"));
		client.setPhoneNumber(result.getString("number"));
		clients.add(client);
	}
	return clients;
}

}
