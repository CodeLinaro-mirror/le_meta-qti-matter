Copyright (c) 2025 Qualcomm Innovation Center, Inc. All rights reserved.
SPDX-License-Identifier: BSD-3-Clause-Clear

import sys
import os

if __name__ == "__main__":
    filename = sys.argv[1]
    outstring =''
    with open(filename) as file_object:
        for eachLine in file_object:
            if '-Wextra-semi' in eachLine:
                eachLine=eachLine.replace('-Wextra-semi', ' ')
            outstring += eachLine
    with open(filename, 'w') as outFile:
        outFile.write(outstring)
