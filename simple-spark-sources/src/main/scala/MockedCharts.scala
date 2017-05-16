
package notebook.front.widgets.charts

import notebook.front.widgets.charts.MockedCharts.DEFAULT_MAX_POINTS

object MockedCharts {
  val DEFAULT_MAX_POINTS = 1000

  def display[C](originalData:C, fields:Option[(String, String)]=None, maxPoints:Int=0) = {}
  def pairs[C](originalData:C, maxPoints:Int=0) = {}
  def ul(capacity:Int=10, initData:Seq[String]=Nil, prefill:Option[String]=None) = {}
  def ol(capacity:Int=10, initData:Seq[String]=Nil, prefill:Option[String]=None) = {}
  def img(tpe:String="png", width:String="", height:String="") = {}
  def text(value: String) = {}
}

case class CustomC3Chart[C](
                             originalData: C,
                             chartOptions: String = "{}",
                             sizes: (Int, Int) = (600, 400),
                             maxPoints: Int = DEFAULT_MAX_POINTS
                           )

case class ScatterChart[C](
                            originalData: C,
                            fields: Option[(String, String)] = None,
                            sizes: (Int, Int) = (600, 400),
                            maxPoints: Int = DEFAULT_MAX_POINTS,
                            groupField: Option[String] = None
                          )

case class LineChart[C](
                         originalData: C,
                         fields: Option[(String, String)] = None,
                         sizes: (Int, Int) = (600, 400),
                         maxPoints: Int = DEFAULT_MAX_POINTS,
                         groupField: Option[String] = None
                       )

case class RadarChart[C](
                          originalData: C,
                          labelField: Option[String] = None,
                          sizes: (Int, Int) = (600, 400),
                          maxPoints: Int = DEFAULT_MAX_POINTS
                        )


case class ParallelCoordChart[C](
                                  originalData: C,
                                  sizes: (Int, Int) = (600, 400),
                                  maxPoints: Int = DEFAULT_MAX_POINTS
                                )

case class TimeseriesChart[C](
                               originalData: C,
                               fields: Option[(String, String)] = None,
                               sizes: (Int, Int) = (600, 400),
                               maxPoints: Int = DEFAULT_MAX_POINTS,
                               groupField: Option[String] = None,
                               tickFormat: String = "%Y-%m-%d %H:%M:%S"
                             )

case class BarChart[C](
                        originalData: C,
                        fields: Option[(String, String)] = None,
                        sizes: (Int, Int) = (600, 400),
                        maxPoints: Int = DEFAULT_MAX_POINTS,
                        groupField: Option[String] = None
                      )


case class PieChart[C](
                        originalData: C,
                        fields: Option[(String, String)] = None,
                        sizes: (Int, Int) = (600, 400),
                        maxPoints: Int = DEFAULT_MAX_POINTS
                      )

case class DiyChart[C](
                        originalData: C,
                        js: String = "",
                        sizes: (Int, Int) = (600, 400),
                        maxPoints: Int = DEFAULT_MAX_POINTS
                      )


case class GeoPointsChart[C](
                              originalData: C,
                              sizes: (Int, Int) = (600, 400),
                              maxPoints: Int = DEFAULT_MAX_POINTS,
                              latLonFields: Option[(String, String)] = None,
                              rField: Option[String] = None,
                              colorField: Option[String] = None
                            )


case class GeoChart[C](
                        originalData: C,
                        sizes: (Int, Int) = (600, 400),
                        maxPoints: Int = DEFAULT_MAX_POINTS,
                        geometryField: Option[String] = None,
                        rField: Option[String] = None,
                        colorField: Option[String] = None,
                        fillColorField: Option[String] = None
                      )

case class GraphChart[C](
                          originalData: C,
                          sizes: (Int, Int) = (600, 400),
                          maxPoints: Int = DEFAULT_MAX_POINTS,
                          charge: Int = -30,
                          linkDistance: Int = 20,
                          linkStrength: Double = 1.0
                        )


case class PivotChart[C](
                          originalData: C,
                          sizes: (Int, Int) = (600, 400),
                          maxPoints: Int = DEFAULT_MAX_POINTS,
                          // FIXME: otherwise this would add dependency on play-json!
                          // derivedAttributes:JsObject=play.api.libs.json.Json.obj(),
                          options: Map[String, String] = Map.empty
                        )


case class CustomPlotlyChart[C](
                                 originalData: C,
                                 layout: String = "{}",
                                 dataOptions: String = "{}",
                                 dataSources: String = "{}",
                                 sizes: (Int, Int) = (600, 400),
                                 maxPoints: Int = DEFAULT_MAX_POINTS
                               )

case class TableChart[C](
                          originalData: C,
                          filterCol: Option[Seq[String]] = None,
                          sizes: (Int, Int) = (600, 400),
                          maxPoints: Int = DEFAULT_MAX_POINTS
                        )

    