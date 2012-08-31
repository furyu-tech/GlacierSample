package jp.furyu.sample.glacier;

import java.io.File;
import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQSClient;

/**
 * High Level API�𗘗p����Vault����Archive���擾����T���v��
 * @author sumi
 */
public class HighLevelArchiveRetriever {

	//�K���ɕύX���Ă�������
	public static String vaultName = "GRLACIER_TEST";
	public static String archiveId = "ArchiveId";
    
    public static void main(String[] args) throws IOException {
    	ClientConfiguration clientConfiguration = new ClientConfiguration();
    	//Proxy���̐ݒ�(�K���ɕύX���Ă�������)
    	clientConfiguration.setProxyHost("xx.xx.xx.xx");
    	clientConfiguration.setProxyPort(0);
    	
    	//Glacier�N���C�A���g�̐���
    	AWSCredentials credentials = new PropertiesCredentials(VaultCreator.class.getResourceAsStream("AwsCredentials.properties"));
    	AmazonGlacierClient client = new AmazonGlacierClient(credentials,clientConfiguration);
    	client.setEndpoint("https://glacier.ap-northeast-1.amazonaws.com/");
    	try {
    		File downloadFile = new File("/Users/sumi/Documents/workspace_aws/GlacierTest/src/jp/furyu/sample/glacier/archive.dat");
    		AmazonSQSClient sqs = new AmazonSQSClient(credentials, clientConfiguration);
    		sqs.setEndpoint("https://sqs.ap-northeast-1.amazonaws.com");
    		AmazonSNSClient sns = new AmazonSNSClient(credentials, clientConfiguration);
    		sns.setEndpoint("https://sns.ap-northeast-1.amazonaws.com");
    		
    		ArchiveTransferManager archiveTransferManager = new ArchiveTransferManager(client,sqs,sns);
    		archiveTransferManager.download(vaultName, archiveId, downloadFile);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
