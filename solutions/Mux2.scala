package solutions

import Chisel._
import Chisel.hwiotesters.SteppedHWIOTester
import scala.math._

class Mux2 extends Module {
  val io = new Bundle {
    val sel = Bits(INPUT,  1)
    val in0 = Bits(INPUT,  1)
    val in1 = Bits(INPUT,  1)
    val out = Bits(OUTPUT, 1)
  }
  io.out := (io.sel & io.in1) | (~io.sel & io.in0)
}

class Mux2Tests extends SteppedHWIOTester {
  val device_under_test = Module(new Mux2)
  val c = device_under_test

  val n = pow(2, 3).toInt
  for (s <- 0 until 2) {
    for (i0 <- 0 until 2) {
      for (i1 <- 0 until 2) {
        poke(c.io.sel, s)
        poke(c.io.in1, i1)
        poke(c.io.in0, i0)
        expect(c.io.out, (if (s == 1) i1 else i0))
        step(1)
      }
    }
  }
}
