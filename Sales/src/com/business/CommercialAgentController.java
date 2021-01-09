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

@Path("commercial")
public class CommercialAgentController extends User {

	CommercialAgentModel model = new CommercialAgentModel();

	@POST
	@Path("client")
	public Response addClient(Client client) {
		Response reply = new Response();
		if (verifyClientData(reply, client) != null)
			return reply;
		return model.addClient(client);
	}

	@DELETE
	@Path("client/{agentemail}/{email}")
	public Response removeClient(@PathParam("agentemail") String agentEmail, @PathParam("email") String email) {
		return model.removeClient(email, agentEmail);

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("client/{agentemail}/{email}")
	public Client getClient(@PathParam("agentemail") String agentEmail, @PathParam("email") String email) {
		return model.getClient(email, agentEmail);
	}

	@PUT
	@Path("client/{agentemail}/{email}")
	public Response editClient(@PathParam("agentemail") String agentEmail, @PathParam("email") String email,
			Client client) {
		Response reply = new Response();
		if (verifyClientData(reply, client) != null)
			return reply;
		return model.editClient(email, agentEmail, client);
	}

	@PUT
	@Path("sales/{email}/{id}")
	public Response editSale(@PathParam("email") String email, @PathParam("id") int saleId, Sale sale) {
		Response reply = new Response();
		if (verifySaleData(reply, sale) != null)
			return reply;
		return model.editSale(email, saleId, sale);

	}

	@POST
	@Path("sale")
	public Response addSale(Sale sale) {
		Response reply = new Response();
		if (verifySaleData(reply, sale) != null)
			return reply;
		return model.addSale(sale);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("sales/{email}/{id}")
	public Sale getSale(@PathParam("email") String email, @PathParam("id") int saleId) {
		return model.getSale(email, saleId);
	}

	@DELETE
	@Path("sales/{email}/{id}")
	public Response removeSale(@PathParam("email") String email, @PathParam("id") int id) {
		return model.removeSale(email, id);

	}

	@GET
	@Path("client/{agentemail}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Client> viewClients(@PathParam("agentemail") String agentEmail) {
		return model.getClients(agentEmail);
	}

	private Response verifySaleData(Response reply, Sale sale) {
		Validation validation = new Validation();
		StringBuilder message = new StringBuilder();
		if (validation.isStringLonger(sale.getClientName())) {
			message.append("Error, client must be up to 50 characters. ");
		}
		if (!validation.isValidDate(sale.getDate())) {
			message.append("Error, invalid date.");
		}
		if (message.length() != 0) {
			reply.setTypeError();
			reply.setMessage(message.toString());
			return reply;
		}

		return null;
	}

	private Response verifyClientData(Response reply, Client client) {
		Validation validation = new Validation();
		StringBuilder message = new StringBuilder();
		if (!validation.validateMail(client.getEmail())) {
			message.append("Error, invalid email address. ");
		}

		if (validation.isAddressLonger(client.getAddress())) {
			message.append("Error, address must be up to 200 characters. ");
		}
		if (validation.isStringLonger(client.getClientName())) {
			message.append("Error, client name must be up to 50 characters. ");
		}
		if (!validation.validateNumber(client.getPhoneNumber())) {
			message.append("Error, invalid phone number.");
		}
		if (message.length() != 0) {
			reply.setTypeError();
			reply.setMessage(message.toString());
			return reply;
		}

		return null;
	}
}
