#!/usr/bin/python
#Filename: elasticsearch.py

import os

if len(sys.argv) <2:
	print 'Please input the correct argument!'
	sys.exit()

startCommand=sys.argv[1]

if os.system(startCommand) == 0:
   print 'Success to start ElasticSearch command!'
else:
   print 'Fail to start command!' 
	
  