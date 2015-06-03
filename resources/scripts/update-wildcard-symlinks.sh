#!/bin/bash

for filename in *[0-9].json; do
    symlink=${filename//[0-9].json/x.json}
    ln -s $filename $symlink
done
