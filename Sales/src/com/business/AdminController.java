package com.business;

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
public class AdminController extends User {

	private AdminModel model = new AdminModel();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<LimitedUser> viewCommercialAgents() {
		return model.getCommercialAgents();
	}

	@POST
	public Response addCommercialAgent(User agent) {
		Response reply = new Response();
		if (verifyAgentData(reply, agent) != null)
			return reply;
		return model.addCommercialAgent(agent);
	}

	@DELETE
	@Path("remove/{username}/{email}")
	public Response removeCommercialAgent(@PathParam("username") String username, @PathParam("email") String email) {
		return model.removeCommercialAgent(username, email);
	}

	@PUT
	@Path("edit/{username}/{email}")
	public Response editCommercialAgent(@PathParam("username") String username, @PathParam("email") String email,
			User agent) {
		Response reply = new Response();
		if (verifyAgentData(reply, agent) != null)
			return reply;
		return model.editCommercialAgent(username, email, agent);
	}

	@POST
	@Path("add/product")
	public Response addProduct(Product product) {
		Response reply = new Response();
		if (verifyProductData(reply, product) != null)
			return reply;
		return model.addProduct(product);
	}

	@DELETE
	@Path("remove/product/{id}")
	public Response removeProduct(@PathParam("id") int id) {
		return model.removeProduct(id);
	}

	@PUT
	@Path("update/product/{id}")
	public Response editProduct(@PathParam("id") int id, Product product) {
		Response reply = new Response();
		if (verifyProductData(reply, product) != null)
			return reply;
		return model.editProduct(id, product);
	}

	@Path("{username}/{email}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getCommercialAgent(@PathParam("username") String username, @PathParam("email") String email) {
		return model.getCommercialAgent(username, email);
	}

	@Path("product/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("id") int id) {
		return model.getProduct(id);
	}

	@GET
	@Path("sale")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sale> viewSales() {

		return model.getSales();
	}

	@GET
	@Path("sale/{from}/{to}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Sale> viewSales(@PathParam("from") String startDate, @PathParam("to") String endDate) {

		return model.getSales(startDate, endDate);
	}

	private Response verifyAgentData(Response reply, User agent) {
		Validation validation = new Validation();
		StringBuilder message = new StringBuilder();
		if (!validation.validateMail(agent.getEmail())) {
			message.append("Error, invalid email address. ");
		}
		if (!validation.validateUsername(agent.getUsername())) {
			message.append("Error, invalid username. ");
		}

		if (validation.isStringLonger(agent.getFirstName())) {
			message.append("Error, first name must be up to 50 characters. ");
		}
		if (validation.isStringLonger(agent.getLastName())) {
			message.append("Error, last name must be up to 50 characters. ");
		}
		if (validation.isStringLonger(agent.getPassword())) {
			message.append("Error, password must be up to 50 characters.");
		}
		if (message.length() != 0) {
			reply.setTypeError();
			reply.setMessage(message.toString());
			return reply;
		}

		return null;
	}

	private Response verifyProductData(Response reply, Product product) {
		Validation validation = new Validation();
		String message = new String();
		if (validation.isStringLonger(product.getType())) {
			message = "Error, product type must be up to 50 characters.";
		}
		if (message.length() != 0) {
			reply.setTypeError();
			reply.setMessage(message);
			return reply;
		}

		return null;
	}

}
