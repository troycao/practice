package com.troy.practice.pos;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

// TODO: Auto-generated Javadoc

/**
 * The Class CommUtils.
 */
public class CommUtils {

	/**
	 * 组合字节数组.
	 * 
	 * @param bytes
	 *            the bytes
	 * @return the byte[]
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static byte[] combinByteArray(byte[]... bytes) throws IOException {
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			for (byte[] b : bytes) {
				bos.write(b);
			}
			return bos.toByteArray();
		} finally {
			if (bos != null) {
				bos.close();
				bos = null;
			}
		}
	}

	/**
	 * 数值格式化为字符串.
	 *
	 * @param num the num
	 * @param len the len
	 * @return the string
	 */
	public static String fmtNumber2Str(long num, int len) {
		return String.format("%0" + len + "d", num);
	}

}
