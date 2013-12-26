package bruto.variability.primitives

import java.lang.reflect.Type
import bruto.variability.{DiscreteSetWalker, ArgumentVariabilityWalker, ArgumentVariability}

class IntegerArgumentVariability extends ArgumentVariability {

  private val variants: Array[Integer] = Array[Integer](null, 0, 1, 2, Integer.MAX_VALUE)

  override def matches(aType: Type) : Boolean = {
    classOf[Integer] == aType
  }

  override def newWalker(aType: Type) : ArgumentVariabilityWalker = {
    new DiscreteSetWalker(variants)
  }

}
