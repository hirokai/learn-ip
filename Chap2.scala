// Adapted from "Principles of Digital Image Processing" by Wilhelm Burger and Mark J. Burge
// 2.2.4 Inverting an Image

import ij.ImagePlus
import ij.plugin.Concatenator
import ij.plugin.filter.PlugInFilter
import ij.process.{ByteProcessor, ImageProcessor}

class Sec2_2_4_Inverting extends PlugInFilter {
  // this plugin accepts 8-bit grayscale images
  def setup(arg: String, im: ImagePlus): Int = PlugInFilter.DOES_8G

  def run(ip: ImageProcessor) {
    val w = ip.getWidth
    val h = ip.getHeight
    // iterate over all image coordinates
    for (u <- 0 until w; v <- 0 until h) {
      val p = ip.getPixel(u, v)
      ip.putPixel(u, v, 255 - p); // invert
    }
  }

}

class Exercise_2_3 extends PlugInFilter {
  // this plugin accepts 8-bit grayscale images
  def setup(arg: String, im: ImagePlus): Int = PlugInFilter.DOES_8G

  def run(ip: ImageProcessor)  {
    val w = ip.getWidth
    val h = ip.getHeight

    for (u <- 0 until 10; v <- 0 until h) ip.putPixel(u, v, 255)
    for (u <- (w - 10) until w; v <- 0 until h) ip.putPixel(u, v, 255)
    for (u <- 0 until w; v <- 0 until 10) ip.putPixel(u, v, 255)
    for (u <- 0 until w; v <- (h - 10) until h) ip.putPixel(u, v, 255)
  }
}
