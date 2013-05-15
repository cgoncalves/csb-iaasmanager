package pt.it.av.atnog.csb.iaasmanager;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.CimiMachineImage;

/**
 * @author <a href="mailto:cgoncalves@av.it.pt">Carlos Gon&ccedil;alves</a>
 *
 */
@Stateless
@Path("/cimi-machines")
public interface ImagesResource {
	
	@Path("/")
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public CimiMachine createImage(final CimiMachineImage cimiData);
}
