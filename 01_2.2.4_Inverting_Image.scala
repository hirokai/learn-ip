/**
 * Created with IntelliJ IDEA.
 * User: hiroyuki
 * Date: 10/5/13
 * Time: 7:16 PM
 * To change this template use File | Settings | File Templates.
 */

import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.process.ImageProcessor

class My_Inverter extends PlugInFilter {
  // this plugin accepts 8-bit grayscale images
  def setup(arg: String, im: ImagePlus): Int = PlugInFilter.DOES_8G

  def run(ip: ImageProcessor) {
    val w = ip.getWidth
    val h = ip.getHeight
    // iterate over all image coordinates
    for (u <- 0 until w) {
      for (v <- 0 until h) {
        val p = ip.getPixel(u, v)
        ip.putPixel(u, v, 255 - p); // invert }
      }
    }
  }
}

// end of class My_Inverter