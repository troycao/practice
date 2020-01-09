package com.troy.practice.pos;


// TODO: Auto-generated Javadoc

/**
 * POS MAC计算工具.
 */
public final class MACTool {
	
	/** The logger. */
//	private static final Logger LOG = LoggerFactory
//			.getLogger(MACTool.class);
	
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
	
	/**
	 * Xor.
	 *
	 * @param ba1 the ba1
	 * @param ba2 the ba2
	 * @return the byte[]
	 */
	public static byte[] xor(byte[] ba1,byte[] ba2){
		byte[] ba3 = new byte[8];
		for(int i=0;i<8;i++){
			ba3[i] = (byte)(ba1[i]^ba2[i]);
		}
		return ba3;
	}
	
//	public String psbcMacCalc(PsbcUnit unit,String txnTypeCode){
//		
//	}

}
