package com.luohao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import LovicocoAPParser.Events.DeviceDataCollect;

public class Test {

	public static void OnReceiveDatas(String Account, DeviceDataCollect Data) {
		//JSONObject json = new JSONObject();
		/*json.put("callName", "com.cloud.smartschool.HomeSchoolRs.addTemparetureInfo()");
		json.put("Account", Data.getAccount());
		List<DeviceData> dataLst = Data.getDeviceLst().get(0).getDataLst();
		for (int i = 0; i < dataLst.size(); i++) {
			int type = dataLst.get(i).getType();
			if (type == 0x73) {
				json.put("Value", dataLst.get(i).getValue());
				json.put("Time", dataLst.get(i).getTime());
			}
		}*/
		
		
		
		//String data = JSON.toJSONString(json);
		//Logger.getLogger(CallbackCls.class.getName()).log(Level.INFO,"OnReceiveData:" + Data.toJsonString());
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://202.100.209.61:8080/CreditCardRecords.ashx");
		try {
			String string = "{\"CardID\":\"11111111\",\"TransactionID\":\"222\",\"DeviceID\":\"111\"}";
			JSONObject json = JSONObject.parseObject(string);
			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");//发送json数据需要设置contentType
			post.setEntity(s);
			HttpResponse res = client.execute(post);
			if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = res.getEntity();
				String result = EntityUtils.toString(res.getEntity());// 返回json格式：
				Logger.getLogger("return data:" + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Logger.getLogger(CallbackCls.class.getName()).log(Level.INFO,"OnReceiveData:" + Data.toJsonString());
	}
	
	public static void main(String[] args) {
		OnReceiveDatas(null,null);
	}
}
