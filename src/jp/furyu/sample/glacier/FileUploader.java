package jp.furyu.sample.glacier;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.transfer.ArchiveTransferManager;
import com.amazonaws.services.glacier.transfer.UploadResult;

/**
 * Glacier��Vault�Ƀt�@�C�����A�b�v���[�h����T���v��
 * @author sumi
 */
public class FileUploader {
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
    		File uploadFile = new File("path to upload file");
    		ArchiveTransferManager atm = new ArchiveTransferManager(client, credentials);
    		long startTime = System.currentTimeMillis();
            UploadResult result = atm.upload(vaultName, "Archive upload test :upload time = " + (new Date()), uploadFile);
            long endTime = System.currentTimeMillis();
            System.out.println("Archive ID: " + result.getArchiveId());
            System.out.println("Upload Time:" + (endTime - startTime) +" msec");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
}
