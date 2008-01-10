#!/bin/bash
#
# Script to add a gpl-header to every sourcefile
#
#

gnuheader=`<gpl-2.0-header.xml.txt`
# echo "$gnuheader"
addheader(){
	if [ -e $1 ]
	then
		filename=$1
# 		echo "$filename"
# 		echo "$gnuheader"
		echo "$gnuheader" > tmp.hdr
		cat $filename >> tmp.hdr
		mv tmp.hdr $filename
		echo "$filename"
	fi
}

# main program
# echo "$PWD"
javaFiles=$(find ../ -iname *.xmi)
# echo "$javaFiles"
for currentFile in $javaFiles
do
	addheader $currentFile
done


exit 0

