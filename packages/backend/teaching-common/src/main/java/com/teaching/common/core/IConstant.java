package com.teaching.common.core;

import com.teaching.common.helper.SpringHelper;

/**
 * @Description：
 * @Author：sacher
 * @Create：2023/5/6 16:23
 **/
public interface IConstant {

    /**
     * 是否为 error 的端点
     * @param uri
     * @return
     */
    static boolean isErrorEndpoint(String uri) {
        String contextPath = SpringHelper.contextPath();
        return (contextPath + "/error").equals(uri);
    }

    /**
     * 是否为 spring cloud actuator 的端点
     * @param uri
     * @return
     */
    static boolean isActuatorEndpoint(String uri) {
        String contextPath = SpringHelper.contextPath();
        return null != uri && ((contextPath + "/actuator").equals(uri) || uri.startsWith(contextPath + "/actuator/"));
    }

    /**
     * 是否为 websocket 的端点
     * @param uri
     * @return
     */
    static boolean isStreamEndpoint(String uri) {
        String contextPath = SpringHelper.contextPath();
        return null != uri && ((contextPath + "/tunnel").equals(uri) || (contextPath + "/tunnel/sjs").equals(uri) || uri.endsWith("/tunnel.sse"));
    }

    /**
     * 是否是静态资源
     * @param uri
     * @return
     */
    static boolean isStaticsEndpoint(String uri) {
        return isResourceRequest(uri) || isImageRequest(uri) || isAudioRequest(uri) || isVideoRequest(uri) || uri.contains("excel");
    }

    /**
     * 是否为图片请求
     * @param uri
     * @return
     */
    static boolean isImageRequest(String uri) {
        if (null != uri) {
            String uriM = uri.toLowerCase();
            return uriM.endsWith(".svg")
                    || uriM.endsWith(".tif")
                    || uriM.endsWith(".tiff")
                    || uriM.endsWith(".png")
                    || uriM.endsWith(".jpg")
                    || uriM.endsWith(".bmp")
                    || uriM.endsWith(".gif")
                    || uriM.endsWith(".ico")
                    || uriM.endsWith(".jpeg");
        }
        return false;
    }

    /**
     * 是否为音频请求
     * @param uri
     * @return
     */
    static boolean isAudioRequest(String uri) {
        if (null != uri) {
            String uriM = uri.toLowerCase();
            return uriM.endsWith(".mp3")
                    || uriM.endsWith(".wav")
                    || uriM.endsWith(".ogg")
                    || uriM.endsWith(".aac");
        }
        return false;
    }

    /**
     * 是否为视频请求
     * @param uri
     * @return
     */
    static boolean isVideoRequest(String uri) {
        if (null != uri) {
            String uriM = uri.toLowerCase();
            return uriM.endsWith(".mp4")
                    || uriM.endsWith(".webm");
        }
        return false;
    }

    /**
     * 文件资源请求
     * @param uri
     * @return
     */
    static boolean isResourceRequest(String uri) {
        if (null != uri) {
            String uriM = uri.toLowerCase();
            return uriM.endsWith(".html")
                    ||uriM.endsWith(".css")
                    || uriM.endsWith(".js")

                    || uriM.endsWith(".pdf")

                    || uriM.endsWith(".ttf")
                    || uriM.endsWith(".woff")
                        || uriM.contains("swagger");
        }
        return false;
    }
}
