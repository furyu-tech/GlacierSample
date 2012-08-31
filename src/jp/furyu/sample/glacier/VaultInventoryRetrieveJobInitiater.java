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
 * GlacierのVault内のArchive一覧を取得するサンプル
 * @author sumi
 */
public class VaultInventoryRetrieveJobInitiater {

	//適当に変更してください
	public static String vaultName = "GRLACIER_TEST";
    
    public static void main(String[] args) throws IOException {
    	ClientConfiguration clientConfiguration = new ClientConfiguration();
    	//Proxy情報の設定(適当に変更してください)
    	clientConfiguration.setProxyHost("xx.xx.xx.xx");
    	clientConfiguration.setProxyPort(0);
    	
    	//Glacierクライアントの生成
    	AWSCredentials credentials = new PropertiesCredentials(VaultCreator.class.getResourceAsStream("AwsCredentials.properties"));
    	AmazonGlacierClient client = new AmazonGlacierClient(credentials,clientConfiguration);
    	client.setEndpoint("https://glacier.ap-northeast-1.amazonaws.com/");
    	try {
    		//一覧取得ジョブの開始
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
