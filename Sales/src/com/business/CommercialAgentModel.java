package com.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

public class CommercialAgentModel {

	public Response addClient(Client client) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "INSERT INTO clients(client,agent_email, email, address,number)"
					+ "VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement toInsert = connector.configurePreparedStatement(query);
			toInsert.setString(1, client.getClientName());
			toInsert.setString(2, client.getAgentEmail());
			toInsert.setString(3, client.getEmail());
			toInsert.setString(4, client.getAddress());
			toInsert.setString(5, client.getPhoneNumber());
			toInsert.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully added client.");
			return reply;

		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Client with such email already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in adding client, please try again.");
			return reply;
		}
	}

	public Response removeClient(String email, String agentEmail) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM clients WHERE email=? AND agent_email=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setString(1, email);
			toRemove.setString(2, agentEmail);
			toRemove.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully removed client.");
			return reply;

		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in removing client, please try again.");
			return reply;
		}

	}

	public Client getClient(String email, String agentEmail) {
		try (DBConnector connector = new DBConnector()) {
			Client client = new Client();
			connector.getConnection();
			String query = ("SELECT * FROM clients WHERE email=? AND agent_email=?;");
			PreparedStatement toReturn = connector.configurePreparedStatement(query);
			toReturn.setString(1, email);
			toReturn.setString(2, agentEmail);
			ResultSet result = toReturn.executeQuery();
			result.next();
			client.setClientName(result.getString("client"));
			client.setAgentEmail(result.getString("agent_email"));
			client.setEmail(result.getString("email"));
			client.setAddress(result.getString("address"));
			client.setPhoneNumber(result.getString("number"));

			return client;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Response editClient(String email, String agentEmail, Client client) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "UPDATE clients\n" + "SET client=?, email=?, address=?,number=?\n"
					+ "WHERE email=? AND agent_email=?;";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setString(5, email);
			toUpdate.setString(6, agentEmail);
			toUpdate.setString(1, client.getClientName());
			toUpdate.setString(2, client.getEmail());
			toUpdate.setString(3, client.getAddress());
			toUpdate.setString(4, client.getPhoneNumber());
			toUpdate.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully edited client.");
			return reply;
		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Client with such email already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in editing client, please try again.");
			return reply;
		}
	}

	public Response editSale(String email, int saleId, Sale sale) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "UPDATE sales\n"
					+ "SET sale_id=?,agent_email=?, client=?, date=?, sum=?, product_id=?, quantity_sold=?\n"
					+ "WHERE agent_email=? AND sale_id=?;";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setString(8, email);
			toUpdate.setInt(9, saleId);
			toUpdate.setInt(1, sale.getId());
			toUpdate.setString(2, sale.getAgentEmail());
			toUpdate.setString(3, sale.getClientName());
			toUpdate.setString(4, sale.getDate());
			toUpdate.setDouble(5, sale.getSum());
			toUpdate.setInt(6, sale.getProductId());
			toUpdate.setInt(7, sale.getQuantitySold());
			toUpdate.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully edited sale.");
			return reply;
		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Sale with such id already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in editing sale, please try again.");
			return reply;
		}
	}

	public Response addSale(Sale sale) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "INSERT INTO sales(sale_id, agent_email, client, date, sum, product_id, quantity_sold)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?); ";
			PreparedStatement toUpdate = connector.configurePreparedStatement(query);
			toUpdate.setInt(1, sale.getId());
			toUpdate.setString(2, sale.getAgentEmail());
			toUpdate.setString(3, sale.getClientName());
			toUpdate.setString(4, sale.getDate());
			toUpdate.setDouble(5, sale.getSum());
			toUpdate.setInt(6, sale.getProductId());
			toUpdate.setInt(7, sale.getQuantitySold());
			toUpdate.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully added sale.");
			return reply;

		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Sale with such id already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in adding sale, please try again.");
			return reply;
		}
	}

	public Sale getSale(String email, int saleId) {
		try (DBConnector connector = new DBConnector()) {
			Sale sale = new Sale();
			connector.getConnection();
			String query = ("SELECT * FROM sales WHERE sale_id=? AND agent_email=?;");
			PreparedStatement toReturn = connector.configurePreparedStatement(query);
			toReturn.setInt(1, saleId);
			toReturn.setString(2, email);
			ResultSet result = toReturn.executeQuery();
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

	public Response removeSale(String email, int id) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM sales WHERE sale_id=? AND agent_email=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setInt(1, id);
			toRemove.setString(2, email);
			toRemove.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully removed sale.");
			return reply;

		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in removing sale, please try again.");
			return reply;
		}

	}

	public List<Client> getClients(String agentEmail) {
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "SELECT * FROM clients WHERE agent_email=?;";
			PreparedStatement toGet = connector.configurePreparedStatement(query);
			toGet.setString(1, agentEmail);
			ResultSet result = toGet.executeQuery();

			return printClientsData(result);

		} catch (Exception e) {
			return null;
		}
	}

	private List<Client> printClientsData(ResultSet result) throws SQLException {
		List<Client> clients = new LinkedList<Client>();

		while (result.next()) {
			Client client = new Client();
			client.setClientName(result.getString("client"));
			client.setEmail(result.getString("email"));
			client.setAddress(result.getString("address"));
			client.setPhoneNumber(result.getString("number"));
			clients.add(client);
		}
		return clients;
	}

}
