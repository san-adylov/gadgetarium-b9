package peaksoft.house.gadgetariumb9.apis;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import peaksoft.house.gadgetariumb9.services.serviceImpl.S3FileService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/files")
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "File API", description = "Endpoints for uploading, downloading, and deleting files")
public class S3FileApi {

    private final S3FileService s3FileService;

    @Operation(summary = "Upload file", description = "Method to upload a file to the server")
    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam(name = "file", required = false) MultipartFile file) throws IOException {
        return new ResponseEntity<>(s3FileService.uploadFile(file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    @Operation(summary = "Download file", description = "Method to download a file from the server")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = s3FileService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    @Operation(summary = "Delete file", description = "Method to delete a file from the server")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        boolean deletionSuccessful = s3FileService.deleteFile(fileName);
        if (deletionSuccessful) {
            return ResponseEntity.ok(fileName + " removed...");
        } else {
            return ResponseEntity.status(500).body("Error deleting " + fileName);
        }
    }
}

