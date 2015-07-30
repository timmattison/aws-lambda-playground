#!/usr/bin/env python

import sys
import json
import base64

# Remove the script name
sys.argv.pop(0)

# Make sure we have a filename
if len(sys.argv) == 0:
  sys.exit("You must specify a filename")

filename = sys.argv.pop(0)

# Read the file
raw_data = open(filename, 'r').read()

# Create the output structure and put the base64 encoded data into it
output_data = {}
output_data['image'] = base64.b64encode(raw_data)

# Print the data as JSON
print json.dumps(output_data)
