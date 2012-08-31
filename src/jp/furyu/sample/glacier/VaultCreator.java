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
 * Glacier��Vault���쐬����T���v��
 * @author sumi
 */
public class VaultCreator {
	
	//�K���ɕύX���Ă�������
	public static String vaultName = "GRLACIER_TEST";
	public static String snsTopicArn = "ARN OF SNS TOPIC";
	    
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
    		//Vault�̍쐬
        	CreateVaultRequest createVaultRequest = new CreateVaultRequest().withVaultName(vaultName);
            client.createVault(createVaultRequest);
            
            //SNS�ʒm�ݒ�(SNS�g�s�b�N��ARN�̐ݒ�ƁA�ʒm�ΏۃC�x���g�̐ݒ�)
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
