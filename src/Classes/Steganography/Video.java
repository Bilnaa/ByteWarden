package Classes.Steganography;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.ffmpeg.global.avutil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * The {@code Video} class provides methods to encode and decode messages within video files using steganography.
 * 
 * <p><strong>WARNING:</strong> This implementation is currently not functional.</p>
 * 
 * <p>This class uses FFmpeg for video processing and Java2D for image manipulation. The message is encoded into the
 * video frames by modifying the least significant bit of the pixel values.</p>
 * 
 * <p>Usage:</p>
 * <pre>
 * {@code
 * File inputFile = new File("path/to/input/video.mp4");
 * File outputFile = new File("path/to/output/video.mp4");
 * Video videoSteganography = new Video(inputFile, outputFile);
 * 
 * // Encode a message
 * videoSteganography.encode("Your secret message");
 * 
 * // Decode the message
 * String decodedMessage = videoSteganography.decode();
 * }
 * </pre>
 * 
 * <p>Note: Ensure that the input and output files are valid and accessible.</p>
 * 
 * @see Steganography
 */
public class Video extends Steganography {
    private File inputFile;
    private File outputFile;
    private static final String END_MARKER = "###END###";
    private static final int PIXELS_PER_FRAME = 1000;

    static {
        avutil.av_log_set_level(avutil.AV_LOG_QUIET); // Suppress FFmpeg logs
    }

    // WARNING: This implementation is currently not functional.
    public Video(File inputFile, File outputFile) throws IOException {
        if (!inputFile.exists()) {
            throw new IOException("Input file does not exist: " + inputFile.getAbsolutePath());
        }
        this.inputFile = inputFile;
        this.outputFile = outputFile;

        File outputDir = outputFile.getParentFile();
        if (outputDir != null && !outputDir.exists()) {
            if (!outputDir.mkdirs()) {
                throw new IOException("Failed to create output directory: " + outputDir.getAbsolutePath());
            }
        }
    }

    @Override
    public void encode(String message) throws IOException {
        if (message == null || message.isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }

        message = message + END_MARKER; // Append end marker to the message
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        System.out.println("[DEBUG] Encoding message: '" + message + "'");
        System.out.println("[DEBUG] Message bytes length: " + messageBytes.length);

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputFile);
             Java2DFrameConverter converter = new Java2DFrameConverter()) {

            grabber.start();
            FFmpegFrameRecorder recorder = createRecorder(grabber);

            try {
                recorder.start();
                int totalBits = messageBytes.length * 8; // Total bits to encode
                int encodedBits = 0;
                Frame frame;
                int frameCount = 0;
                boolean messageDone = false;

                while ((frame = grabber.grab()) != null) {
                    if (frame.image != null) {
                        BufferedImage img = converter.convert(frame);
                        
                        if (!messageDone && encodedBits < totalBits) {
                            for (int i = 0; i < PIXELS_PER_FRAME && encodedBits < totalBits; i++) {
                                int x = (i % (img.getWidth() - 1)) + 1;
                                int y = (i / (img.getWidth() - 1)) + 1;
                                
                                int byteIndex = encodedBits / 8;
                                int bitIndex = 7 - (encodedBits % 8);
                                int bit = (messageBytes[byteIndex] >> bitIndex) & 1;
                                
                                embedBitInPixel(img, x, y, 0, bit); // Embed bit in pixel
                                encodedBits++;
                            }
                            
                            if (encodedBits >= totalBits) {
                                messageDone = true;
                            }
                        }

                        frame = converter.convert(img);
                        recorder.record(frame);
                        frameCount++;
                        
                        if (frameCount % 30 == 0) {
                            System.out.println("[DEBUG] Processed frames: " + frameCount + ", Encoded bits: " + encodedBits + "/" + totalBits);
                        }
                    }
                }

                System.out.println("[DEBUG] Total frames processed: " + frameCount);
                System.out.println("[DEBUG] Total bits encoded: " + encodedBits);
                
                recorder.stop();
                recorder.release();

            } catch (Exception e) {
                cleanupOnError(recorder);
                throw e;
            }
        }
    }

    private FFmpegFrameRecorder createRecorder(FFmpegFrameGrabber grabber) {
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(
            outputFile,
            grabber.getImageWidth(),
            grabber.getImageHeight()
        );
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        recorder.setFormat("mp4");
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setVideoQuality(0);
        recorder.setVideoOption("preset", "veryslow");
        recorder.setVideoOption("crf", "18");
        return recorder;
    }

    @Override
    public String decode() throws IOException {
        StringBuilder binaryMessage = new StringBuilder();
        byte[] messageBytes = new byte[1024 * 1024];
        int messageLength = 0;
        int bitsRead = 0;

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(outputFile);
             Java2DFrameConverter converter = new Java2DFrameConverter()) {

            grabber.start();
            System.out.println("[DEBUG] Starting decoding process");
            
            Frame frame;
            int frameCount = 0;
            byte currentByte = 0;
            int bitCount = 0;
            while ((frame = grabber.grab()) != null) {
                if (frame.image != null) {
                    BufferedImage img = converter.convert(frame);
                    
                    for (int i = 0; i < PIXELS_PER_FRAME; i++) {
                        int x = (i % (img.getWidth() - 1)) + 1;
                        int y = (i / (img.getWidth() - 1)) + 1;
                        
                        int bit = extractBitFromPixel(img, x, y, 0); // Extract bit from pixel
                        currentByte = (byte) ((currentByte << 1) | bit);
                        bitCount++;
                        bitsRead++;

                        if (bitCount == 8) {
                            messageBytes[messageLength++] = currentByte;
                            currentByte = 0;
                            bitCount = 0;

                            String currentMessage = new String(messageBytes, 0, messageLength, StandardCharsets.UTF_8);
                            if (currentMessage.contains(END_MARKER)) {
                                String result = currentMessage.substring(0, currentMessage.indexOf(END_MARKER));
                                System.out.println("[DEBUG] Found end marker at length: " + messageLength);
                                return result;
                            }
                        }
                    }

                    frameCount++;
                    if (frameCount % 30 == 0) {
                        System.out.println("[DEBUG] Processed frames: " + frameCount + ", Bits read: " + bitsRead);
                    }
                }
            }

            System.out.println("[DEBUG] Total frames processed: " + frameCount);
            System.out.println("[DEBUG] Total bits read: " + bitsRead);
            System.out.println("[DEBUG] Message length: " + messageLength);
            
            throw new IOException("Failed to decode message: End marker not found or message corrupted");
        }
    }

    private void embedBitInPixel(BufferedImage img, int x, int y, int channel, int bit) {
        int rgb = img.getRGB(x, y);
        int[] channels = {
            (rgb >> 16) & 0xFF,
            (rgb >> 8) & 0xFF,
            rgb & 0xFF
        };
        
        channels[channel] = (channels[channel] & ~1) | bit; // Embed bit in the specified channel
        
        rgb = (channels[0] << 16) | (channels[1] << 8) | channels[2];
        img.setRGB(x, y, rgb);
    }

    private int extractBitFromPixel(BufferedImage img, int x, int y, int channel) {
        int rgb = img.getRGB(x, y);
        int[] channels = {
            (rgb >> 16) & 0xFF,
            (rgb >> 8) & 0xFF,
            rgb & 0xFF
        };
        return channels[channel] & 1; // Extract bit from the specified channel
    }

    private void cleanupOnError(FFmpegFrameRecorder recorder) {
        try {
            recorder.stop();
            recorder.release();
        } catch (Exception ex) {
        }
        if (outputFile.exists()) {
            outputFile.delete(); // Delete output file on error
        }
    }
}