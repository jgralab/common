#!/bin/bash

fixtext=`<fix.java.txt`
# echo "$gnuheader"
fixheader(){
	if [ -e $1 ]
	then
		filename=$1
# 		echo "$filename"
# 		echo "$gnuheader"
		rpl " * This program is free" "$fixtext" $filename
		echo "$filename"
	fi
}

# main program
# echo "$PWD"

# fixheader "test.txt"

javaFiles=$(find ../src/ -iname *.java)

for currentFile in $javaFiles
do
	fixheader $currentFile
done


exit 0
