package bruto.variability

import java.lang.reflect.Type

/**
 * describes the range of values which might be assumed by a given parameter
 */
abstract class ArgumentVariability {

  def matches(aType : Type) : Boolean

  def newWalker(aType : Type) : ArgumentVariabilityWalker

}
