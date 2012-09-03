package jp.furyu.sample.glacier;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.glacier.AmazonGlacierClient;
import com.amazonaws.services.glacier.model.GetJobOutputRequest;
import com.amazonaws.services.glacier.model.GetJobOutputResult;

/**
 * �W���u�̌��ʂ��_�E�����[�h����T���v��
 * @author sumi
 */
public class JobResultDownloader {

    //�K���ɕύX���Ă�������
    public static String vaultName = "GLACIER_TEST";
    public static String jobId = "JobId";
    
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
            GetJobOutputRequest getJobOutputRequest = new GetJobOutputRequest()
                .withVaultName(vaultName)
            .    withJobId(jobId);
            GetJobOutputResult getJobOutputResult = client.getJobOutput(getJobOutputRequest);
            InputStream resultStream = getJobOutputResult.getBody();
            File downloadFile = new File("path to download file");
            createFileFromInputStream(resultStream,downloadFile);        
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private static void createFileFromInputStream(InputStream inputStream, File file) throws IOException {  
        
        byte[] buffer = new byte[2056];  
        int length = 0;  
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {  
            bos = new BufferedOutputStream(new FileOutputStream(file));  
            bis = new BufferedInputStream(inputStream);
            long startTime = System.currentTimeMillis();
            while ((length = bis.read(buffer)) >= 0) {  
                bos.write(buffer, 0, length);  
            }
            long endTime = System.currentTimeMillis();
            System.out.println("download complete. time:"+(endTime-startTime)+"msec.");
            bos.flush();
            bis.close();
            bos.close();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
