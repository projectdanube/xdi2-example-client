package xdi2.example.client;

import java.net.URI;

import xdi2.client.XDIClient;
import xdi2.client.impl.http.XDIHttpClient;
import xdi2.core.ContextNode;
import xdi2.core.Relation;
import xdi2.core.constants.XDIConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.features.linkcontracts.instance.PublicLinkContract;
import xdi2.core.syntax.CloudName;
import xdi2.core.syntax.CloudNumber;
import xdi2.core.syntax.XDIStatement;
import xdi2.core.util.iterators.ReadOnlyIterator;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.response.MessagingResponse;

/**
 * This example shows how to discover a list of cloud names given a cloud number,
 * using the standard XDI messaging API.
 */
public class CloudNameDiscovery {

	public static XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;

	public static void discoverAutomatic(CloudNumber cloudNumber) throws Exception {

		XDIDiscoveryResult result = xdiDiscoveryClient.discover(cloudNumber.getXDIAddress());

		for (CloudName cloudName : result.getCloudNames()) System.out.println("Automatically discovered: " + cloudName);
	}

	public static void discoverManual(CloudNumber cloudNumber) throws Exception {

		XDIDiscoveryResult resultFromRegistry = xdiDiscoveryClient.discoverFromRegistry(cloudNumber.getXDIAddress());

		URI xdiEndpointUri = resultFromRegistry.getXdiEndpointUri();

		MessageEnvelope messageEnvelope = new MessageEnvelope();
		Message message = messageEnvelope.createMessage(cloudNumber.getXDIAddress());
		message.setToPeerRootXDIArc(cloudNumber.getPeerRootXDIArc());
		message.createGetOperation(XDIStatement.fromComponents(cloudNumber.getXDIAddress(), XDIDictionaryConstants.XDI_ADD_IS_REF, XDIConstants.XDI_ADD_COMMON_VARIABLE));
		message.setLinkContractClass(PublicLinkContract.class);

		XDIClient xdiClient = new XDIHttpClient(xdiEndpointUri);

		MessagingResponse messagingResponse = xdiClient.send(messageEnvelope);

		ContextNode contextNode = messagingResponse.getGraph().getDeepContextNode(cloudNumber.getXDIAddress());
		ReadOnlyIterator<Relation> relations = contextNode.getRelations(XDIDictionaryConstants.XDI_ADD_IS_REF);

		for (Relation relation : relations) System.out.println("Manually discovered: " + relation.follow().getXDIAddress());
	}

	public static void main(String[] args) throws Exception {

		CloudNumber cloudNumber = CloudNumber.create("=!:uuid:91f28153-f600-ae24-91f2-8153f600ae24");

		discoverAutomatic(cloudNumber);

		discoverManual(cloudNumber);
	}
}
