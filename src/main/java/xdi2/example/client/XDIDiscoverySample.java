package xdi2.example.client;

import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

public class XDIDiscoverySample {

	public static void main(String[] args) throws Exception {

		XDIDiscoveryClient discovery = new XDIDiscoveryClient();
		discovery.setRegistryXdiClient(XDIDiscoveryClient.NEUSTAR_PROD_DISCOVERY_XDI_CLIENT);

		XDIDiscoveryResult resultFromRegistry = discovery.discoverFromRegistry(XDI3Segment.create("=markus"), null);

		System.out.println("Cloud Number: " + resultFromRegistry.getCloudNumber());
		System.out.println("URI: " + resultFromRegistry.getXdiEndpointUri());

		if (resultFromRegistry.getXdiEndpointUri() != null && resultFromRegistry.getCloudNumber() != null) {

			XDIDiscoveryResult resultFromAuthority = discovery.discoverFromAuthority(resultFromRegistry.getXdiEndpointUri(), resultFromRegistry.getCloudNumber(), null);

			System.out.println("Cloud Number: " + resultFromAuthority.getCloudNumber());
			System.out.println("URI: " + resultFromAuthority.getXdiEndpointUri());
		}
	}
}
