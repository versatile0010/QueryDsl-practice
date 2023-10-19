package study.querydsl.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import study.querydsl.entity.FileData;
import study.querydsl.repository.FileDataRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class StorageService {
    private final FileDataRepository fileDataRepository;
    //private final String FOLDER_PATH = "C:\\study\\querydsl\\files\\";
    private final String FOLDER_PATH = "C:" + File.separator + "study"
            + File.separator + "querydsl"
            + File.separator + "files" + File.separator;
    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        log.info("upload file: {}", file.getOriginalFilename());
        String filePath = FOLDER_PATH + file.getOriginalFilename();
        FileData fileData = fileDataRepository.save(
                FileData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .filePath(filePath)
                        .build()
        );

        // 파일 경로
        file.transferTo(new File(filePath));

        return "file uploaded successfully! filePath : " + filePath;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        FileData fileData = fileDataRepository.findByName(fileName)
                .orElseThrow(RuntimeException::new);

        String filePath = fileData.getFilePath();

        log.info("download fileData: {}", fileData);
        log.info("download filePath: {}", filePath);

        return Files.readAllBytes(new File(filePath).toPath());
    }
}
