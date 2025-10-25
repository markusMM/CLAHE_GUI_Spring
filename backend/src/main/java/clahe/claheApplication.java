package clahe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import nu.pattern.OpenCV;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Base64;

@SpringBootApplication
@RestController
@RequestMapping("/api/image")
public class claheApplication {

    static { 
        OpenCV.loadLocally();
    }

    public static void main(String[] args) {
        SpringApplication.run(claheApplication.class, args);
    }

    @PostMapping(value = "/clahe", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutputResponse applyClahe(@RequestParam MultipartFile file,
                                     @RequestParam(defaultValue = "2.0") double clipLimit,
                                     @RequestParam(defaultValue = "8") int tileGridSize) throws IOException {

        // Bild in Mat konvertieren
        Mat img = Imgcodecs.imdecode(new MatOfByte(file.getBytes()), Imgcodecs.IMREAD_COLOR);

        // Konvertiere zu Lab Farbraum
        Mat imgLab = new Mat();
        Imgproc.cvtColor(img, imgLab, Imgproc.COLOR_BGR2Lab);

        // Splitte in Kanäle
        java.util.List<Mat> labChannels = new java.util.ArrayList<>();
        Core.split(imgLab, labChannels);

        // CLAHE auf das L Kanal anwenden
        Imgproc.createCLAHE(clipLimit, new Size(tileGridSize, tileGridSize))
            .apply(labChannels.get(0), labChannels.get(0));

        // Merge Channels wieder und zurück zum BGR Farbraum
        Core.merge(labChannels, imgLab);
        Mat imgClahe = new Mat();
        Imgproc.cvtColor(imgLab, imgClahe, Imgproc.COLOR_Lab2BGR);

        // Kodiere beide Bilder als Base64 String für JSON Response
        String originalBase64 = encodeMatToBase64(img);
        String claheBase64 = encodeMatToBase64(imgClahe);

        return new OutputResponse(originalBase64, claheBase64);
    }

    private String encodeMatToBase64(Mat img) {
        MatOfByte buf = new MatOfByte();
        Imgcodecs.imencode(".png", img, buf);
        return Base64.getEncoder().encodeToString(buf.toArray());
    }

    public static class OutputResponse {
        public String originalImage; // base64 PNG
        public String claheImage;    // base64 PNG

        public OutputResponse(String originalImage, String claheImage) {
            this.originalImage = originalImage;
            this.claheImage = claheImage;
        }
    }
}
