#!/bin/sh
# AUTO-GENERATED FILE, DO NOT EDIT!
if [ -f $1.org ]; then
  sed -e 's!^C:/cygwin64/lib!/usr/lib!ig;s! C:/cygwin64/lib! /usr/lib!ig;s!^C:/cygwin64/bin!/usr/bin!ig;s! C:/cygwin64/bin! /usr/bin!ig;s!^C:/cygwin64/!/!ig;s! C:/cygwin64/! /!ig;s!^Z:!/cygdrive/z!ig;s! Z:! /cygdrive/z!ig;s!^X:!/cygdrive/x!ig;s! X:! /cygdrive/x!ig;s!^J:!/cygdrive/j!ig;s! J:! /cygdrive/j!ig;s!^C:!/cygdrive/c!ig;s! C:! /cygdrive/c!ig;' $1.org > $1 && rm -f $1.org
fi
