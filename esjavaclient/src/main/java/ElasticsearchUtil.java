

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.elasticsearch.xpack.client.PreBuiltXPackTransportClient;

/**
  * 【描述】：  ELK java API 接口 ，包括查询，删除，插入等待
  * 【步骤】：     
  * @param
  * @return    
  * @throws       
  * @author    Allen
  * @date      2017/7/4 9:47
  */
public class ElasticsearchUtil {
   private static TransportClient client;
   private static String elasticIp = "172.25.12.101";
   private static int elasticPort = 9300;


   /**
     * 【描述】：  初始化ElasticSearch对象
     * 【步骤】：
     * @param
     * @return
     * @throws
     * @author    Allen
     * @date      2017/7/4 15:19
     */
   public static void init() throws UnknownHostException, InterruptedException, ExecutionException {
      //设置ES实例的名称.put("client.transport.sniff", true) //自动嗅探整个集群的状态，把集群中其他ES节点的ip添加到本地的客户端列表中
      Settings esSettings = Settings.builder().put("xpack.security.user", "elastic:changeme").put("cluster.name", "elasticsearch").build();
      client = new PreBuiltXPackTransportClient(esSettings);//初始化client较老版本发生了变化，此方法有几个重载方法，初始化插件等。
      
      //此步骤添加IP，至少一个，其实一个就够了，因为添加了自动嗅探配置
      client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(elasticIp), elasticPort));
      System.out.println("连接建立成功");
   }


   /**
     * 【描述】：  创建index，把其中的文档转化为json的格式存储
     * 【步骤】：
     * @param     　　<Node>:节点ip <port>:节点端口号，默认9200 <Index>:索引名 <Type>:索引类型 <ID>:操作对象的ID号
    * @return
     * @throws
     * @author    Allen
     * @date      2017/7/5 9:42
     */
   public static void createIndex() throws ElasticsearchException,IOException {
      for (int i=300; i<=50000000;i++){
         User user = new User();
         user.setId(1);
         user.setName("huang fox " + i);
         user.setAge(i % 100);
         IndexResponse indexResponse = null;
         indexResponse = client.prepareIndex("users", "user",i+"").setSource(generateJson(user)).execute().actionGet();
         System.out.println("responseIsCreated: "+indexResponse);
      }
      System.out.println("it is ok ！");
   }

   public static void query() throws Exception {
      //term查询
      //QueryBuilder queryBuilder = QueryBuilders.termQuery("age", 50) ;  //年龄等于50
      //range查询
      QueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age").gt(50); //年龄大于50
      SearchResponse searchResponse = client.prepareSearch("users")
            .setTypes("user")
            .setQuery(rangeQueryBuilder)     //query
            .setPostFilter(QueryBuilders.rangeQuery( "age" ).from( 70 ).to( 80 )) // Filter
            .addSort("age", SortOrder.DESC)
            .setSize(120)   // 不设置的话，默认取10条数据
            .execute().actionGet();
      SearchHits hits = searchResponse.getHits();
      System.out.println("查到记录数："+hits.getTotalHits());
      SearchHit[] searchHists = hits.getHits();
      if(searchHists.length>0){
         for(SearchHit hit:searchHists){
            String name =  (String) hit.getSource().get("name");
            Integer age = Integer.parseInt( hit.getSource().get("age").toString() );
            System.out.format("name:%s ,age :%d \n",name ,age);
         }
      }
   }


   /**
    * 转换成json对象
    *
    * @param user
    * @return
    */
   private static String generateJson(User user) {
      String json = "";
      try {
         XContentBuilder contentBuilder = XContentFactory.jsonBuilder().startObject();
         contentBuilder.field("id", user.getId());
         contentBuilder.field("name", user.getName());
         contentBuilder.field("age", user.getAge());
         json = contentBuilder.endObject().string();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return json;
   }


   /*
    * Get index 获取文档相当于读取数据库的一行数据
    */
   public static void getIndex(){
      GetResponse getresponse = client.prepareGet("users", "user", "402").execute().actionGet();
      System.out.println(getresponse.getSourceAsString());
   }

   /*
    *Delete index 相当于删除一行数据
    */
   public static void delete(){
      DeleteResponse deleteresponse = client.prepareDelete("users", "user","1").execute().actionGet();
      System.out.println(deleteresponse.getVersion());
   }

   /*
    *Delete index 删除索引及该索引所管辖的记录
    */
   public static void deleteIndex(){
      //删除所有记录
      DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete("logs").execute().actionGet();
      System.out.println(deleteIndexResponse.isAcknowledged());
   }

   /**
     * 【描述】：     获取到所有的索引
     * 【步骤】：
     * @param
     * @return
     * @throws
     * @author    Allen
     * @date      2017/7/4 16:27
     */
   public static void getAllIndex(){
      ClusterStateResponse response = client.admin().cluster().prepareState().execute().actionGet();
      //获取所有索引
      String[] indexs=response.getState().getMetaData().getConcreteAllIndices();
      for (String index : indexs) {
         System.out.println( index + " delete" );//
      }
   }



   public void close(){
      //on shutdown 断开集群
      client.close();
   }

   public static void main( String[] args ) {
      try {
         init();
         createIndex();
         //getIndex();
         //delete();
         //deleteIndex();
         //getAllIndex();
         query();
      } catch( Exception e ) {
         e.printStackTrace();
      }
   }

}


class User{

   private static final long serialVersionUID = 5290251719938640641L;

   private Integer id;
   private String name;
   private int age;

   public Integer getId() {
      return id;
   }

   public void setId( Integer id ) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName( String name ) {
      this.name = name;
   }

   public int getAge() {
      return age;
   }

   public void setAge( int age ) {
      this.age = age;
   }
}