package org.foxbpm.kernel.advanced.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class ObjectOrBytesUtil {
	
	
	/**
	 * byte[] to long
	 * 
	 * @param b
	 * @return
	 */
	public static Object bytesToObject(byte[] b) {

		if (b.length > 0) {
			ObjectInput in = null;
			try {
				ByteArrayInputStream byteIn = new ByteArrayInputStream(b);
				in = new ObjectInputStream(byteIn);
				Object obj = in.readObject();

				if (obj != null) {
					return obj;
				}

			} catch (IOException e) {

			} catch (ClassNotFoundException e) {

			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {

					}
				}
			}

			return null;
		} else {

			return null;
		}
	}
	
	
	public static byte[] ObjectToBytes(Object obj) {

		ObjectOutput out = null;
		try {
			ByteArrayOutputStream byteout = new ByteArrayOutputStream();
			out = new ObjectOutputStream(byteout);
			out.writeObject(obj);
			byte[] buf = byteout.toByteArray();

			return buf;
		} catch (IOException e) {
			return null;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {

				}
			}
		}
	}

}
