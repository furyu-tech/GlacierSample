package jp.furyu.sample.glacier;

import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.CreateVaultRequest;
import com.amazonaws.services.glacier.model.SetVaultNotificationsRequest;
import com.amazonaws.services.glacier.model.VaultNotificationConfig;

/**
 * GlacierのVaultを作成するサンプル
 * @author sumi
 */
public class VaultCreator {
	
	//適当に変更してください
	public static String vaultName = "GRLACIER_TEST";
	public static String snsTopicArn = "ARN OF SNS TOPIC";
	    
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
    		//Vaultの作成
        	CreateVaultRequest createVaultRequest = new CreateVaultRequest().withVaultName(vaultName);
            client.createVault(createVaultRequest);
            
            //SNS通知設定(SNSトピックのARNの設定と、通知対象イベントの設定)
            VaultNotificationConfig vaultNotificationConfig = 
            	new VaultNotificationConfig().withSNSTopic(snsTopicArn)
            	.withEvents("ArchiveRetrievalCompleted","InventoryRetrievalCompleted");
            SetVaultNotificationsRequest setVaultNotificationsRequest = 
            	new SetVaultNotificationsRequest().withVaultName(vaultName)
            	.withVaultNotificationConfig(vaultNotificationConfig);
            client.setVaultNotifications(setVaultNotificationsRequest);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
}
