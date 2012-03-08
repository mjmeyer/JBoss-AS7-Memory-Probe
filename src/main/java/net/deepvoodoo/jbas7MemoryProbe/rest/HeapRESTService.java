package net.deepvoodoo.jbas7MemoryProbe.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.deepvoodoo.jbas7MemoryProbe.model.PlatformMbeanMemory;


/**
 * JAX-RS Service to reveal Jboss CLI metrics for heap memory usage on the local Jboss instance
 */

@RequestScoped
@Path("heap")
public class HeapRESTService {
	@Inject
	PlatformMbeanMemory model;
	
	@GET
	@Path("")
	@Produces("application/json")
	public String getHeap() {
		return model.getHeapJSON();
	}
	
	@GET
	@Path("/percentUsed")
	@Produces("text/html")
	public String getHeapPctUsed() {
		return String.format("%.0f",model.getPercentHeapUsed());
	}
	
	@GET
	@Path("/{attr}")
	@Produces("text/html")
	public String getPermgenAttribute(@PathParam("attr") String attr) {
		return model.getMetricJSONAttr(PlatformMbeanMemory.HEAP_METRIC, attr).toString();
	}
	
}
