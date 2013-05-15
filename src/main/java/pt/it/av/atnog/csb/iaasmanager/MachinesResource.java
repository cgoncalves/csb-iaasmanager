package pt.it.av.atnog.csb.iaasmanager;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineCollection;

@Stateless
@Path("/cimi-machines")
public interface MachinesResource {
	
	@Path("/")
	@GET
	@Produces({ MediaType.APPLICATION_XML})
	public CimiMachineCollection getMachines();
	
	@Path("/{id}")
	@GET
	@Produces({ MediaType.APPLICATION_XML})
	public CimiMachine getMachine(@PathParam("id") String id);
	
	@Path("/")
	@POST
	@Produces({ MediaType.APPLICATION_XML})
	public CimiMachine createMachine();
}
