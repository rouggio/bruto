package bruto.variability.primitives

import bruto.variability.{DiscreteSetWalker, ArgumentVariabilityWalker, ArgumentVariability}
import java.lang.reflect.Type

class StringArgumentVariability extends ArgumentVariability {

  private val variants : Array[String] = Array[String](null, "", " ", " a", " a ", "ab", " ab ")


  override def matches(aType: Type) : Boolean = {
    classOf[String] == aType
  }

  override def newWalker(aType: Type) : ArgumentVariabilityWalker = {
    new DiscreteSetWalker(variants)
  }

}
