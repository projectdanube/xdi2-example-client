package xdi2.example.client;
import xdi2.client.XDIClient;
import xdi2.client.impl.local.XDILocalClient;
import xdi2.core.Graph;
import xdi2.core.constants.XDIConstants;
import xdi2.core.constants.XDIDictionaryConstants;
import xdi2.core.features.nodetypes.XdiCommonRoot;
import xdi2.core.features.nodetypes.XdiInnerRoot;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.syntax.CloudName;
import xdi2.core.syntax.CloudNumber;
import xdi2.core.syntax.XDIAddress;
import xdi2.core.syntax.XDIStatement;
import xdi2.core.util.GraphUtil;
import xdi2.core.util.XDIAddressUtil;
import xdi2.messaging.Message;
import xdi2.messaging.MessageEnvelope;
import xdi2.messaging.response.MessagingResponse;


/**
 * This shows how a cloud name can be "remembered" as part of a relationship
 * between two XDI authorities. This should not replace the need to properly
 * discover cloud names from cloud numbers, but can be used a supplementary tool.
 * 
 * This simple exampe assumes that there is only one cloud name per cloud number,
 * and that there is notion of "preferred" cloud names.
 */
public class SetGetCloudNameForRelationship {

	public static void main(String[] args) throws Exception {

		// let's get started by setting up a fake graph for child1
		// and a client to send messages to it

		CloudNumber child1CloudNumber = CloudNumber.create("=!:uuid:1111");

		CloudName child2CloudName = CloudName.create("=bob");
		CloudNumber child2CloudNumber = CloudNumber.create("=!:uuid:2222");

		Graph child1Graph = MemoryGraphFactory.getInstance().openGraph();
		GraphUtil.setOwnerXDIAddress(child1Graph, child1CloudNumber.getXDIAddress());
		XDIClient clientToChild1 = new XDILocalClient(child1Graph);

		// let's write child2's cloud name into the child1's graph
		// we write this into an inner root representing the child1/child2 relationship
		// this will look as follows in the graph:
		//
		// (=!:uuid:1111/=!:uuid:2222)=!:uuid:2222/$is$ref/=bob
		//

		Graph tempGraphSet = MemoryGraphFactory.getInstance().openGraph();
		XdiInnerRoot innerRootSet = XdiCommonRoot.findCommonRoot(tempGraphSet).getInnerRoot(
				child1CloudNumber.getXDIAddress(), 
				child2CloudNumber.getXDIAddress(), 
				true);

		innerRootSet.getContextNode().setStatement(XDIStatement.fromComponents(
				child2CloudNumber.getXDIAddress(), 
				XDIDictionaryConstants.XDI_ADD_IS_REF, 
				child2CloudName.getXDIAddress()));

		MessageEnvelope meSet = new MessageEnvelope();
		Message mSet = meSet.createMessage(child1CloudNumber.getXDIAddress());
		mSet.setToPeerRootXDIArc(child1CloudNumber.getPeerRootXDIArc());
		mSet.createSetOperation(tempGraphSet);

		clientToChild1.send(meSet);

		// debug: let's look at child1's graph at this point

		System.out.println("Child 1's graph:");
		System.out.println();
		System.out.println(child1Graph.toString("XDI DISPLAY", null));

		// lookup

		Graph tempGraphGet = MemoryGraphFactory.getInstance().openGraph();
		XdiInnerRoot innerRootGet = XdiCommonRoot.findCommonRoot(tempGraphGet).getInnerRoot(
				child1CloudNumber.getXDIAddress(), 
				child2CloudNumber.getXDIAddress(), 
				true);

		innerRootGet.getContextNode().setStatement(XDIStatement.fromComponents(
				child2CloudNumber.getXDIAddress(), 
				XDIDictionaryConstants.XDI_ADD_IS_REF, 
				XDIConstants.XDI_ADD_COMMON_VARIABLE));

		MessageEnvelope meGet = new MessageEnvelope();
		Message mGet = meGet.createMessage(child1CloudNumber.getXDIAddress());
		mGet.setToPeerRootXDIArc(child1CloudNumber.getPeerRootXDIArc());
		mGet.createGetOperation(tempGraphGet);

		MessagingResponse mrGet = clientToChild1.send(meGet);

		// debug: let's look at the request result

		System.out.println("Result from child1's graph:");
		System.out.println();
		System.out.println(mrGet.getResultGraph().toString("XDI DISPLAY", null));

		// extra child2's cloud name from the result

		XDIAddress mrXDIAddress = mrGet.getResultGraph()
				.getDeepContextNode(XDIAddressUtil.concatXDIAddresses(innerRootGet.getXDIAddress(), child2CloudNumber.getXDIAddress()))
				.getRelation(XDIDictionaryConstants.XDI_ADD_IS_REF)
				.getTargetXDIAddress();

		CloudName mrCloudName = CloudName.fromXDIAddress(mrXDIAddress);

		// debug: print child2's cloud name

		System.out.println("Child 2's cloud name:");
		System.out.println();
		System.out.println(mrCloudName);
	}
}
