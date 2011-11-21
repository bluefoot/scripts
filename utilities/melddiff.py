#!/usr/bin/python
"""
Will open meld with a diff between a file and his base svn local revision.
author: Bluefoot
email: bluefoot.dev@gmail.com
date: 2011 Jul 15
license: Apache v2
"""
import os, sys

def get_svn_base(filepath):
    return os.path.join(os.path.dirname(filepath), ".svn/text-base", os.path.basename(filepath) + ".svn-base")

if __name__ == '__main__':
    if len(sys.argv) != 2:
        print "must provide only one argument"
        sys.exit(1)
    
    if sys.argv[1].startswith('/'):
        filepath = sys.argv[1]
    else:
        filepath = os.path.join(os.getcwd(), sys.argv[1])

    os.popen("meld %s %s" % (get_svn_base(filepath), filepath))

