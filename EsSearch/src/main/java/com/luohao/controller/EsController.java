package com.luohao.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luohao.helper.DataConvertHelper;
import com.luohao.helper.StringUtils;

@RestController
@RequestMapping(value = "/es")
public class EsController {

	@RequestMapping(value = "/search.ashx", method = RequestMethod.POST)
	public JSONObject queryEsearch(HttpServletRequest req, HttpServletResponse resp) throws UnknownHostException {
		Map<String, Object> where = DataConvertHelper.getRequestParams(req);
		Integer curr = Integer.valueOf(where.get("curr").toString());
		JSONObject result = new JSONObject();
		//1.设置集群
		Settings settings = Settings.builder().put("cluster.name", "elasticsearch") // 本地固定的集群名称
				.put("xpack.security.transport.ssl.enabled", false) // 设置xpack的ssl模块不可用
				.put("xpack.security.user", "elastic:changeme") // 登录用户和密码，不然不能连接到elasticsearch
				.put("client.transport.sniff", true) // 自动去发现新加入集群的机器
				.build();
		
		//2.获取client，如果加入了x-pack安全框架，则PreBuiltXPackTransportClient必须换成x-packjar包中的
		TransportClient client = new PreBuiltXPackTransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("172.25.12.101"), 9300));
		
		// 查询条件
		QueryBuilder matchQuery = QueryBuilders.multiMatchQuery(where.get("resourcename").toString(), "resourcename"); // 设置搜索条件
		BoolQueryBuilder must = QueryBuilders.boolQuery().must(matchQuery);
		
		if (!StringUtils.IsEmptyOrNull(where.get("stage_gid"))) {
			QueryBuilder matchQueryStage = QueryBuilders.matchPhraseQuery("stage_gid", where.get("stage_gid"));
			must.must(matchQueryStage);
		}
		if (!StringUtils.IsEmptyOrNull(where.get("subject_value"))) {
			QueryBuilder matchQuerySubject = QueryBuilders.matchPhraseQuery("subject_value", where.get("subject_value"));
			must.must(matchQuerySubject);
		}
		if (!StringUtils.IsEmptyOrNull(where.get("book_version"))) {
			QueryBuilder matchQueryBook = QueryBuilders.matchPhraseQuery("book_version", where.get("book_version"));
			must.must(matchQueryBook);
		}
		if (!StringUtils.IsEmptyOrNull(where.get("text_book_gid"))) {
			QueryBuilder matchQueryText = QueryBuilders.matchPhraseQuery("text_book_gid", where.get("text_book_gid"));
			must.must(matchQueryText);
		}
		if (!StringUtils.IsEmptyOrNull(where.get("type_value"))) {
			QueryBuilder matchQueryType = QueryBuilders.matchPhraseQuery("type_value", where.get("type_value"));
			must.must(matchQueryType);
		}
		
		// 排序
		SortBuilder sortBuilder = SortBuilders.fieldSort("viewnum").order(SortOrder.DESC);// 提交时间排序
		
		HighlightBuilder hiBuilder = new HighlightBuilder(); // 设置高亮
		hiBuilder.preTags("<font style='color:red;'>");
		hiBuilder.postTags("</font>");
		hiBuilder.field("resourcename");// 若要匹配多个高亮字段，则只用在后面加.field("字段的名称")

		//3.去索引库搜索数据
		SearchResponse response = client.prepareSearch("indexdb") // 需要搜索的索引库
				.setTypes("tab") // 搜索的类型(相当于数据库中的表),这里如果不设置就搜索这个索引库下所有的类型，
				.setQuery(must) // 搜索的条件
				.addSort(sortBuilder) // 设置排序
				.highlighter(hiBuilder) // 设置高亮
				.setSearchType(SearchType.DFS_QUERY_THEN_FETCH) // 设置搜索类型，各个不同的类型决定搜索所用的时间
				.setFrom((curr-1)*10) // 从多少开始搜，相当于pageIndex
				.setSize(10) // 搜索结果集的总数，相当于pageSize
				.execute().actionGet();
		
		//4.获取查询结果集
		SearchHits searchHits = response.getHits();
		
		//5.处理数据得到我们想要的数据
		JSONArray array = new JSONArray();
		for (SearchHit hit : searchHits) {
			String sourceAsString = hit.getSourceAsString();
			JSONObject parseObject = JSONObject.parseObject(sourceAsString);
			if (!StringUtils.IsEmptyOrNull(hit.getHighlightFields().get("resourcename"))) {
				Text[] text = hit.getHighlightFields().get("resourcename").getFragments();
				parseObject.put("resourcename", text[0].string());
			}
			/*if (!StringUtils.IsEmptyOrNull(hit.getHighlightFields().get("masterial"))) {
				Text[] wrap = hit.getHighlightFields().get("masterial").getFragments();
				parseObject.put("masterial", wrap[0].string());
			}*/
			array.add(parseObject);
		}
		if(searchHits.getHits().length == 0) {
			result.put("data", array);
			result.put("total", 0);
		} else {
			result.put("data", array);
			result.put("total", searchHits.getTotalHits());
			result.put("totalpage", (searchHits.getTotalHits() + 10 -1) /10);
		}
		return result;
	}
}
