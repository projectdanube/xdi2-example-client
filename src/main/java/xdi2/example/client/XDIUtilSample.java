package xdi2.example.client;

import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;

import xdi2.client.exceptions.Xdi2ClientException;
import xdi2.client.util.XDIClientUtil;
import xdi2.core.syntax.CloudNumber;
import xdi2.core.syntax.XDIAddress;
import xdi2.discovery.XDIDiscoveryClient;
import xdi2.discovery.XDIDiscoveryResult;

public class XDIUtilSample {

	public static void sampleAuthenticateSecretToken() throws Xdi2ClientException {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=alice"), null);

		CloudNumber cloudNumber = xdiDiscoveryResult.getCloudNumber();
		URL xdiEndpointUrl = xdiDiscoveryResult.getXdiEndpointUrl();
		String secretToken = "alice";

		try {

			XDIClientUtil.authenticateSecretToken(cloudNumber, xdiEndpointUrl, secretToken);
		} catch (Xdi2ClientException ex) {

			System.err.println("Not authenticated.");
			throw ex;
		}

		System.out.println("Successfully authenticated.");
	}

	public static void sampleRetrievePrivateKeys() throws Xdi2ClientException, GeneralSecurityException {

		XDIDiscoveryClient xdiDiscoveryClient = XDIDiscoveryClient.DEFAULT_DISCOVERY_CLIENT;
		XDIDiscoveryResult xdiDiscoveryResult = xdiDiscoveryClient.discoverFromRegistry(XDIAddress.create("=alice"), null);

		CloudNumber cloudNumber = xdiDiscoveryResult.getCloudNumber();
		URL xdiEndpointUrl = xdiDiscoveryResult.getXdiEndpointUrl();
		String secretToken = "alice";

		PrivateKey signaturePrivateKey = XDIClientUtil.retrieveSignaturePrivateKey(cloudNumber, xdiEndpointUrl, secretToken);
		PrivateKey encryptionPrivateKey = XDIClientUtil.retrieveEncryptionPrivateKey(cloudNumber, xdiEndpointUrl, secretToken);

		System.out.println("Successfully retrieved signature private key: " + signaturePrivateKey);
		System.out.println("Successfully retrieved encryption private key: " + encryptionPrivateKey);
	}

	public static void main(String[] args) throws Exception {

		sampleAuthenticateSecretToken();
		sampleRetrievePrivateKeys();
	}
}
