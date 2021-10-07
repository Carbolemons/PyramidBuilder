#!/bin/bash
printf '\033[8;48;128t'
printf '\033[u'
clear
cat src/META-INF
javac -d ./bin @src/META-INF && cd bin
read -p ">> Compiled! If no errors have been presented // Press any button to continue."
java pyramidbuilder.PyramidBuilder