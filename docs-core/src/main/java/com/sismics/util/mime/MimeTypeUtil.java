//package com.sismics.util.mime;
//
//import java.io.IOException;
//import java.net.URLConnection;
//import java.nio.file.Files;
//import java.nio.file.Path;
//
///**
// * Utility to check MIME types.
// *
// * @author bgamard
// */
//public class MimeTypeUtil {
//    /**
//     * Try to guess the MIME type of a file.
//     *
//     * @param file File to inspect
//     * @param name File name
//     * @return MIME type
//     * @throws IOException e
//     */
//    public static String guessMimeType(Path file, String name) throws IOException {
//        String mimeType = Files.probeContentType(file);
//
//
//        if (mimeType == null && name != null) {
//            mimeType = URLConnection.getFileNameMap().getContentTypeFor(name);
//        }
//
//        if (mimeType == null) {
//            return MimeType.DEFAULT;
//        }
//
//        return mimeType;
//    }
//
//    /**
//     * Get a file extension linked to a MIME type.
//     *
//     * @param mimeType MIME type
//     * @return File extension
//     */
//    public static String getFileExtension(String mimeType) {
//        switch (mimeType) {
//            case MimeType.APPLICATION_ZIP:
//                return "zip";
//            case MimeType.IMAGE_GIF:
//                return "gif";
//            case MimeType.IMAGE_JPEG:
//                return "jpg";
//            case MimeType.IMAGE_PNG:
//                return "png";
//            case MimeType.APPLICATION_PDF:
//                return "pdf";
//            case MimeType.OPEN_DOCUMENT_TEXT:
//                return "odt";
//            case MimeType.OFFICE_DOCUMENT:
//                return "docx";
//            case MimeType.TEXT_PLAIN:
//                return "txt";
//            case MimeType.TEXT_CSV:
//                return "csv";
//            case MimeType.VIDEO_MP4:
//                return "mp4";
//            case MimeType.VIDEO_WEBM:
//                return "webm";
//            default:
//                return "bin";
//        }
//    }
//}





package com.sismics.util.mime;

import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utility to check MIME types.
 *
 * @author bgamard
 */
public class MimeTypeUtil {
    /**
     * Try to guess the MIME type of a file.
     *
     * @param file File to inspect
     * @param name File name
     * @return MIME type
     * @throws IOException e
     */
    public static String guessMimeType(Path file, String name) throws IOException {
        // 1. 先按扩展名精确匹配（跨平台一致）
        String mimeType = guessMimeTypeByExtension(name);

        // 2. 如果扩展名无法识别，再用系统探测
        if (mimeType == null) {
            mimeType = Files.probeContentType(file);
        }

        // 3. 系统探测失败，再用 URLConnection 回退
        if (mimeType == null && name != null) {
            mimeType = URLConnection.getFileNameMap().getContentTypeFor(name);
        }

        if (mimeType == null) {
            return MimeType.DEFAULT;
        }
        return mimeType;
    }

    /**
     * 按文件扩展名识别 MIME 类型（确保跨平台一致性）
     */
    private static String guessMimeTypeByExtension(String name) {
        if (name == null) {
            return null;
        }
        String lowerName = name.toLowerCase();
        if (lowerName.endsWith(".csv")) {
            return MimeType.TEXT_CSV;
        }
        if (lowerName.endsWith(".txt")) {
            return MimeType.TEXT_PLAIN;
        }
        if (lowerName.endsWith(".pdf")) {
            return MimeType.APPLICATION_PDF;
        }
        if (lowerName.endsWith(".docx")) {
            return MimeType.OFFICE_DOCUMENT;
        }
        if (lowerName.endsWith(".xlsx")) {
            return MimeType.OFFICE_SHEET;
        }
        if (lowerName.endsWith(".pptx")) {
            return MimeType.OFFICE_PRESENTATION;
        }
        if (lowerName.endsWith(".odt")) {
            return MimeType.OPEN_DOCUMENT_TEXT;
        }
        if (lowerName.endsWith(".zip")) {
            return MimeType.APPLICATION_ZIP;
        }
        if (lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg")) {
            return MimeType.IMAGE_JPEG;
        }
        if (lowerName.endsWith(".png")) {
            return MimeType.IMAGE_PNG;
        }
        if (lowerName.endsWith(".gif")) {
            return MimeType.IMAGE_GIF;
        }
        if (lowerName.endsWith(".mp4")) {
            return MimeType.VIDEO_MP4;
        }
        if (lowerName.endsWith(".webm")) {
            return MimeType.VIDEO_WEBM;
        }
        return null;
    }

    /**
     * Get a file extension linked to a MIME type.
     *
     * @param mimeType MIME type
     * @return File extension
     */
    public static String getFileExtension(String mimeType) {
        switch (mimeType) {
            case MimeType.APPLICATION_ZIP:
                return "zip";
            case MimeType.IMAGE_GIF:
                return "gif";
            case MimeType.IMAGE_JPEG:
                return "jpg";
            case MimeType.IMAGE_PNG:
                return "png";
            case MimeType.APPLICATION_PDF:
                return "pdf";
            case MimeType.OPEN_DOCUMENT_TEXT:
                return "odt";
            case MimeType.OFFICE_DOCUMENT:
                return "docx";
            case MimeType.TEXT_PLAIN:
                return "txt";
            case MimeType.TEXT_CSV:
                return "csv";
            case MimeType.VIDEO_MP4:
                return "mp4";
            case MimeType.VIDEO_WEBM:
                return "webm";
            default:
                return "bin";
        }
    }
}
