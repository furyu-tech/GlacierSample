package jp.furyu.sample.glacier;

import java.io.IOException;
import java.util.Date;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.InitiateJobRequest;
import com.amazonaws.services.glacier.model.InitiateJobResult;
import com.amazonaws.services.glacier.model.JobParameters;

/**
 * Glacier��Vault����Archive�ꗗ���擾����T���v��
 * @author sumi
 */
public class VaultInventoryRetrieveJobInitiater {

	//�K���ɕύX���Ă�������
	public static String vaultName = "GRLACIER_TEST";
    
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
    		//�ꗗ�擾�W���u�̊J�n
    		InitiateJobRequest initiateJobRequest = 
    			new InitiateJobRequest().withVaultName(vaultName)
    				.withJobParameters(new JobParameters().withType("inventory-retrieval"));
    		InitiateJobResult initiateJobResult = client.initiateJob(initiateJobRequest);
    		String jobId = initiateJobResult.getJobId();
    		System.out.println("initiate inventory-retrieval job:jobId="+jobId+",startTime:"+new Date());
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
