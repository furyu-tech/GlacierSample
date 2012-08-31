package jp.furyu.sample.glacier;

import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DeleteVaultRequest;

/**
 * Vault���폜����T���v��
 * �������AVault����Archive�����݂��Ȃ���Ԃł���K�v������܂��B
 * @author sumi
 */
public class VaultRemover {

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
    		DeleteVaultRequest request = new DeleteVaultRequest()
    			.withVaultName(vaultName);
    		client.deleteVault(request);
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
