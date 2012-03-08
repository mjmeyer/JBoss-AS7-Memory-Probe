package net.deepvoodoo.jbas7MemoryProbe.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.deepvoodoo.jbas7MemoryProbe.model.PlatformMbeanMemory;


/**
 * JAX-RS Service to reveal Jboss CLI metrics for Permgen / non-heap memory usage on the local Jboss instance
 */

@RequestScoped
@Path("permgen")
public class PermgenRESTService {
	@Inject
	PlatformMbeanMemory model;
	
	@GET
	@Path("")
	@Produces("application/json")
	public String getPermgen() {
		return model.getPermgenJSON();
	}
	
	@GET
	@Path("/percentUsed")
	@Produces("text/html")
	public String getPermgenPctUsed() {
		return String.format("%.0f",model.getPercentPermgenUsed());
	}
	
	@GET
	@Path("/{attr}")
	@Produces("text/html")
	public String getPermgenAttribute(@PathParam("attr") String attr) {
		return model.getMetricJSONAttr(PlatformMbeanMemory.PERMGEN_METRIC,attr).toString();
	}
	
}
