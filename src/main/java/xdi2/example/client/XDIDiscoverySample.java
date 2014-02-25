package xdi2.example.client;

import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

public class XDIDiscoverySample {

	public static void main(String[] args) throws Exception {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult resultFromRegistry = xdiDiscoveryClient.discoverFromRegistry(XDI3Segment.create("=alice"), null);

		System.out.println("Cloud Number: " + resultFromRegistry.getCloudNumber());
		System.out.println("URI: " + resultFromRegistry.getXdiEndpointUri());

		if (resultFromRegistry.getXdiEndpointUri() != null && resultFromRegistry.getCloudNumber() != null) {

			XDIDiscoveryResult resultFromAuthority = xdiDiscoveryClient.discoverFromAuthority(resultFromRegistry.getXdiEndpointUri(), resultFromRegistry.getCloudNumber(), null);

			System.out.println("Cloud Number: " + resultFromAuthority.getCloudNumber());
			System.out.println("URI: " + resultFromAuthority.getXdiEndpointUri());
		}
	}
}
