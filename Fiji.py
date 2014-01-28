# Example of Fiji script for taking statistics of every stack in a stack file.

from ij import IJ
from ij.process import ImageStatistics as IS
import os

options = IS.MEAN | IS.MEDIAN | IS.MIN_MAX | IS.AREA

def getStatistics(imp):
  """ Return statistics for the given ImagePlus """
  global options
  ip = imp.getProcessor()
  stats = IS.getStatistics(ip, options, imp.getCalibration())
  return stats.mean, stats.median, stats.min, stats.max, stats.area


# Folder to read all images from:
filename = "image.tif"
roi1 = [56,55,13,13]
roi2 = [51,82,15,7]
roi3 = [92,58,7,13]
def getFrameStat(imp,i):
	imp.setSlice(i)
	mean, median, min, max,area = getStatistics(imp)
	return mean,median,min,max,area

# Get statistics for each image in the folder
# whose file extension is ".tif":
if filename.endswith(".tif"):
    # print "Processing", filename
    imp = IJ.openImage(filename)
    if imp is None:
      print "Could not open image from file:", filename
    else:
#      mean, median, min, max = getStatistics(imp)
      n = imp.getNFrames()
      for i in range(1,n+1):
	      imp.setRoi(*roi1)
	      mean,median,min,max,area = getFrameStat(imp,i)
	      imp.setRoi(*roi2)
	      mean2,median2,min2,max2,area2 = getFrameStat(imp,i)
	      imp.setRoi(*roi3)
	      mean3,median3,min3,max3,area3 = getFrameStat(imp,i)
#	      print n, "frames"
#	      print "Image statistics for", imp.title
#	      print "Mean:", mean
	      print mean,"\t",mean2,"\t",mean3
#	      print "Median:", median
#	      print "Stdev:", median
#	      print "Min and max:", min, "-", max
#	      print "Area:",area
else:
    print "Ignoring", filename
          
