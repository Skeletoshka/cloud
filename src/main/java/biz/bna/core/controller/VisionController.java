package biz.bna.core.controller;

import biz.bna.core.response.OCRResult;
import biz.bna.core.response.OcrVisionResponse;
import biz.bna.core.response.StandardResponse;
import biz.bna.core.service.OcrVisionService;
import biz.bna.core.view.BucketView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/apps",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class VisionController {

    @Autowired
    private OcrVisionService ocrVisionService;

    @RequestMapping(value = "vision/response", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> upload(@RequestBody MultipartFile file){
        try {
            OCRResult ocrResult = ocrVisionService.readText(file);
            // Конвертация OutputStream в InputStream
            InputStream inputStream = new ByteArrayInputStream(((ByteArrayOutputStream)ocrVisionService
                    .writeBoundingBoxes(new ByteArrayInputStream(file.getBytes()), ocrResult)).toByteArray());
            // Создание ResponseEntity с StreamingResponseBody для возврата PDF файла клиенту
            StreamingResponseBody stream = os -> {
                readAndWrite(inputStream, os);
            };
            return ResponseEntity
                    .ok()
                    .headers(getHttpHeadersForDownload("generatedDocument.pdf"))
                    .body(stream);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private HttpHeaders getHttpHeadersForDownload(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.builder("attachment")
                .filename(fileName, StandardCharsets.UTF_8)
                .build());
        return headers;
    }

    private void readAndWrite(final InputStream is, OutputStream os)
            throws IOException {
        // по 2 Кб
        byte[] data = new byte[2048];
        int read = 0;
        while ((read = is.read(data)) > 0) {
            os.write(data, 0, read);
        }
        os.flush();
    }
}
