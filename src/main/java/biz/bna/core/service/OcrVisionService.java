package biz.bna.core.service;

import biz.bna.core.dto.OcrBodyDTO;
import biz.bna.core.response.Coordinate;
import biz.bna.core.response.OCRResult;
import biz.bna.core.response.OcrVisionResponse;
import biz.bna.core.response.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.List;

@Service
public class OcrVisionService {

    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${yandex.ocr.vision.url}")
    private String url;
    @Value("${yandex.iam.token}")
    private String iamToken;
    @Value("${yandex.folder.id}")
    private String folderId;

    public OCRResult readText(MultipartFile multipartFile) throws IOException {
        OcrBodyDTO body = new OcrBodyDTO();
        body.setModel("page");
        body.setLanguageCodes(List.of("*"));
        body.setMimeType(/*multipartFile.getContentType()*/"JPEG");
        body.setContent(multipartFile.getBytes());
        HttpHeaders headers = new HttpHeaders();
        // устанавливаем заголовок content-type
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(iamToken);
        headers.add("x-folder-id", folderId);
        headers.add("x-data-logging-enabled", "true");
        HttpEntity<OcrBodyDTO> entity = new HttpEntity<>(body, headers);
        // отправляем POST-запрос
        ResponseEntity<OCRResult> response = restTemplate.postForEntity(url, entity, OCRResult.class);
        // проверяем код статуса ответа
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public OutputStream writeBoundingBoxes(InputStream is, OCRResult ocrResult) throws IOException {
        BufferedImage image = ImageIO.read(is);
        Graphics2D g2d = image.createGraphics();
        Stroke stroke = new BasicStroke(6f);
        g2d.setColor(Color.BLUE);
        g2d.setStroke(stroke);
        ocrResult.getResult().getTextAnnotation().getBlocks().stream()
                .forEach(block -> block.getLines().stream()
                        .forEach(line -> {
                            line.getWords().stream()
                                    .forEach(word -> writeRect(g2d, word));
                        }));
        g2d.dispose();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        return os;
    }

    public void writeRect(Graphics2D graphics2D, Word word){
        int maxX = Integer.parseInt(word.getBoundingBox().getVertices().stream()
                .max(Comparator.comparing(Coordinate::getX))
                .get().getX());
        int maxY = Integer.parseInt(word.getBoundingBox().getVertices().stream()
                .max(Comparator.comparing(Coordinate::getX))
                .get().getY());
        int minX = Integer.parseInt(word.getBoundingBox().getVertices().stream()
                .min(Comparator.comparing(Coordinate::getX))
                .get().getX());
        int minY = Integer.parseInt(word.getBoundingBox().getVertices().stream()
                .min(Comparator.comparing(Coordinate::getX))
                .get().getY());
        graphics2D.drawRect(minX, minY, maxX-minX, maxY-minY);
    }
}
