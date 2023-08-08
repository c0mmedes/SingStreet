package com.ssafy.singstreet.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {
    @Value("${aws.s3.bucketName}")
    private String bucketName;
    @Value("${aws.region}")
    private String region;

    private final S3Client s3Client;

    public InputStream downloadFileFromS3(String s3Url) {
        try {
            ResponseInputStream<GetObjectResponse> responseInputStream = s3Client.getObject(GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Url)
                    .build());
            return responseInputStream;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to download file from S3");
        }
    }


    public ResponseEntity<InputStreamResource> downloadFile(String filename) {
        try {
            InputStream inputStream = downloadFileFromS3(filename);

            // Obtain the content type based on the filename or configure it as needed
            String contentType = "application/octet-stream";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("attachment", filename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to download file");
        }
    }

//    private final AmazonS3 amazonS3;
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    public ResponseEntity<UrlResource> downloadImage(String originalFilename) {
//        UrlResource urlResource = new UrlResource(amazonS3.getUrl(bucket, originalFilename));
//
//        String contentDisposition = "attachment; filename=\"" +  originalFilename + "\"";
//
//        // header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
//                .body(urlResource);
//
//    }


//    @Value("${aws.s3.bucketName}")
//    private String bucketName;
//
//    private final S3Client s3Client;
//
//
//    public String uploadFile(MultipartFile file) {
//        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
//        try {
//            s3Client.putObject(PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fileName)
//                    .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
//            return fileName;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to upload file to S3");
//        }
//    }
public String uploadFile(MultipartFile file) {
    String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
    try {
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType("image/png")
                .build(), RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
        String S3url = "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
        return S3url;
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException("Failed to upload file to S3");
    }
}
}
