package xdi2.example.client;

import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.util.XDIClientUtil;
import xdi2.core.syntax.CloudNumber;
import xdi2.core.syntax.XDIAddress;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

public class RetrievePrivateKey {

	public static void sampleAuthenticateSecretToken() throws Xdi2ClientException {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=alice"));

		CloudNumber cloudNumber = xdiDiscoveryResult.getCloudNumber();
		URI xdiEndpointUri = xdiDiscoveryResult.getXdiEndpointUri();
		String secretToken = "alice";

		try {

			XDIClientUtil.authenticateSecretToken(cloudNumber, xdiEndpointUri, secretToken);
		} catch (Xdi2ClientException ex) {

			System.err.println("Not authenticated.");
			throw ex;
		}

		System.out.println("Successfully authenticated.");
	}

	public static void sampleRetrievePrivateKeys() throws Xdi2ClientException, GeneralSecurityException {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=alice"));

		CloudNumber cloudNumber = xdiDiscoveryResult.getCloudNumber();
		URI xdiEndpointUri = xdiDiscoveryResult.getXdiEndpointUri();
		String secretToken = "alice";

		PrivateKey signaturePrivateKey = XDIClientUtil.retrieveSignaturePrivateKey(cloudNumber, xdiEndpointUri, secretToken);
		PrivateKey encryptionPrivateKey = XDIClientUtil.retrieveEncryptionPrivateKey(cloudNumber, xdiEndpointUri, secretToken);

		System.out.println("Successfully retrieved signature private key: " + signaturePrivateKey);
		System.out.println("Successfully retrieved encryption private key: " + encryptionPrivateKey);
	}

	public static void main(String[] args) throws Exception {

		sampleAuthenticateSecretToken();
		sampleRetrievePrivateKeys();
	}
}
