package xdi2.example.client;

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
		System.out.println("URI: " + resultFromRegistry.getXdiEndpointUrl());
		System.out.println();

		if (resultFromRegistry.getXdiEndpointUrl() != null && resultFromRegistry.getCloudNumber() != null) {

			XDIDiscoveryResult resultFromAuthority = xdiDiscoveryClient.discoverFromAuthority(resultFromRegistry.getXdiEndpointUrl(), resultFromRegistry.getCloudNumber());

			System.out.println("Result from authority:");
			System.out.println("Cloud Number: " + resultFromAuthority.getCloudNumber());
			System.out.println("URI: " + resultFromAuthority.getXdiEndpointUrl());
		}
	}
}
