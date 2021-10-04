package slightlyspring.imgo.domain.til.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import slightlyspring.imgo.infra.S3FileUploader;

import java.io.IOException;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TilImageService {
    private final S3FileUploader s3FileUploader;

    public String uploadImage(MultipartFile multipartFile, String dirName) throws IOException {
        return s3FileUploader.upload(multipartFile, dirName);
    }

}
