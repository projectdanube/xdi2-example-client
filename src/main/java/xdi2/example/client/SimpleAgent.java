package xdi2.example.client;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import xdi2.client.agent.XDIAgent;
import xdi2.client.agent.impl.XDIBasicAgent;
import xdi2.core.ContextNode;
import xdi2.core.syntax.XDIAddress;

/**
 * This example shows how the XDIBasicAgent class can be used to perform XDI discovery and messaging
 * with very little code, to retrieve data given only an XDI address.
 */
public class SimpleAgent {

	public static void main(String[] args) throws Exception {

		LogManager.getLogger("xdi2").setLevel(Level.OFF);

		XDIAgent xdi = new XDIBasicAgent();

		ContextNode c = xdi.get(XDIAddress.create("=markus<#email>&"), null);
		System.out.println(c.getLiteral().getLiteralDataString());
	}
}