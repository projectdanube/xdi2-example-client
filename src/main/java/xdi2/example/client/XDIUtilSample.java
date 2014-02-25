package xdi2.example.client;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.util.XDIClientUtil;
import xdi2.core.xri3.CloudNumber;
import xdi2.core.xri3.XDI3Segment;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

public class XDIUtilSample {

	public static void sampleAuthenticateSecretToken() throws Xdi2ClientException, GeneralSecurityException {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discover(XDI3Segment.create("=alice"), null);

		CloudNumber cloudNumber = xdiDiscoveryResult.getCloudNumber();
		String xdiEndpoint = xdiDiscoveryResult.getXdiEndpointUri();
		String secretToken = "alice";

		XDIClientUtil.authenticateSecretToken(cloudNumber, xdiEndpoint, secretToken);

		System.out.println("Successfully authenticated.");
	}

	public static void sampleRetrievePrivateKeys() throws Xdi2ClientException, GeneralSecurityException {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discover(XDI3Segment.create("=alice"), null);

		CloudNumber cloudNumber = xdiDiscoveryResult.getCloudNumber();
		String xdiEndpoint = xdiDiscoveryResult.getXdiEndpointUri();
		String secretToken = "alice";

		PrivateKey signaturePrivateKey = XDIClientUtil.retrieveSignaturePrivateKey(cloudNumber, xdiEndpoint, secretToken);
		PrivateKey encryptionPrivateKey = XDIClientUtil.retrieveEncryptionPrivateKey(cloudNumber, xdiEndpoint, secretToken);

		System.out.println("Successfully retrieved signature private key: " + signaturePrivateKey);
		System.out.println("Successfully retrieved encryption private key." + encryptionPrivateKey);
	}

	public static void main(String[] args) throws Exception {

		sampleAuthenticateSecretToken();
		sampleRetrievePrivateKeys();
	}
}
