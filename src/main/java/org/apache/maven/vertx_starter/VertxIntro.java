package org.apache.maven.vertx_starter;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
//import io.vertx.rxjava.ext.web.client.HttpResponse;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;

public class VertxIntro extends AbstractVerticle {
	public static final String COLLECTION = "democollection";
	private MongoClient mongo;
 
  @Override
  public void start(Future<Void> fut) {
	  /*
      WebClient client = WebClient.create(vertx);
      
      HttpRequest<Buffer> request = client.get(8080, "https://proximus.stb-tester.com", "/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1153");
      request.putHeader("content-type", "application/json");
      request.putHeader("Authorization", "token _m8PfmdE7snOKQKkfa4tBkEKZfGg_r7e");
      */
      /*
      WebClient client = WebClient.create(vertx);
      client
      .get("https://proximus.stb-tester.com", "/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1153")
      .send(ar -> {
        if (ar.succeeded()) {
          // Obtain response
          //HttpResponse<Buffer> response = (HttpResponse<Buffer>) ar.result();
          System.out.println("Received response with status code" + ar.result());
        } else {
          System.out.println("Something went wrong " + ar.cause().getMessage());
        }
      });
	  */
	  
	  /*
	  JsonObject config = Vertx.currentContext().config();
	  
	  String uri = config.getString("mongo_uri");
	    if (uri == null) {
	      uri = "mongodb://localhost:27017";
	    }
	    String db = config.getString("demodb");
	    if (db == null) {
	      db = "democollection";
	    }

	    JsonObject mongoconfig = new JsonObject()
	        .put("connection_string", uri)
	        .put("db_name", db);
	    
	  
	  mongo = MongoClient.createShared(vertx, mongoconfig);

	  createSomeData(fut);
	  */
	  Router router= Router.router(vertx);
	  
	  router.route("/").handler(routingContext -> {
	      HttpServerResponse response = routingContext.response();
	      response
	          .putHeader("content-type", "text/html")
	          .end("<h1>Hello from my first Vert.x application</h1>");
	    });
	  router.get("/vertex/handler").handler(this::getAll);

    vertx
        .createHttpServer()
        .requestHandler(router::accept)
        .listen(8080);
        
  }
  
  
  private void getAll(RoutingContext routingContext) {
	  HttpServerResponse response = routingContext.response();
      response
          .putHeader("content-type", "text/html")
          .end("<h1>This is a handler example</h1>");
      
      System.out.println(">>>>> Inside getAll");
      
      /*
      WebClient client = WebClient.create(vertx,
    	      new WebClientOptions()
    	      	.setDefaultHost("https://proximus.stb-tester.com")
    	        .setSsl(true)
    	        .setTrustAll(true)
    	        .setKeepAlive(false)
    	    );
    	    client.getAbs("https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1154")
    	    .putHeader("Authorization", "token _m8PfmdE7snOKQKkfa4tBkEKZfGg_r7e")  
    	    .send(ar -> {
    	        if (ar.succeeded()) {
    	          HttpResponse<Buffer> resp = ar.result();
    	          System.out.println("Got HTTP response with status " + resp.statusCode());
    	          System.out.println("Got HTTP response with status " + resp.bodyAsString());
    	        } else {
    	          ar.cause().printStackTrace();
    	        }
    	      });
      */
      /*
      HttpClient client = vertx.createHttpClient(new HttpClientOptions()
    		  .setDefaultHost("https://proximus.stb-tester.com")
    		  .setSsl(true)
    		  .setTrustAll(true)
    		  .setKeepAlive(false));
      HttpClientRequest request = client.getAbs("https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1154", resp -> {
    	  System.out.println("Got response from server");
    	  System.out.println("Got response " + resp.statusCode());
          resp.bodyHandler(body -> System.out.println("Got data " + body.toString("ISO-8859-1")));
        })
     .putHeader("Authorization", "token _m8PfmdE7snOKQKkfa4tBkEKZfGg_r7e");
      */
      
      WebClient client = WebClient.create(vertx,
    	      new WebClientOptions()
    	      	.setDefaultHost("https://proximus.stb-tester.com")
    	        .setSsl(true)
    	        .setTrustAll(true)
    	        .setKeepAlive(false)
    	    );
      JsonArray test_cases = new JsonArray();
      test_cases.add("tests/home.py::test_remote_control");
    	    client.postAbs("https://proximus.stb-tester.com/api/v2/run_tests")
    	    .putHeader("Authorization", "token _m8PfmdE7snOKQKkfa4tBkEKZfGg_r7e")
    	    .sendJsonObject(new JsonObject()
    	    		.put("node_id", "stb-tester-00044b80ea4d")
    	    		.put("test_pack_revision", "f32bd5b1ef774d3fb2f7c483e8299d517c6a1fc0")
    	    		.put("test_cases", test_cases)
    	    		.put("remote_control", "belgacom-v4"),
    	    ar -> {
    	        if (ar.succeeded()) {
    	          HttpResponse<Buffer> resp = ar.result();
    	          System.out.println("Got HTTP response with status " + resp.statusCode());
    	          System.out.println("Got HTTP response with status " + resp.bodyAsString());
    	        } else {
    	          ar.cause().printStackTrace();
    	        }
    	      });
      
      
      /*
      WebClient client = WebClient.create(vertx,
    	      new WebClientOptions());
      client.get(3000, "localhost", "/welcome")
      .send(ar -> {
    	  if(ar.succeeded())
    		  System.out.println("api call success");
      });
      
      /*
      HttpClient client = vertx.createHttpClient();
      client.get("https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1154", response -> {
          if (response.statusCode() == HttpResponseStatus.OK.code()) {
              System.out.println("Inside api success");
      });*/
      
      /*
      HttpClient client = vertx.createHttpClient();
      client.getNow("https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1154",new Handler<HttpClientResponse>() {
    	  @Override
    	    public void handle(HttpClientResponse httpClientResponse) {
    		  System.out.println("Inside handle");
    		  /*
    	    	httpClientResponse.bodyHandler(new Handler<Buffer>() {
    	            @Override
    	            public void handle(Buffer buffer) {
    	                System.out.println("Response (" + buffer.length() + "): ");
    	                System.out.println(buffer.getString(0, buffer.length()));
    	            }
    	        }); 
    	    }
    	}); 
      //.exceptionHandler(error -> {System.out.println("Error " + error);})
      //.putHeader("content-type", "application/json")
      //.putHeader("Authorization", "token _m8PfmdE7snOKQKkfa4tBkEKZfGg_r7e");
      
      
      /*
      HttpClient client = vertx.createHttpClient();
      client.request(HttpMethod.GET, "https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1154", response -> {
         // System.out.println("Received response with status code " + response.statusCode());
        }).end();
      */
      
      /*
      HttpClient client = vertx.createHttpClient(new HttpClientOptions());
      HttpClientRequest request = client.get("https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1153", resp -> {
    	  System.out.println("Got response from server");
    	  System.out.println("Got response " + resp.statusCode());
          resp.bodyHandler(body -> System.out.println("Got data " + body.toString("ISO-8859-1")));
        })
     .exceptionHandler(error -> {System.out.println(error.toString());})
     .putHeader("content-type", "application/json")
     .putHeader("Authorization", "token _m8PfmdE7snOKQKkfa4tBkEKZfGg_r7e");
     */
      
      /*
      HttpClient client = vertx.createHttpClient();
      HttpClientRequest request = client.get(3000, "localhost", "/welcome");
      request.handler(res -> {
    	 System.out.println("api response " + res);
      });
      */
      
      //https://proximus.stb-tester.com/api/v2/jobs/stb-tester-00044b80ea4d/60Xu/1153
      
      /*
      WebClient client = WebClient.create(vertx);
      client
      .get("localhost:3000")
      .send(ar -> {
        if (ar.succeeded()) {
          // Obtain response  
          //HttpResponse<Buffer> response = (HttpResponse<Buffer>) ar.result();
          System.out.println("Received response with status code" + ar.result());
        } else {
          System.out.println("Something went wrong " + ar.cause().getMessage());
        }
      });
      */
      
    }
  
  private void createSomeData(Future<Void> fut) {
	    Employee employee1 = new Employee(1234567, "John", "ENG");
	    Employee employee2 = new Employee(12345678, "Sam", "DNA");
	    System.out.println(employee1.toJson());

	    // Do we have data in the collection ?
	    mongo.count(COLLECTION, new JsonObject(), count -> {
	      if (count.succeeded()) {
	        if (count.result() == 0) {
	          mongo.insert(COLLECTION, employee1.toJson(), ar -> {
	            if (ar.failed()) {
	              fut.fail(ar.cause());
	            } else {
	              mongo.insert(COLLECTION, employee2.toJson(), ar2 -> {
	                if (ar2.failed()) {
	                  fut.failed();
	                }
	              });
	            }
	          });
	        }
	      } else {
	        // report the error
	        fut.fail(count.cause());
	      }
	    });
	  }
  
}

