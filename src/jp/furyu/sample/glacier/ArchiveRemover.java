package jp.furyu.sample.glacier;

import java.io.IOException;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.DeleteArchiveRequest;

/**
 * ID���w�肵��Vault����Archive���폜����T���v��
 * @author sumi
 */
public class ArchiveRemover {

	public static String vaultName = "GRLACIER_TEST";
	public static String archiveId = "Archive ID";
    
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
    		//�A�[�J�C�u�̍폜���N�G�X�g�̑��M
    		client.deleteArchive(new DeleteArchiveRequest()
    			.withVaultName(vaultName)
    			.withArchiveId(archiveId));
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
}
