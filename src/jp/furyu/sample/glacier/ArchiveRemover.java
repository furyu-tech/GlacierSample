package jp.furyu.sample.glacier;

import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DeleteArchiveRequest;

/**
 * IDを指定してVault内のArchiveを削除するサンプル
 * @author sumi
 */
public class ArchiveRemover {

	public static String vaultName = "GRLACIER_TEST";
	public static String archiveId = "Archive ID";
    
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
    		//アーカイブの削除リクエストの送信
    		client.deleteArchive(new DeleteArchiveRequest()
    			.withVaultName(vaultName)
    			.withArchiveId(archiveId));
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
