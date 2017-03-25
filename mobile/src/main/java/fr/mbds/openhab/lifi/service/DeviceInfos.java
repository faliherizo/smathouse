package fr.mbds.openhab.lifi.service;

import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by Faliherizo on 24/03/2017.
 */

public class DeviceInfos {

    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] buffer;
    private String pNetworkOperator;
    private TelephonyManager telephonyManager;
    private String imei;
    private GsmCellLocation cellLocation;
    private int bytesRead;
    int mcc = 0;
    int mnc = 0;
    int cid;
    int lac;
    private Socket socket;
    private InputStream inputStream;
    private static DeviceInfos deviceInfos;

    public ByteArrayOutputStream getByteArrayOutputStream() {
        return byteArrayOutputStream;
    }

    public void setByteArrayOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
        this.byteArrayOutputStream = byteArrayOutputStream;
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public void setBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    public String getpNetworkOperator() {
        return pNetworkOperator;
    }

    public void setpNetworkOperator(String pNetworkOperator) {
        this.pNetworkOperator = pNetworkOperator;
    }

    public TelephonyManager getTelephonyManager() {
        return telephonyManager;
    }

    public void setTelephonyManager(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public GsmCellLocation getCellLocation() {
        return cellLocation;
    }

    public void setCellLocation(GsmCellLocation cellLocation) {
        this.cellLocation = cellLocation;
    }

    public int getBytesRead() {
        return bytesRead;
    }

    public void setBytesRead(int bytesRead) {
        this.bytesRead = bytesRead;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public int getMnc() {
        return mnc;
    }

    public void setMnc(int mnc) {
        this.mnc = mnc;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLac() {
        return lac;
    }

    public void setLac(int lac) {
        this.lac = lac;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public static DeviceInfos getDeviceInfos() {
        return deviceInfos;
    }

    public static void setDeviceInfos(DeviceInfos deviceInfos) {
        DeviceInfos.deviceInfos = deviceInfos;
    }

    public static DeviceInfos GetInstance(){
        if(deviceInfos==null)
            deviceInfos = new DeviceInfos();
        return deviceInfos;
    }
    public void GetDeviceInfos() throws IOException {

        //out = new DataOutputStream(socket.getOutputStream());
        byteArrayOutputStream = new ByteArrayOutputStream(
                1024);
        buffer = new byte[1024];

        if (TextUtils.isEmpty(pNetworkOperator) == false) {
            mcc = Integer.parseInt(pNetworkOperator.substring(0, 3));
            mnc = Integer.parseInt(pNetworkOperator.substring(3));
        }
        cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        inputStream = socket.getInputStream();
        cid = cellLocation.getCid();
        lac = cellLocation.getLac();
        imei = telephonyManager.getDeviceId();
    }
}
