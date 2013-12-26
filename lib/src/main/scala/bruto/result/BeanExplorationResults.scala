package bruto.result

import java.util
import scala.collection.JavaConversions._

class BeanExplorationResults(var clazz: java.lang.Class[_]) {

  var methodExplorationResults: java.util.List[MethodExplorationResults] = new util.ArrayList[MethodExplorationResults]

  def add(methodExplorationResults: MethodExplorationResults) {
    this.methodExplorationResults.add(methodExplorationResults)
  }

  def getMethodExplorationResults: java.util.List[MethodExplorationResults] = {
    methodExplorationResults
  }

  def printResults(stringBuilder: java.lang.StringBuilder) = {
    stringBuilder.append("-------------- Bean Exploration Report --------------\n")
    stringBuilder.append("Class: %s\n".format(clazz.getName))
    stringBuilder.append("Testable methods: %s\n".format(methodExplorationResults.size()))

    methodExplorationResults.foreach(methodResults => methodResults.printResults(stringBuilder))

    stringBuilder.append("-------------- Bean Exploration Report End --------------\n")
  }

}
