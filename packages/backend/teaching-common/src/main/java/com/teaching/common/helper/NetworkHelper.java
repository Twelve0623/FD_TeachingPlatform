package com.teaching.common.helper;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * 网络信息工具类
 *
 * @author sacher
 * */
public final class NetworkHelper {

    private static final Logger LOG = LoggerFactory.getLogger(NetworkHelper.class);

    private NetworkHelper() {
    }

    private static final List<InetAddress> LOCAL_IPS = Lists.newArrayList();
    private static final List<InetAddress> INNER_IPS = Lists.newArrayList();
    private static final List<InetAddress> OUTER_IPS = Lists.newArrayList();

    static {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (isFictitious(networkInterface.getDisplayName(), networkInterface.getName())) {
                    continue;
                }
                Enumeration<InetAddress> inAddresses = networkInterface.getInetAddresses();
                while (inAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inAddresses.nextElement();
                    if (inetAddress instanceof Inet4Address && null != inetAddress.getHostAddress()) {
                        //本机的地址，以127开头的IP地址
                        if (inetAddress.isLoopbackAddress()) {
                            LOCAL_IPS.add(inetAddress);
                        }
                        //地区本地地址，内网地址以10，172，192开头的IP地址
                        else if (inetAddress.isSiteLocalAddress() && !inetAddress.isAnyLocalAddress()) {
                            INNER_IPS.add(inetAddress);
                        }
                        //其他地址可认为外网地址
                        if (!inetAddress.isAnyLocalAddress()
                                && !inetAddress.isLoopbackAddress()
                                && !inetAddress.isLinkLocalAddress()
                                && !inetAddress.isSiteLocalAddress()) {
                            OUTER_IPS.add(inetAddress);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("To find device all address error.....", e);
        }
    }

    @SuppressWarnings("unused") //以网卡顺序排序
    private static String MIN_LB_IP, MAX_LB_IP, MIN_INNER_IP, MAX_INNER_IP, MIN_OUTER_IP, MAX_OUTER_IP;

    /**
     * 最小本地IP
     **/
    public static String minLocalIp() {
        return machineIp(MIN_LB_IP, () -> CollectsHelper.head(LOCAL_IPS));
    }

    /**
     * 最大本地IP
     **/
    public static String maxLocalIp() {
        return machineIp(MAX_LB_IP, () -> CollectsHelper.head(LOCAL_IPS));
    }

    /**
     * 最小内网IP
     **/
    public static String minInnerIp() {
        return machineIp(MIN_INNER_IP, () -> CollectsHelper.head(INNER_IPS));
    }

    /**
     * 最大内网IP
     **/
    public static String maxInnerIp() {
        return machineIp(MAX_INNER_IP, () -> CollectsHelper.end(INNER_IPS));
    }

    /**
     * 最小外网IP
     **/
    public static String minOuterIp() {
        return machineIp(MIN_OUTER_IP, () -> CollectsHelper.head(OUTER_IPS));
    }

    /**
     * 最大外网IP
     **/
    public static String maxOuterIp() {
        return machineIp(MAX_OUTER_IP, () -> CollectsHelper.end(OUTER_IPS));
    }

    public static List<InetAddress> innerNetAddressGet() {
        return Collections.unmodifiableList(INNER_IPS);
    }

    /**
     * 获取本机网卡IP
     **/
    public static String machineIp() {
        String ip = NetworkHelper.maxInnerIp();
        if (noMappingIp(ip)) {
            ip = NetworkHelper.minInnerIp();
        }
        if (noMappingIp(ip)) {
            ip = NetworkHelper.maxOuterIp();
        }
        if (noMappingIp(ip)) {
            ip = NetworkHelper.minOuterIp();
        }
        if (noMappingIp(ip)) {
            ip = NetworkHelper.maxLocalIp();
        }
        if (noMappingIp(ip)) {
            ip = NetworkHelper.minLocalIp();
        }
        if (noMappingIp(ip)) {
            LOG.warn("can not find the machine ip.....");
            return "";
        }
        return ip;
    }

    /**
     * IPv4转换成十进制数
     **/
    public static long ip2long(String ip) {
        if (noMappingIp(ip)) {
            return 0;
        }
        String[] p = ip.split("\\.");
        if (p.length == 4) {
            int p1 = ((Integer.parseInt(p[0]) << 24) & 0xFF000000);
            int p2 = ((Integer.parseInt(p[1]) << 16) & 0x00FF0000);
            int p3 = ((Integer.parseInt(p[2]) << 8) & 0x0000FF00);
            int p4 = ((Integer.parseInt(p[3])) & 0x000000FF);
            return ((p1 | p2 | p3 | p4) & 0xFFFFFFFFL);
        }
        return 0;
    }

    /**
     * 十进制数转换成IPv4
     **/
    public static String long2ip(long ip) {
        return String.valueOf((ip >> 24) & 0xFF) + '.' +
                ((ip >> 16) & 0xFF) + '.' +
                ((ip >> 8) & 0xFF) + '.' +
                ((ip) & 0xFF);
    }

    /**
     * 获取客户端真实IP
     **/
    public static String ofClientIp(HttpServletRequest request) {
        String fromSource = "X-Forwarded-For";
        String ip = request.getHeader(fromSource);
        if (noMappingIp(ip)) {
            fromSource = "Proxy-Client-IP";
            ip = request.getHeader(fromSource);
        }
        if (noMappingIp(ip)) {
            fromSource = "WL-Proxy-Client-IP";
            ip = request.getHeader(fromSource);
        }
        if (noMappingIp(ip)) {
            fromSource = "X-Real-IP";
            ip = request.getHeader(fromSource);
        }
        if (noMappingIp(ip)) {
            fromSource = "getRemoteAddr";
            ip = request.getRemoteAddr();
        }
        LOG.debug("fromSource: {}, ip: {}", fromSource, ip);
        return "0:0:0:0:0:0:0:1".equals(ip) ? machineIp() : ip.split(",")[0].trim();
    }

    /**
     * 获取本地 HOST NAME
     **/
    public static String localHostName() {
        String cn = System.getenv("COMPUTERNAME");
        if (null != cn && cn.trim().length() > 0) {
            return cn;
        } else {
            try {
                return (InetAddress.getLocalHost()).getHostName();
            } catch (UnknownHostException uhe) {
                // host = "hostname: hostname"
                String host = uhe.getMessage();
                if (null != host && host.trim().length() > 0) {
                    int colon = host.indexOf(':');
                    if (colon > 0) {
                        return host.substring(0, colon);
                    }
                }
                return "UnknownHost";
            }
        }
    }

    private static boolean noMappingIp(String ip) {
        return null == ip || ip.trim().length() < 1 || "unknown".equalsIgnoreCase(ip);
    }


    private static boolean isFictitious(String desc, String name) {
        return (null != desc && desc.trim().startsWith("VMware")) || (null != name && name.trim().startsWith("docker"));
    }

    private static String machineIp(String rs, Supplier<Optional<InetAddress>> supplier) {
        if (null == rs || rs.trim().length() < 1) {
            Optional<InetAddress> itOpt = supplier.get();
            if (itOpt.isPresent()) {
                rs = itOpt.get().getHostAddress();
            }
        }
        return null == rs ? "" : rs.trim();
    }

    public static boolean internalIp(String ip) {
        byte[] addr = textToNumericFormatV4(ip);
        return internalIp(addr) || "127.0.0.1".equals(ip);
    }

    private static boolean internalIp(byte[] addr) {
        if (Objects.isNull(addr) || addr.length < 2) {
            return true;
        }
        final byte b0 = addr[0];
        final byte b1 = addr[1];
        // 10.x.x.x/8
        final byte SECTION_1 = 0x0A;
        // 172.16.x.x/12
        final byte SECTION_2 = (byte) 0xAC;
        final byte SECTION_3 = (byte) 0x10;
        final byte SECTION_4 = (byte) 0x1F;
        // 192.168.x.x/16
        final byte SECTION_5 = (byte) 0xC0;
        final byte SECTION_6 = (byte) 0xA8;
        switch (b0) {
            case SECTION_1:
                return true;
            case SECTION_2:
                if (b1 >= SECTION_3 && b1 <= SECTION_4) {
                    return true;
                }
            case SECTION_5:
                if (b1 == SECTION_6) {
                    return true;
                }
            default:
                return false;
        }
    }

    public static byte[] textToNumericFormatV4(String text) {
        if (text.length() == 0) {
            return null;
        }
        byte[] bytes = new byte[4];
        String[] elements = text.split("\\.", -1);
        try {
            long l;
            int i;
            switch (elements.length) {
                case 1:
                    l = Long.parseLong(elements[0]);
                    if ((l < 0L) || (l > 4294967295L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l >> 24 & 0xFF);
                    bytes[1] = (byte) (int) ((l & 0xFFFFFF) >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 2:
                    l = Integer.parseInt(elements[0]);
                    if ((l < 0L) || (l > 255L)) {
                        return null;
                    }
                    bytes[0] = (byte) (int) (l & 0xFF);
                    l = Integer.parseInt(elements[1]);
                    if ((l < 0L) || (l > 16777215L)) {
                        return null;
                    }
                    bytes[1] = (byte) (int) (l >> 16 & 0xFF);
                    bytes[2] = (byte) (int) ((l & 0xFFFF) >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 3:
                    for (i = 0; i < 2; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    l = Integer.parseInt(elements[2]);
                    if ((l < 0L) || (l > 65535L)) {
                        return null;
                    }
                    bytes[2] = (byte) (int) (l >> 8 & 0xFF);
                    bytes[3] = (byte) (int) (l & 0xFF);
                    break;
                case 4:
                    for (i = 0; i < 4; ++i) {
                        l = Integer.parseInt(elements[i]);
                        if ((l < 0L) || (l > 255L)) {
                            return null;
                        }
                        bytes[i] = (byte) (int) (l & 0xFF);
                    }
                    break;
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return bytes;
    }

}
