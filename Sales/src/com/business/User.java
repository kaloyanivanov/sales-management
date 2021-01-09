package com.business;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class User extends LimitedUser {
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@GET
	@Path("/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sale> viewSales(@PathParam("email") String email) {
		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			String query = "SELECT * FROM sales WHERE agent_email=?;";
			PreparedStatement toRemove = connector.configurePreparedStatement(query);
			toRemove.setString(1, email);
			ResultSet result = toRemove.executeQuery();
			return printSalesData(result);

		} catch (Exception e) {
			return null;
		}
	}

	protected List<Sale> printSalesData(ResultSet resultSet) throws SQLException {
		List<Sale> sales = new LinkedList<Sale>();

		while (resultSet.next()) {
			Sale sale = new Sale();
			sale.setId(resultSet.getInt("sale_id"));
			sale.setAgentEmail(resultSet.getString("agent_email"));
			sale.setClientName(resultSet.getString("client"));
			sale.setDate(resultSet.getString("date"));
			sale.setSum(resultSet.getDouble("sum"));
			sale.setProductId(resultSet.getInt("product_id"));
			sale.setQuantitySold(resultSet.getInt("quantity_sold"));
			sales.add(sale);
		}
		return sales;
	}

	@GET
	@Path("products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> viewProducts() {

		try (DBConnector connector = new DBConnector()) {
			connector.getConnection();
			connector.executeQuery("SELECT * FROM products;");

			return printProductsData(connector.getResults());

		} catch (Exception e) {
			return null;
		}
	}

	private List<Product> printProductsData(ResultSet resultSet) throws SQLException {
		List<Product> products = new LinkedList<Product>();

		while (resultSet.next()) {
			Product product = new Product();
			product.setId(resultSet.getInt("product_id"));
			product.setAvailability(resultSet.getBoolean("availability"));
			product.setPrice(resultSet.getDouble("price"));
			product.setQuantity(resultSet.getInt("quantity"));
			product.setType(resultSet.getString("type"));
			products.add(product);
		}
		return products;
	}

}
