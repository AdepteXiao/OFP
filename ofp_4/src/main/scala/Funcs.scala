package ru.ad.ofp_4

sealed trait Func {
  val name: String
  override def toString: String = name
}

object Funcs {
  case object GETALLNUM extends Func {
    val name = "getAllNum"
  }

  case object GETRAW extends Func {
    val name = "getRaw"
  }

  case object ISCAPSLOCKED extends Func {
    val name = "isCapsLocked"
  }

  case object GETSTRING extends Func {
    val name = "getString"
  }

  def withName(name: String): Func = name match {
    case GETALLNUM.name => GETALLNUM
    case GETRAW.name => GETRAW
    case ISCAPSLOCKED.name => ISCAPSLOCKED
    case GETSTRING.name => GETSTRING
    case _ => throw new IllegalArgumentException(s"Unknown function name: $name")
  }
}