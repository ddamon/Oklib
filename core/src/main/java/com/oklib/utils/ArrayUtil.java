package com.oklib.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 数组操作帮助类
 * 
 * @author Michael.li
 *
 */
public class ArrayUtil {

	/**
	 * 求两个数组的交集
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static String[] intersect(String[] arr1, String[] arr2) {
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		LinkedList<String> list = new LinkedList<String>();
		for (String str : arr1) {
			if (!map.containsKey(str)) {
				map.put(str, false);
			}
		}
		for (String str : arr2) {
			if (map.containsKey(str)) {
				map.put(str, true);
			}
		}
		for (Entry<String, Boolean> e : map.entrySet()) {
			if (e.getValue()) {
				list.add(e.getKey());
			}
		}
		String[] result = {};
		return list.toArray(result);
	}

	/**
	 * 是否有交集
	 * 
	 * @param arr1
	 * @param arr2
	 * @return
	 */
	public static boolean hasIntersect(String[] arr1, String[] arr2) {
		boolean ret = false;
		List<String> array = Arrays.asList(arr1);
		for (String c : arr2) {
			if (array.contains(c)) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	/**
	 * 多个数组合并
	 * 
	 * @param first
	 * @param rest
	 * @return
	 */
	public static <T> T[] concatAll(T[] first, T[]... rest) {
		int totalLength = first.length;
		for (T[] array : rest) {
			totalLength += array.length;
		}
		T[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (T[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
}
