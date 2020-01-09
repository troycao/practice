package com.troy.practice.pos;

import java.util.Locale;

public class MacTest {

    String message = "";
    String mak = "240AB9531B5F860F";
    public static String dbKey ="48756174656E67333131325348464654";

    public static void main(String[] args) {
        byte[] db_key = hexStringToByte(dbKey);
        System.out.println(bytesToHexString(db_key));
        System.out.println(bytesToString(db_key));
    }


    /**
     * 计算mac.
     *
     * @param src the src
     * @param macKey the mac key
     * @return the string
     * @throws Exception the exception
     */
    public static String calcPosMac(byte[] src,byte[] macKey) throws Exception{
        if(src==null){
            return null;
        }
        macKey = CommUtils.combinByteArray(macKey,macKey);
        int len = src.length;
        int mod = len%8;
        int totlen = len+8-mod;

        byte[] fillsrc = null;
        //不满8倍数字节，填0x00
        if(mod!=0){
            fillsrc = new byte[totlen];
            System.arraycopy(src, 0, fillsrc, 0, len);
        }else{
            fillsrc = src;
        }

        byte[][] bs = new byte[fillsrc.length/8][8];
        int bslen = bs.length;
        for(int i=0;i<bslen;i++){
            System.arraycopy(fillsrc, i*8, bs[i], 0, 8);
        }
        //8字节一组异或
        byte[] temp = bs[0];
        for(int i=1;i<bs.length;i++){
            temp = MACTool.xor(temp, bs[i]);
        }
        String hexTemp = ConvertTools.bytesToHexString(temp);
        byte[] hexAscii = hexTemp.getBytes("utf-8");
        byte[] hexAscii1 = new byte[8];
        byte[] hexAscii2 = new byte[8];
        System.arraycopy(hexAscii, 0, hexAscii1, 0, 8);
        System.arraycopy(hexAscii, 8, hexAscii2, 0, 8);
        byte[] desHexAscii = DesTools.threeEncrypt(hexAscii1, macKey);
        byte[] lastBa = MACTool.xor(desHexAscii, hexAscii2);
        byte[] ba = DesTools.threeEncrypt(lastBa, macKey);
        String baHex = ConvertTools.bytesToHexString(ba);
        byte[] baHexAscii = baHex.getBytes();
        byte[] mac = new byte[8];
        System.arraycopy(baHexAscii, 0, mac, 0, 8);
        return ConvertTools.bytesToHexString(mac);
    }


    /*
     * 把16进制字符串转换成字节数组 @param hex @return
     */
    public static byte[] hexStringToByte(String hex) {

        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toUpperCase().toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }
            Locale locale = new Locale("US-ASCII");
            sb.append(sTemp.toUpperCase(locale));
        }
        return sb.toString();
    }

    public static final String bytesToString(byte[] bArray){
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            /*sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sb.append(0);
            }*/
            sTemp = String.valueOf(bArray[i]);

            Locale locale = new Locale("US-ASCII");
            sb.append(sTemp.toUpperCase(locale));
        }
        return sb.toString();
    }
}
