import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class solrjtest {
    @Test
    public void addDocument() throws Exception{
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8888/solr");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id","test001");
        document.addField("item_title","测试商品2");
        document.addField("item_price",54321);
        //把文档对象写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }

    @Test
    public void deleteDocument() throws Exception{
        //创建连接
        SolrServer solrServer = new HttpSolrServer("http://localhost:8888/solr");
        //solrServer.deleteById("test001");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
    }

    @Test
    public void qD() throws Exception{
        SolrServer solrServer = new HttpSolrServer("http://localhost:8888/solr");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse response = solrServer.query(query);
        SolrDocumentList solrDocumentList = response.getResults();
        for (SolrDocument entries : solrDocumentList) {
            System.out.println(entries.get("id"));
        }
    }
}
