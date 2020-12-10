package com.luv2code.demo.services;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.logs.model.DataAlreadyAcceptedException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.luv2code.demo.exception.FileNotProvidedException;
import com.luv2code.demo.exception.MyFileNotFoundException;

@Service
public class AmazonS3ClientServiceImpl {

	private static final String SUFFIX = "/";


	AWSCredentials credentials = new BasicAWSCredentials(
			"AKIAWKOLEE3IJ7E4L6ZJ", 
			"W4kdQW8beoVXDEKfr3HdUnjm8ulYj/KlWQgUGQ3W");


	private AmazonS3 s3client=new AmazonS3Client(credentials);
	
	String defaultBucketName = "scanmykyc-ab";

	private final long EXPIRATION_TIME = 1000 * 60 * 60;

	public List<Bucket> getAllBuckets() {

		return s3client.listBuckets();
	}


	public String uploadFile(MultipartFile uploadFile) throws IOException {

		File file = convertMultiPartToFile(uploadFile);

		String url = null;

		String folderName = "testfolder";  // this one should be read from request - in this case user's mobile number/Aadhar Number
		
		PutObjectResult putObjectResult;
		
		 if ( uploadFile != null) {
			 
			 createFolder(defaultBucketName, folderName, s3client);
			 putObjectResult = s3client.putObject(new PutObjectRequest(defaultBucketName, 
					 			folderName + SUFFIX + uploadFile.getOriginalFilename(), file).
					 			withCannedAcl(CannedAccessControlList.PublicRead));
			 
			 url = generatePresignedUrl(folderName);

	        } else {

	                throw new FileNotProvidedException("Please Attach the file");
	            }

		return url;
		//return "Successfully uploaded"+fileName+"file";

	}

	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);

		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		try {
			// create a PutObjectRequest passing the folder name suffixed by /
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
					folderName + SUFFIX, emptyContent, metadata);

			// send request to S3 to create folder
			client.putObject(putObjectRequest);

		}
		catch (AmazonServiceException e) {
			e.printStackTrace();
		}
		
		catch(AmazonClientException e) {
			e.printStackTrace();
		}
	}

	/**

	 *To Convert Multipart File into File

	 */

	private File convertMultiPartToFile(MultipartFile file) throws IOException {

		File convFile = new File(file.getOriginalFilename());

		FileOutputStream fos = new FileOutputStream(convFile);

		fos.write(file.getBytes());

		fos.close();

		return convFile;

	}

	/**

	 *To generate download URL

	 */

	public String generatePresignedUrl(String folderName) {

		//   AmazonS3 s3Client = gets3Client();

		Date expiration = new Date();

		long timestamp = expiration.getTime();

		timestamp += EXPIRATION_TIME;// link expiration time is 1 hour

		expiration.setTime(timestamp);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(

				defaultBucketName, folderName).withMethod(HttpMethod.GET).withExpiration(expiration);

		URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);

		if(url.toString()==null&&url.toString().isEmpty()){
			
			 throw new MyFileNotFoundException("File/Folder doesnt exist");
		}
		
		return url.toString();

	}

}

