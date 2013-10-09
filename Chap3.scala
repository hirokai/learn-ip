import ij.ImagePlus
import ij.plugin.filter.PlugInFilter
import ij.process.{ByteProcessor, ImageProcessor}
import scala.util.Random

class Compute_Histogram extends PlugInFilter {
  def setup(arg: String, img: ImagePlus): Int = PlugInFilter.DOES_8G + PlugInFilter.NO_CHANGES

  def run(ip: ImageProcessor) {
    val H = new Array[Int](256)
    // histogram array int w = ip.getWidth()
    val h = ip.getHeight
    val w = ip.getWidth
    for (v <- 0 until h) {
      for (u <- 0 until w) {
        val i = ip.getPixel(u, v)
        H(i) += 1
      }
    }
    println(H.mkString(" "))
  }
}

// end of class Compute_Histogram

// Binning
class Compute_Histogram2 extends PlugInFilter {
  def setup(arg: String, img: ImagePlus): Int = PlugInFilter.DOES_16 + PlugInFilter.NO_CHANGES

  def binning(i: Int): Int = i / 256

  def run(ip: ImageProcessor) {
    val H = new Array[Int](256)
    // histogram array int w = ip.getWidth()
    val h = ip.getHeight
    val w = ip.getWidth
    for (v <- 0 until h) {
      for (u <- 0 until w) {
        val i = ip.getPixel(u, v)
        val b = binning(i)
        H(b) += 1
      }
    }
    println(H.mkString(" "))
  }
}

// end of class Compute_Histogram2

// Binning
class Compute_Cumulative_Histogram extends PlugInFilter {
  def setup(arg: String, img: ImagePlus): Int = PlugInFilter.DOES_8G + PlugInFilter.NO_CHANGES

  def run(ip: ImageProcessor) {
    val H = new Array[Int](256)
    // histogram array int w = ip.getWidth()
    val h = ip.getHeight
    val w = ip.getWidth
    for (v <- 0 until h) {
      for (u <- 0 until w) {
        val i = ip.getPixel(u, v)
        H(i) += 1
      }
    }

    val H2 = H.scanLeft(0)((i, v) => i + v).tail

    //H3 gives the same result as H2.
    val H3 = new Array[Int](256)
    H3(0) = H(0)
    for (i <- 1 until 256) {
      H3(i) = H3(i - 1) + H(i)
    }

    println(H.mkString(" "))

    println(H2.mkString(" "))
    println(H3.mkString(" "))

    assert(H2.sameElements(H3))
  }
}

// end of class Compute_Cumulative_Histogram

class Histogram_Exercise_3_2 extends PlugInFilter {
  var title: String = null

  def setup(arg: String, im: ImagePlus): Int = {
    title = im.getTitle
    PlugInFilter.DOES_8G + PlugInFilter.NO_CHANGES
  }

  def run(ip: ImageProcessor) {
    val hist: Array[Int] = ip.getHistogram
    HistogramUtil.showHist(hist,title,true)
  }

}
// end of class Histogram_Exercise_3_2

object HistogramUtil {
  def showHist(hist: Array[Int], title: String, cumulative: Boolean = false) {
    val w = 256
    val h = 100

    // create the histogram image:
    val histIp = new ByteProcessor(w, h)
    histIp.setValue(255) // white = 255
    histIp.fill() // clear this image

    histIp.setValue(0)
    histIp.setLineWidth(1)

    val hist_draw = if (cumulative) {
      hist.scanLeft(0)((i, v) => i + v).tail
    } else {
      hist
    }
    val m = hist_draw.max.toDouble
    for(i <- 0 until 256){
      val l = (hist_draw(i).toDouble/m*h).round.toInt
      histIp.drawLine(i,h-l,i,h)
    }

    // display the histogram image:
    val hTitle = "Cumulative histogram of " + title
    val histIm = new ImagePlus(hTitle, histIp)
    histIm.show()
    // histIm.updateAndDraw();
  }
}

class Histogram_Exercise_3_4 extends PlugInFilter {
  var title: String = null

  def setup(arg: String, im: ImagePlus): Int = {
    title = "Uniformly random"
    PlugInFilter.DOES_8G + PlugInFilter.NO_CHANGES
  }

  def run(ip: ImageProcessor) {
    //Create a uniformly random image.
    val w = 300
    val h = 300
    val randIp = new ByteProcessor(w,h)
    val r = new Random
    for(u <- 0 until w; v <- 0 until h) {
      randIp.putPixel(u,v,r.nextInt(256))
    }

    val randIm = new ImagePlus("Uniformly Random Image", randIp)
    randIm.show()

    val hist: Array[Int] = randIp.getHistogram
    HistogramUtil.showHist(hist,title)
  }
}
// end of class Histogram_Exercise_3_4

class Histogram_Exercise_3_5 extends PlugInFilter {
  var title: String = null

  def setup(arg: String, im: ImagePlus): Int = {
    title = "Gaussian Random"
    PlugInFilter.DOES_8G + PlugInFilter.NO_CHANGES
  }

  def run(ip: ImageProcessor) {
    //Create a uniformly random image.
    val w = 300
    val h = 300
    val randIp = new ByteProcessor(w,h)
    val r = new Random
    for(u <- 0 until w; v <- 0 until h) {
      randIp.putPixel(u,v,(r.nextGaussian()*50).round.toInt+128)
    }

    val randIm = new ImagePlus("Gaussian Random Image", randIp)
    randIm.show()

    val hist: Array[Int] = randIp.getHistogram
    HistogramUtil.showHist(hist,title)
  }
}
// end of class Histogram_Exercise_3_5