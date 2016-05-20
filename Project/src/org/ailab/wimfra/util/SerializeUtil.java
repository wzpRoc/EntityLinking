package org.ailab.wimfra.util;

import java.io.*;

public final class SerializeUtil {

	/**
	 * 序列化对象
	 * 
	 * @throws java.io.IOException
	 */
	public static byte[] serializeObject(Object object) throws IOException {
		ByteArrayOutputStream saos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(saos);
		oos.writeObject(object);
		oos.flush();
		return saos.toByteArray();
	}

	/**
	 * 反序列化对象
	 *
	 * @throws java.io.IOException
	 * @throws ClassNotFoundException
	 */
	public static Object deserializeObject(byte[] buf) throws IOException,
			ClassNotFoundException {
		Object object = null;
		ByteArrayInputStream sais = new ByteArrayInputStream(buf);
		ObjectInputStream ois = new ObjectInputStream(sais);
		object = (Object) ois.readObject();
		return object;
	}
}
