package com.evolutionnext.cdiscala.dao

import javax.persistence.{Query, EntityManager}

/**
 * Created by Daniel Hinojosa
 * User: Daniel Hinojosa
 * Date: 4/29/11
 * Time: 3:31 PM
 * url: <a href="http://www.evolutionnext.com">http://www.evolutionnext.com</a>
 * email: <a href="mailto:dhinojosa@evolutionnext.com">dhinojosa@evolutionnext.com</a>
 * tel: 505.363.5832
 */
class TypedFinder(em: EntityManager) {
  def inferType(a: Any) = {
    a match {
      case x: Char => "Char"
      case x: Byte => "Int"
      case x: Short => "Short"
      case x: Int => "Int"
      case x: Long => "Long"
      case x: Float => "Float"
      case x: Double => "Double"
      case x: String => "String"
      case x: AnyRef => "Object"
    }
  }

  def resultList[T](fieldMap: Map[String, Any], queryString: String): Seq[T] = {
    import scala.collection.JavaConversions._
    val query = em.createQuery(queryString)
    fieldMap.foreach {case (k: String, v: Any) => query.setParameter(k, v)}
    query.getResultList.toList.asInstanceOf[Seq[T]]
  }

  def singleResult[T](fieldMap: Map[String, Any], queryString: String):T = {
    val query = em.createQuery(queryString)
    fieldMap.foreach {case (k: String, v: Any) => query.setParameter(k, v)}
    query.getSingleResult.asInstanceOf[T]
  }

  def find[E](id:Any)(implicit m:Manifest[E]):E = {
    em.find(m.erasure.asInstanceOf[Class[E]], id)
  }
}