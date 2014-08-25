package xdi2.example.client;

import xdi2.core.syntax.XDIAddress;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

public class XDIDiscoverySample {

	public static void main(String[] args) throws Exception {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult resultFromRegistry = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=alice"), null);

		System.out.println("Cloud Number: " + resultFromRegistry.getCloudNumber());
		System.out.println("URI: " + resultFromRegistry.getXdiEndpointUrl());

		if (resultFromRegistry.getXdiEndpointUrl() != null && resultFromRegistry.getCloudNumber() != null) {

			XDIDiscoveryResult resultFromAuthority = xdiDiscoveryClient.discoverFromAuthority(resultFromRegistry.getXdiEndpointUrl(), resultFromRegistry.getCloudNumber(), null);

			System.out.println("Cloud Number: " + resultFromAuthority.getCloudNumber());
			System.out.println("URI: " + resultFromAuthority.getXdiEndpointUrl());
		}
	}
}
