package jp.furyu.sample.glacier;

import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DescribeJobRequest;
import com.amazonaws.services.glacier.model.DescribeJobResult;

/**
 * ジョブの状態を取得し表示するサンプル
 * @author sumi
 */
public class JobStatusGetter {

	//適当に変更してください
	public static String vaultName = "GRLACIER_TEST";
	public static String jobId = "JobId";
    
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
    		DescribeJobRequest describeJobRequest = new DescribeJobRequest().withVaultName(vaultName).withJobId(jobId);
    		DescribeJobResult describeJobResult = client.describeJob(describeJobRequest);
    		
    		System.out.println("JobId:"+jobId);
    		System.out.println("Completed:"+describeJobResult.getCompleted());
    		System.out.println("StatusCode:"+describeJobResult.getStatusCode());
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }    
}
