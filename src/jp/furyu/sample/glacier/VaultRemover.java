package jp.furyu.sample.glacier;

import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DeleteVaultRequest;

/**
 * Vaultを削除するサンプル
 * ただし、Vault内にArchiveが存在しない状態である必要があります。
 * @author sumi
 */
public class VaultRemover {

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
    		DeleteVaultRequest request = new DeleteVaultRequest()
    			.withVaultName(vaultName);
    		client.deleteVault(request);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
