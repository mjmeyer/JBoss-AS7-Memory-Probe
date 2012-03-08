package net.deepvoodoo.jbas7MemoryProbe.model;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.dmr.ModelNode;
import org.jboss.logging.Logger;

@Named
@RequestScoped
public class PlatformMbeanMemory implements Serializable{
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PlatformMbeanMemory.class);

	public final static String PERMGEN_METRIC= "non-heap-memory-usage";
	public final static String HEAP_METRIC = "heap-memory-usage";
	@Inject
	ModelControllerClient client;
	
	/***
	 * Gets the core-service=platform-mbean/type=memory:read-attribute(name=non-heap-memory-usage)
	 * from Jboss CLI as a JSON object.
	 * @return JSON string representing the permgen/non-heap state
	 */
	public String getPermgenJSON(){
		ModelNode result = queryMemoryMetric(PERMGEN_METRIC);
		if (result != null){
			return result.get("result").toJSONString(false);
		} else {
			return "";
		}
	}
	
	/***
	 * Gets a specific attribute of the given metric
	 * @param metric PERMGEN_METRIC or HEAP_METRIC
	 * @param attr the name of a attribute in the JSON object. 
	 * @return the value of the attribute or undefined of attribute doesnt exist
	 */
	public String getMetricJSONAttr(String metric, String attr){
		ModelNode result = queryMemoryMetric(metric);
		if (result != null){
			try {
				String val = result.get("result").get(attr).toString();
				return val;
			} catch (Exception ex) {
				return "attribute " + attr + " not found in model";
			}
		} else {
			return "";
		}
	}
	
	/***
	 * Gets the percentage of permgen currently used
	 * @return
	 */
	public float getPercentPermgenUsed(){
		ModelNode result = queryMemoryMetric(PERMGEN_METRIC);
		if (result != null){
			return getPercentUsedFromMetric(result);
		} else {
			return 0;
		}
	}
	
	/***
	 * Gets the core-service=platform-mbean/type=memory:read-attribute(name=heap-memory-usage)
	 * from Jboss CLI as a JSON object.
	 * @return JSON string representing the heap state
	 */
	public String getHeapJSON(){
		ModelNode result = queryMemoryMetric(HEAP_METRIC);
		if (result != null){
			return result.get("result").toJSONString(false);
		} else {
			return "";
		}
	}

	/***
	 * Gets the percentage of heap currently used
	 * @return
	 */
	public float getPercentHeapUsed(){
		ModelNode result = queryMemoryMetric(HEAP_METRIC);
		if (result != null){
			return getPercentUsedFromMetric(result);
		} else {
			return 0;
		}
	}

	/***
	 * Queries Jboss CLI for the given metric
	 * @param metric HEAP_METRIC or PERMGEN_METRIC
	 * @return ModelNode result of the query if query was successful. null if not
	 */
	private ModelNode queryMemoryMetric(String metric){
		ModelNode op = new ModelNode();
		ModelNode result = null;
		try {
			ModelNode address = op.get("address");
			// /core-service=platform-mbean/type=memory:read-attribute(name=non-heap-memory-usage|object-pending-finalization-count|heap-memory-usage)
			address.add("core-service", "platform-mbean");
			address.add("type", "memory");
			op.get("operation").set("read-attribute");
			op.get("name").set(metric);
			result = client.execute(op);
			if (result.get("outcome").toString().equals("\"success\""))
			{
				return result;
			} else {
				return null;
			}
		}catch (Exception e){
			logger.error("error getting metric: " + metric + ": ",e);
			return null;
		}
	}
	
	/***
	 * Takes a query result that includes used & max attributes and computes a percent used value
	 * @param queryResult
	 * @return the % used of the metric that was queried for
	 */
	private float getPercentUsedFromMetric(ModelNode queryResult){
		// if you wanted all props
		/*List<Property> props = queryResult.get("result").asPropertyList();
		System.out.println("props size:" + props.size());
		for (Property prop: props){
			System.out.println(prop.getName() + " = " + prop.getValue().asLong());
		}*/
		// Or as JSON string...
		//System.out.println(queryResult.get("result").toJSONString(false));

		long used = queryResult.get("result").get("used").asLong();
		long max = queryResult.get("result").get("max").asLong();
		float percentUsed = ((used * 100) / (max * 1.0f));
		return percentUsed;
	}

}
