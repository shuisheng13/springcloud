package com.pactera.serviceT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONTokener;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.pactera.base.Tester;
import com.pactera.business.dao.LaunWidgetMinPropertyMapper;
import com.pactera.business.service.LaunWidgetManagerService;
import com.pactera.business.service.LaunWidgetMinPropertyService;
import com.pactera.business.service.LaunWidgetPropertyService;
import com.pactera.domain.LaunWidget;
import com.pactera.domain.LaunWidgetMinProperty;
import com.pactera.domain.LaunWidgetProperty;
import com.pactera.utlis.HStringUtlis;
import com.pactera.utlis.IdUtlis;

@SuppressWarnings("all")
public class HeapSort {

	public static void main(String[] args) {
		int[] arr = {9,6,11,8,5,23};
		/*
		 * sort(arr); System.out.println(Arrays.toString(arr));
		 */
		quick_sort(arr, 0, arr.length - 1);
		System.out.println(Arrays.toString(arr));
	}

	public static void sort(int[] arr) {
		// 1.构建大顶堆

		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			// 从第一个非叶子结点从下至上，从右至左调整结构
			adjustHeap(arr, i, arr.length);
		}
		// 2.调整堆结构+交换堆顶元素与末尾元素
		for (int j = arr.length - 1; j > 0; j--) {
			swap(arr, 0, j);// 将堆顶元素与末尾元素进行交换
			adjustHeap(arr, 0, j);// 重新对堆进行调整
		}

	}

	/**
	 * 调整大顶堆（仅是调整过程，建立在大顶堆已构建的基础上）
	 * 
	 * @param arr
	 * @param i
	 * @param length
	 */
	public static void adjustHeap(int[] arr, int i, int length) {
		int temp = arr[i];// 先取出当前元素i
		for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {// 从i结点的左子结点开始，也就是2i+1处开始
			if (k + 1 < length && arr[k] < arr[k + 1]) {// 如果左子结点小于右子结点，k指向右子结点
				k++;
			}
			if (arr[k] > temp) {// 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
				arr[i] = arr[k];
				i = k;
			} else {
				break;
			}
		}
		arr[i] = temp;// 将temp值放到最终的位置
	}

	/**
	 * 交换元素
	 * 
	 * @param arr
	 * @param a
	 * @param b
	 */
	public static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	static void quick_sort(int s[], int l, int r) {
		
		if (l < r) {
			// Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
			int i = l, j = r, x = s[l];
			while (i < j) {
				while (i < j && s[j] >= x) // 从右向左找第一个小于x的数
					j--;
				if (i < j)
					s[i++] = s[j];

				while (i < j && s[i] < x) // 从左向右找第一个大于等于x的数
					i++;
				if (i < j)
					s[j--] = s[i];
			}
			s[i] = x;
			System.out.println(Arrays.toString(s));
			quick_sort(s, l, i - 1); // 递归调用
			quick_sort(s, i + 1, r);
		}
	}
}
