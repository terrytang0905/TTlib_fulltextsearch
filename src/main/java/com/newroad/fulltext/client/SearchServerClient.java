package com.newroad.fulltext.client;

import static org.elasticsearch.client.Requests.clusterHealthRequest;
import static org.elasticsearch.common.settings.Settings.builder;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.newroad.fulltext.datamodel.DataConnectionOption;
import com.newroad.fulltext.util.FullTextSearchException;

/**
 * @info Elastic Search Server Client
 * @author tangzj1
 * @date Aug 26, 2013
 * @version
 */

public class SearchServerClient implements InitializingBean {

  private static Logger logger = LoggerFactory.getLogger(SearchServerClient.class);

//  public static final String ADMIN_DATABASE_NAME = "admin";
//  public static final String LOCAL_DATABASE_NAME = "local";
//  public static final String REPLICA_SET_NAME = "rep1";
//  public static final String OPLOG_COLLECTION = "oplog.rs";

  private static String esHost;
  private static Integer esPort;

  @Autowired
  private DataConnectionOption dataConnectionOption;
  private Settings settings;

  private static Client client;
  private static Node node;
  private static Mongo mongo;
  private static DB mongoDB;

  @Override
  public void afterPropertiesSet() {
    try {
      loadSettings();
      initElasticsearchServer();
      // setupElasticsearchServer();
      initMongoInstances();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      logger.error("SearchServerClient failed", e);
      throw new FullTextSearchException(e);
    }
  }

  public Client getESClient() {
    return client;
  }

  public Settings getSettings() {
    return settings;
  }

  public Node getESNode() {
    return node;
  }

  public void closeESClient() {
    client.close();
    mongo.close();
  }

  public Mongo getMongo() {
    return mongo;
  }

  public static void checkClusterHealth() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    logger.debug("Running Cluster Health");
    ClusterHealthResponse clusterHealth = client.admin().cluster().health(clusterHealthRequest().waitForGreenStatus()).actionGet();
    logger.info("Done Cluster Health, status " + clusterHealth.getStatus());
  }

  private void loadSettings() throws IOException {
    // Settings settings = ImmutableSettings.settingsBuilder()
    // .put("cluster.name", clusterName).build();
    InputStream is = this.getClass().getClassLoader().getResourceAsStream("/config/settings.yml");
    if (is != null) {
      settings = builder().loadFromStream("settings.yml", is).put("client", true).put("data", false).build();

      esHost = settings.get("client.host");
      esPort = settings.getAsInt("client.port", 9300);

    }
  }

  private void initElasticsearchServer() throws UnknownHostException {
    InetAddress inetAddress=InetAddress.getByAddress(esHost, esHost.getBytes());
    InetSocketTransportAddress addr = new InetSocketTransportAddress(inetAddress, esPort);
    client = new PreBuiltTransportClient(settings).addTransportAddress(addr);
  }

  private void initMongoInstances() throws Exception {
    logger.debug("*** initMongoInstances ***");
    try {
      String username = dataConnectionOption.getUserName();
      String dbname = dataConnectionOption.getDbName();
      String password = dataConnectionOption.getPassWord();
      MongoCredential credential = MongoCredential.createMongoCRCredential(username, dbname, password.toCharArray());

      MongoClientOptions mco =
          MongoClientOptions.builder().autoConnectRetry(true).connectTimeout(dataConnectionOption.getConnectionTimeOut())
              .maxAutoConnectRetryTime(dataConnectionOption.getMaxRetryTime()).socketTimeout(60000).build();
      String[] nodeips = dataConnectionOption.getNodeiplist();
      String[] nodeports = dataConnectionOption.getNodeportlist();
      List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
      for (int i = 0; i < nodeips.length; i++) {
        serverAddressList.add(new ServerAddress(nodeips[i], Integer.parseInt(nodeports[i])));
      }
      mongo = new MongoClient(serverAddressList, Arrays.asList(credential), mco);
      mongoDB = mongo.getDB(dbname);
      if (!mongoDB.authenticate(username, password.toCharArray())) {
        logger.error("Authentication failure!userName:" + username + ",dbName:" + dbname + ",passWord:" + password);
        throw new FullTextSearchException("DB Connection failed because of authentication failure!");
      }
    } catch (Exception ex) {
      logger.error("initMongoInstances failed", ex);
      throw ex;
    }
  }

//  @SuppressWarnings("unused")
//  private void setupElasticsearchServer() throws Exception {
//    logger.debug("*** setupElasticsearchServer ***");
//    try {
//      //Tuple<Settings, Environment> initialSettings = InternalSettingsPreparer.prepareSettings(settings, true);
//      Tuple<Settings, Environment> initialSettings = InternalSettingsPerparer.prepareSettings(settings, true);
//      File configFile = initialSettings.v2().configFile();
//      logger.debug("elasticSearch config file:" + configFile.getAbsolutePath());
//      if (!configFile.exists()) {
//        FileSystemUtils.mkdirs(configFile);
//      }
//      File logFile = initialSettings.v2().logsFile();
//      if (!logFile.exists()) {
//        FileSystemUtils.mkdirs(logFile);
//      }
//      File pluginsFile = initialSettings.v2().pluginsFile();
//      if (!pluginsFile.exists()) {
//        FileSystemUtils.mkdirs(pluginsFile);
//        if (settings.getByPrefix("plugins") != null) {
//          //PluginManager pluginManager = new PluginManager(initialSettings.v2(), null, OutputMode.DEFAULT, PluginManager.DEFAULT_TIMEOUT);
//          PluginManager pluginManager = new PluginManager(initialSettings.v2(), null);
//          
//          Map<String, String> plugins = settings.getByPrefix("plugins").getAsMap();
//          for (Entry<String, String> entry : plugins.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue();
//            //pluginManager.downloadAndExtract(plugins.get(key)); 
//             if (value.indexOf("elasticsearch") == 0) {
//             pluginManager.downloadAndExtract(plugins.get(key), false);
//             } else {
//             pluginManager.downloadAndExtract(plugins.get(key), true);
//             }
//          }
//        }
//      }
//      node = nodeBuilder().settings(settings).node();
//      client = node.client();
//    } catch (Exception ex) {
//      logger.error("setupElasticsearchServer failed", ex);
//      throw ex;
//    }
//  }

}
