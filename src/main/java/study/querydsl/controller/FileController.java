package study.querydsl.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import study.querydsl.service.StorageService;

import java.io.IOException;

@Tag(name = "이미지 파일 업로드 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final StorageService storageService;
    @PostMapping
    @Operation(summary = "단일 이미지 업로드", description =
                    """
                    [RequestParameter] 타입: MultipartFile, 이름: image
                    
                    [Response] upload file 이름  
                    """)
    public ResponseEntity<?> uploadImageToFileSystem(@RequestParam("image") MultipartFile file) throws IOException {
        log.info("[file 을 upload 합니다.]");
        String uploadImage = storageService.uploadImageToFileSystem(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImageToFileSystem(@PathVariable("fileName") String fileName) throws IOException {
        log.info("[file 을 download 합니다.]");
        byte[] downloadImage = storageService.downloadImageFromFileSystem(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage);
    }
}
