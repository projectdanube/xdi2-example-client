package xdi2.example.client;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import xdi2.client.XDIClient;
import xdi2.client.http.XDIHttpClient;
import xdi2.core.features.linkcontracts.instance.PublicLinkContract;
import xdi2.core.io.XDIWriter;
import xdi2.core.io.XDIWriterRegistry;
import xdi2.core.syntax.XDIAddress;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.MessageResult;

public class SimpleClient {

	public static void main(String[] args) throws Exception {

		LogManager.getLogger("xdi2").setLevel(Level.OFF);

		XDIWriter writer = XDIWriterRegistry.forFormat("XDI DISPLAY", null);

		// discovery

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=markus"), null);

		// construct message

		MessageEnvelope messageEnvelope = new MessageEnvelope();
		Message message = messageEnvelope.createMessage(XDIAddress.create("=sender"));
		message.createGetOperation(XDIAddress.create("=markus<#email>"));
		message.setToPeerRootXDIArc(xdiDiscoveryResult.getCloudNumber().getPeerRootXDIArc());
		message.setLinkContract(PublicLinkContract.class);

		// construct client, send message, read result

		XDIClient client = new XDIHttpClient(xdiDiscoveryResult.getXdiEndpointUrl());

		MessageResult messageResult = new MessageResult();
		client.send(messageEnvelope, messageResult);

		// print results

		System.out.println("Discovery result: ");
		writer.write(xdiDiscoveryResult.getMessageResult().getGraph(), System.out);
		System.out.println();

		System.out.println("Message envelope: ");
		writer.write(messageEnvelope.getGraph(), System.out);
		System.out.println();
		
		System.out.println("Message result: ");
		writer.write(messageResult.getGraph(), System.out);
	}
}
