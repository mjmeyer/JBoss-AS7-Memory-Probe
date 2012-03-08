package net.deepvoodoo.jbas7MemoryProbe.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.as.controller.client.ModelControllerClient;
import org.jboss.logging.Logger;

public class CliClientProducer {
	
private static final Logger logger = Logger.getLogger(CliClientProducer.class);
	
	private ModelControllerClient mClient = null;
	
	@Produces
	@ApplicationScoped 
	public ModelControllerClient getCliClient (){
		if (mClient == null) {
			mClient = obtainClient();
		}
		return mClient;
	}
	
	private ModelControllerClient obtainClient(){
		ModelControllerClient client = null;
		try {
			client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9999);
		} catch (UnknownHostException e) {
			logger.error("error connecting CLI client: ",e);
		}
		return client;
	}
	

}
