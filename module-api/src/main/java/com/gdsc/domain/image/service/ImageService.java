package com.gdsc.domain.image.service;

import com.gdsc.common.exception.ApplicationErrorException;
import com.gdsc.domain.image.entity.Image;
import com.gdsc.domain.image.repo.ImageRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.gdsc.common.exception.ApplicationErrorType.FAIL_TO_UPLOAD_IMAGE;

@Service
@RequiredArgsConstructor
public class ImageService {
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    private final Storage storage;
    private final ImageRepository imageRepository;

    public Image upload(MultipartFile multipartFile) {
        String uuid = UUID.randomUUID().toString();
        String ext = multipartFile.getContentType();

        String imgUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, uuid)
                .setContentType(ext).build();
        try {
            storage.create(blobInfo, multipartFile.getInputStream());

            Image image = Image.builder()
                    .imageUrl(imgUrl)
                    .uuid(uuid)
                    .build();

            return imageRepository.save(image);
        } catch (IOException e) {
            throw new ApplicationErrorException(FAIL_TO_UPLOAD_IMAGE, "이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    public void delete(Image image) {
        BlobId blobId = BlobId.of(bucketName, image.getUuid());
        storage.delete(blobId);

        imageRepository.delete(image);
    }
}
