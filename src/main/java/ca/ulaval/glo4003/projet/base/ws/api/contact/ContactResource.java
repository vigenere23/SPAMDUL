package ca.ulaval.glo4003.projet.base.ws.api.contact;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4003.projet.base.ws.api.contact.dto.ContactDto;

import java.util.List;

@Path("/telephony/contacts")
public interface ContactResource {

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  List<ContactDto> getContacts();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{id}")
  ContactDto getContact(@PathParam("id") String id);

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  void addContact(ContactDto contactDto);

  @PUT
  @Path("{id}")
  void updateContact(@PathParam("id") String id,
                     ContactDto contactDto);

  @DELETE
  @Path("{id}")
  void deleteContact(@PathParam("id") String id);
}
