
package com.example.simplespark

object Main {

  def main(args:Array[String]):Unit = {
    // spark context
    import org.apache.spark.{SparkContext, SparkConf}
    import org.apache.spark.SparkContext._
    import org.apache.spark.rdd._
    import org.apache.spark.sql._
    import org.apache.spark.sql.functions._
    import com.typesafe.config._
    import scala.collection.JavaConverters._
    import scala.util.Try
    
    
    // Spark notebook widgets (can be removed if you do not use them)
    // Dummy implementation of the most common ones (to avoid shipping 80+ MB of spark-notebook jars)
    import notebook.front.widgets.charts._
    import notebook.front.widgets.charts.MockedCharts._
    
    // Create spark configuration holder
    val sparkConf = new SparkConf()
    
    // Set configuration
    val config = ConfigFactory.load()
    val sparkConfig = Try(config.getConfig("spark"))
      .getOrElse(com.typesafe.config.ConfigFactory.empty)
      .atPath("spark").entrySet.asScala.map(e => e.getKey -> config.getString(e.getKey))
    
    sparkConf.setAll(sparkConfig)
    
    sparkConf.setMaster(sparkConf.get("spark.master", "local[*]"))
    sparkConf.set("spark.app.name", sparkConf.get("spark.app.name", "simple-spark"))
    
    // Distribute the jars to executors, so this do not require a separate spark installation
    // This is needed only if not using spark-submit (comment otherwise)
    
    def setExecutorJars() = {
      val currentProjectJars = Array("lib/com.example.simple-spark-0.0.1-SNAPSHOT.jar", "target/scala-2.10/com.example.simple-spark_2.10-0.0.1-SNAPSHOT.jar").map{j => new java.io.File(j)}.filter(_.exists()).map(_.getAbsolutePath)
      val sparkLibDir = new java.io.File("spark-lib")
      val fromProjectJars = Array[String]().map{j => new java.io.File(sparkLibDir, j).getAbsolutePath}
      val jarsArray = (sparkConf.get("spark.jars", "").split(",").toArray ++ currentProjectJars ++ fromProjectJars).distinct.filter(!_.isEmpty)
      println("Add Jars: \n" + jarsArray.mkString("\n"))
      sparkConf.setJars(jarsArray)
    }
    
    setExecutorJars()
    
    // Create Spark Session / Spark Context
    val sparkSession = SparkSession.builder().config(sparkConf).getOrCreate
    val sparkContext = sparkSession.sparkContext
    println("SparkConf used:" + sparkContext.getConf.toDebugString)
    
    // aliases
    val sc = sparkContext
    val ss = sparkSession
    import ss.implicits._

    // main code
    // no custom variables 
    /* -- Code Cell: Some(2A2F6AA618AC48018D01E7D2F4183B76) -- */ 
    
      sparkContext.getConf.toDebugString
    /****************/
    
    
      /* -- Code Cell: Some(9088B578DE2F4BA48DF323F11895488A) -- */ 
    
      def transform(i: Int) = (i, i+1)
    /****************/
    
    
      /* -- Code Cell: Some(BF434E47187740E78B7A7A521D2D87DD) -- */ 
    
      val dataset = sparkSession.createDataset(1 to 1000).map(transform)
    /****************/
    
    
      /* -- Code Cell: Some(EE80B60DB2C645D58B30EB2B793A5BEC) -- */ 
    
      display(dataset.toDF)
    /****************/
    
    
      /* -- Code Cell: Some(7797C8DBEB7643D788F4D14F6C8E2B40) -- */ 
    
      val sum = dataset.map(_._2).reduce(_+_)
      
      println(sum)
    /****************/
    
    
      /* -- Code Cell: Some(73F60C7C9F2945E38AC0C252F2C3AC1E) -- */
    
    sparkContext.stop
  }
}
