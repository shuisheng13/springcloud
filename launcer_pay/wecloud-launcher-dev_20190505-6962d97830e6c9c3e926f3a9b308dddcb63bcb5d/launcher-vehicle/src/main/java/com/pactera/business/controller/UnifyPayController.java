package com.pactera.business.controller;

import com.pactera.utlis.MD5Util;
import sun.security.provider.MD5;

import java.util.HashMap;
import java.util.Map;

/***
 * 统一支付控制器
 * 调试北京统一支付平台
 */
public class UnifyPayController {

    private static final String  codeUrl = "http://192.168.146.85:9051/payment/generateQrCode";
    private static final String priKey = "86d2d782-17c6-464c-8e13-d27bf64fcea5";
    public static void main(String[] args) {
        Map<String,Object> parMap = new HashMap<>();
        parMap.put("goodsName","夏日冰爽主题");
        parMap.put("goodsId",152);
        parMap.put("goodsCount",1);
        parMap.put("productId",15);
        String temp = parMap.get("goodsName").toString()+parMap.get("goodsId").toString()+
                parMap.get("goodsCount").toString()+parMap.get("productId").toString()+priKey;
        String signTemp = MD5Util.MD5Encode(temp,"utf-8");
        parMap.put("sign",signTemp);






    }



}
