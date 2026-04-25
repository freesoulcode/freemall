package io.github.freesoulcode.bff.seller.interfaces.web;

import io.github.freesoulcode.bff.seller.infrastructure.config.storage.FileStorageService;
import io.github.freesoulcode.common.interfaces.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件上传", description = "文件上传接口")
@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @Operation(summary = "上传营业执照")
    @PostMapping("/license")
    public Result<String> uploadLicense(@RequestParam("file") MultipartFile file,
                                         @RequestParam("merchantId") Long merchantId) {
        String path = "merchant/" + merchantId + "/license";
        String url = fileStorageService.uploadFile(file, path);
        return Result.success(url);
    }
}
