
SAVEIFS=$IFS
IFS=$(echo -en "\n\b")
COUNTER=1

for file in zip_down/*.rar
do
	# do something on "$file"
	echo "+++++++++++ clearing dir : zip_down/extracted_temp/ +++++++++++"
	rm -rf zip_down/extracted_temp/
	mkdir -p zip_down/extracted_temp/

	echo "+++++++++++ unzipping file to : zip_down/extracted_temp/ +++++++++++"
	# echo $file
	unrar e $file zip_down/extracted_temp
	
	# change directory to extracted dir
	cd zip_down/extracted_temp

	# get the first srt file in the dir
	srt_file=$(ls | grep '.srt\|.SRT\|.ssa' | head -1)
	
	# copying the srt file to ../../srt dir
	echo "+++++++++++ copying " $srt_file "to /home/sudheera/semester 7/fyp/Java/subtitles_use/zip_down/srt/" $srt_file " +++++++++++"
	mkdir -p /home/sudheera/semester\ 7/fyp/Java/subtitles_use/zip_down/srt_rar
	cp $srt_file /home/sudheera/semester\ 7/fyp/Java/subtitles_use/zip_down/srt_rar/$srt_file


	cd /home/sudheera/semester\ 7/fyp/Java/subtitles_use
	echo "================================================================================="

	echo "$COUNTER"
	COUNTER=$[$COUNTER +1]
done
IFS=$SAVEIFS
