subtitle_crawler
================

Tools I developed for crawl through biscope.lk and fetch all the subtitles links available, download them,extract them and create the srt dir by chosing one of the srt from each archive.

1. use the HTMLUtils java class to fetch the links of the each subtitle post page
2. use the same class to get the download links to the subtitles.
3. using down_sub.sh script download the subtitles zip files, use the path to download_link_list.txt as parameter
4. use extract_sub.sh and extract_sub_rar.sh to extract and choose one srt to process. follow the dir structure given.
