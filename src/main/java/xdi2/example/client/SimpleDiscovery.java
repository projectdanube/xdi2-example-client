package xdi2.example.client;

import java.util.Arrays;

import xdi2.core.syntax.XDIAddress;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

/**
 * This example shows how the XDIDiscoveryClient class can be used to
 * find a cloud number and XDI endpoint URI, given a cloud name.
 */
public class SimpleDiscovery {

	public static void main(String[] args) throws Exception {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult resultFromRegistry = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=markus"));

		System.out.println("Result from registry:");
		System.out.println("Cloud Number: " + resultFromRegistry.getCloudNumber());
		System.out.println("Cloud Names: " + (resultFromRegistry.getCloudNames() == null ? null : Arrays.asList(resultFromRegistry.getCloudNames())));
		System.out.println("URI: " + resultFromRegistry.getXdiEndpointUri());
		System.out.println();

		if (resultFromRegistry.getXdiEndpointUri() != null && resultFromRegistry.getCloudNumber() != null) {

			XDIDiscoveryResult resultFromAuthority = xdiDiscoveryClient.discoverFromAuthority(resultFromRegistry.getXdiEndpointUri(), resultFromRegistry.getCloudNumber());

			System.out.println("Result from authority:");
			System.out.println("Cloud Number: " + resultFromAuthority.getCloudNumber());
			System.out.println("Cloud Names: " + (resultFromAuthority.getCloudNames() == null ? null : Arrays.asList(resultFromAuthority.getCloudNames())));
			System.out.println("URI: " + resultFromAuthority.getXdiEndpointUri());
		}
	}
}
