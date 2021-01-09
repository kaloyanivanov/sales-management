package com.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

public class AdminModel extends User {

	public List<LimitedUser> getCommercialAgents() {

		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			connector.executeQuery("SELECT * FROM commercial_agents;");

			return getCommercialAgentsData(connector.getResults());

		} catch (Exception e) {
			return null;
		}
	}

	private List<LimitedUser> getCommercialAgentsData(ResultSet resultSet) throws SQLException {

		List<LimitedUser> agents = new LinkedList<LimitedUser>();
		while (resultSet.next()) {
			LimitedUser agent = new LimitedUser();
			agent.setFirstName(resultSet.getString("first_name"));
			agent.setLastName(resultSet.getString("last_name"));
			agent.setUsername(resultSet.getString("username"));
			agent.setEmail(resultSet.getString("email"));
			agents.add(agent);
		}
		return agents;
	}

	public Response addCommercialAgent(User agent) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "INSERT INTO commercial_agents(first_name, last_name, username, email, password)"
					+ "VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement toInsert = connector.configurePreparedStatement(query);
			toInsert.setString(1, agent.getFirstName());
			toInsert.setString(2, agent.getLastName());
			toInsert.setString(3, agent.getUsername());
			toInsert.setString(4, agent.getEmail());
			toInsert.setString(5, agent.getPassword());
			toInsert.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully added commercial agent.");
			return reply;

		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Commercial agent with such email already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in adding commercial agent, please try again.");
			return reply;
		}
	}

	public Response removeCommercialAgent(String username, String email) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM commercial_agents WHERE email=? AND username=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setString(1, email);
			toRemove.setString(2, username);
			toRemove.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully deleted commercial agent.");
			return reply;

		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in deleting commercial agent, please try again.");
			return reply;
		}

	}

	public Response editCommercialAgent(String username, String email, User agent) {
		Response reply = new Response();
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
			reply.setTypeMesssage();
			reply.setMessage("Successfully updated commercial agent.");
			return reply;
		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Commercial agent with such email already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in updating commercial agent, please try again.");
			return reply;
		}
	}

	public Response addProduct(Product product) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "INSERT INTO products(product_id,type,price,availability,quantity)"
					+ "VALUES (?, ?, ?, ?, ?); ";
			PreparedStatement toInsert = connector.configurePreparedStatement(query);
			toInsert.setInt(1, product.getId());
			toInsert.setString(2, product.getType());
			toInsert.setDouble(3, product.getPrice());
			toInsert.setBoolean(4, product.isAvailability());
			toInsert.setInt(5, product.getQuantity());
			toInsert.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully added product.");
			return reply;

		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Product with such id already exists. Please try again.");
			return reply;
		}

		catch (Exception e) {
			e.printStackTrace();
			reply.setTypeError();
			reply.setMessage("Error in adding product, please try again.");
			return reply;
		}
	}

	public Response removeProduct(int id) {
		Response reply = new Response();
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "DELETE FROM products WHERE product_id=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setInt(1, id);
			toRemove.execute();
			reply.setTypeMesssage();
			reply.setMessage("Successfully deleted product.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in deleting product, please try again.");
			return reply;
		}
	}

	public Response editProduct(int id, Product product) {
		Response reply = new Response();
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
			reply.setTypeMesssage();
			reply.setMessage("Successfully updated product.");
			return reply;
		} catch (SQLIntegrityConstraintViolationException ex) {
			reply.setTypeError();
			reply.setMessage("Product with such id already exists. Please try again.");
			return reply;
		} catch (Exception e) {
			reply.setTypeError();
			reply.setMessage("Error in updating product, please try again.");
			return reply;
		}
	}

	public User getCommercialAgent(String username, String email) {

		try (DBConnector connector = new DBConnector()) {
			User agent = new User();
			connector.getConnection();
			String query = ("SELECT * FROM commercial_agents WHERE email=? AND username=?;");
			PreparedStatement toReturn = connector.configurePreparedStatement(query);
			toReturn.setString(1, email);
			toReturn.setString(2, username);
			ResultSet result = toReturn.executeQuery();
			result.next();
			agent.setFirstName(result.getString("first_name"));
			agent.setLastName(result.getString("last_name"));
			agent.setEmail(result.getString("email"));
			agent.setUsername(result.getString("username"));
			return agent;

		} catch (Exception e) {
			return null;
		}
	}

	public Product getProduct(int id) {
		try (DBConnector connector = new DBConnector()) {
			Product product = new Product();
			connector.getConnection();
			String query = ("SELECT * FROM products WHERE product_id=?;");
			PreparedStatement toReturn = connector.configurePreparedStatement(query);
			toReturn.setInt(1, id);
			ResultSet result = toReturn.executeQuery();
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

	public List<Sale> getSales() {

		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			connector.executeQuery("SELECT * FROM sales;");

			return printSalesData(connector.getResults());

		} catch (Exception e) {
			return null;
		}
	}

	public List<Sale> getSales(String startDate, String endDate) {

		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "SELECT * FROM sales WHERE date BETWEEN ? AND ?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setString(1, startDate);
			toRemove.setString(2, endDate);
			ResultSet result = toRemove.executeQuery();
			return printSalesData(result);

		} catch (Exception e) {
			return null;
		}
	}

}
