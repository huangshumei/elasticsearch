package com.luohao.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luohao.helper.DataConvertHelper;
import com.luohao.helper.StringUtils;

@RestController
@RequestMapping("/")
public class ZlbAttend {

	private Logger logger = LoggerFactory.getLogger(ZlbAttend.class);

	/**
	 * 考勤基本信息上传接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/CreditCardRecords.ashx", method = RequestMethod.POST)
	public JSONObject queryBasicData(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
		StringBuffer sb = new StringBuffer();
		// 格式检查
		if (StringUtils.IsEmptyOrNull(where.get("ProvinceCode"))) {
			sb.append("ProvinceCode parse error! Message:省份编码为必填。");
			result.put("ProvinceCode", "");
		} else {
			result.put("ProvinceCode", where.get("ProvinceCode"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("TransactionID"))) {
			sb.append("TransactionID parse error! Message:流水号为必填。");
			result.put("TransactionID", "");
		} else {
			result.put("TransactionID", where.get("TransactionID"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("SchoolID"))) {
			sb.append("SchoolID parse error! Message:学校ID为必填。");
		}
		if (StringUtils.IsEmptyOrNull(where.get("DeviceID"))) {
			sb.append("DeviceID parse error! Message:设备编码为必填。");
			result.put("DeviceID", "");
		} else {
			result.put("DeviceID", where.get("DeviceID"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("CardID"))) {
			sb.append("CardID parse error! Message:卡号为必填。");
			result.put("CardID", "");
		} else {
			result.put("CardID", where.get("CardID"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("CardTime"))) {
			sb.append("CardTime parse error! Message:卡时间为必填。");
			result.put("CardTime", "");
		} else {
			if (!DataConvertHelper.checkDate(where.get("CardTime").toString())) {
				sb.append("CardTime parse error! Message:该字符串未被识别为有效的 DateTime。");
			} else {
				result.put("CardTime", where.get("CardTime"));
			}
		}
		if (StringUtils.IsEmptyOrNull(where.get("CardType"))) {
			sb.append("CardType parse error! Message:卡类型为必填。");
		}
		if (StringUtils.IsEmptyOrNull(where.get("TimeStamp"))) {
			sb.append("TimeStamp parse error! Message:时间戳为必填。");
		} else {
			if (!DataConvertHelper.checkDate(where.get("TimeStamp").toString())) {
				sb.append("TimeStamp parse error! Message:该字符串未被识别为有效的 DateTime。");
			} else {
				result.put("TimeStamp", where.get("TimeStamp"));
			}
		}
		if (StringUtils.IsEmptyOrNull(where.get("Extension"))) {
			sb.append("Extension parse error! Message:设备mac地址为必填。");
		}
		// 最后判断状态
		if (sb.length() == 0) {
			result.put("ResultCode", "200");
			result.put("ResultMsg", "接收数据成功");
		} else {
			result.put("ResultCode", "400");
			result.put("ResultMsg", sb);
		}
		logger.info(">>>>>>>>>>>>>>>basic result"+result.toJSONString());
		return result;
	}
	
	/**
	 * 通过学校ID 和省份编码获取学生列表信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/GetStudentInfos.ashx", method = RequestMethod.GET)
	public JSONObject queryStudentData(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
		StringBuffer sb = new StringBuffer();
		if (StringUtils.IsEmptyOrNull(where.get("ProvinceCode")) || StringUtils.IsEmptyOrNull(where.get("SchoolID"))) {
			sb.append("省份编码或学校ID不能为空！");
		}
		if (sb.length() == 0) {
			
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://192.168.0.100/ecp/remote?callName=com.cloud.school.service.impl.SchoolClassBl.queryAllStuBySchId()&callback=1");
			try {
				List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>(); 
				pairList.add(new BasicNameValuePair("SchoolID", where.get("SchoolID").toString()));
				post.setEntity(new UrlEncodedFormEntity(pairList, "utf-8")); 
				
				HttpResponse res = client.execute(post);
				if(res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					HttpEntity entity = res.getEntity();
					String entityList = EntityUtils.toString(res.getEntity());// 返回json格式：
					StringBuffer sbu = new StringBuffer(entityList.toLowerCase());
					sbu.delete(0, 2);
					String sbus = sbu.substring(0, sbu.length()-1);
					result.put("data", JSON.parseArray(sbus));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			JSONObject jsonCode = new JSONObject();
			jsonCode.put("code", "200");
			result.put("_metadata", jsonCode);
		} else {
			JSONObject jsonCode = new JSONObject();
			jsonCode.put("code", "400");
			result.put("_metadata", jsonCode);
			JSONObject jsonData = new JSONObject();
			jsonData.put("info", sb);
			result.put("msg", jsonData);
		}
		logger.info(">>>>>>>>>>>>>>>stu result"+result.toJSONString());
		return result;
	}
	
	/**
	 * 考勤图片上报
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/CreditCardPhotos.ashx", method = RequestMethod.GET)
	public JSONObject uploadCreditPic(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = new JSONObject();
		Map<String, Object> where = DataConvertHelper.getRequestParams(request);
		String sb = new String();
		if (StringUtils.IsEmptyOrNull(where.get("ProvinceCode"))) {
			sb = "考勤记录信息不存在！";
		} else {
			result.put("ProvinceCode", where.get("ProvinceCode"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("TransactionID"))) {
			sb = "考勤记录信息不存在！";
		} else {
			result.put("TransactionID", where.get("TransactionID"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("SchoolID"))) {
			sb = "考勤记录信息不存在！";
		}
		if (StringUtils.IsEmptyOrNull(where.get("DeviceID"))) {
			sb = "考勤记录信息不存在！";
		} else {
			result.put("DeviceID", where.get("DeviceID"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("CardID"))) {
			sb = "考勤记录信息不存在！";
		} else {
			result.put("CardID", where.get("CardID"));
		}
		if (StringUtils.IsEmptyOrNull(where.get("CardTime"))) {
			sb = "考勤记录信息不存在！";
		} else {
			if (!DataConvertHelper.checkDate(where.get("TimeStamp").toString())) {
				sb = "考勤记录信息不存在！";
			} else {
				result.put("CardTime", where.get("CardTime"));
			}
		}
		result.put("PhotoImg", where.get("PhotoImg"));
		if (sb.length() == 0) {
			result.put("ResultCode", "200");
			result.put("ResultMsg", "接收考勤照片数据成功");
		} else {
			result.put("ResultCode", "400");
			result.put("ResultMsg", sb);
		}
		return result;
	}
}
