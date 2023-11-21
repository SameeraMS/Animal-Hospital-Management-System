package lk.ijse.ahms.qr;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.embed.swing.SwingNode;
import lk.ijse.ahms.controller.dashboard.PaymentFormController;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class QRScanner implements Runnable, ThreadFactory {

    public static Webcam webcam;
    public Thread thread;
    static boolean flag =true;
    private Executor executor = Executors.newSingleThreadExecutor(this);

    public SwingNode getVideoPanel() {
        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode);
        return swingNode;
    }

    private void createSwingContent(final SwingNode swingNode) {
        SwingUtilities.invokeLater(() -> {
            /*Swing中的调用摄像头方法*/
            webcam = Webcam.getDefault();
            System.out.println("get webcam");
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            WebcamPanel panel = new WebcamPanel(webcam);
            panel.setFPSDisplayed(true);
            panel.setDisplayDebugInfo(true);
            panel.setImageSizeDisplayed(true);
            panel.setMirrored(true);
            executor.execute(this);
            swingNode.setContent(panel);
        });
    }

    @Override
    public void run() {
        while (flag) {
            getWebcam(webcam);
        }
    }

     void getWebcam(Webcam webcam) {
        try{
            Thread.sleep(100);
            Result result = null;
            BufferedImage image = null;

            if (webcam.isOpen()) {
                if ((image = webcam.getImage()) == null) {
                    return;
                }

            }

//            assert  != null;
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap);
            System.out.println(result);
            if (result != null) {
                System.out.println(result.getText());
                PaymentFormController.scan = result.getText();

                flag = false;
                result = null;
                webcam.close();
                // result_field.setText(result.getText());
            }
        } catch (NotFoundException | InterruptedException e) {
          //  System.out.println(String.valueOf(e));
        }
    }

    @Override
    public Thread newThread(Runnable runnable) {
        thread = new Thread(runnable, "My Thread");
        thread.setDaemon(true);
        return thread;
    }

}
