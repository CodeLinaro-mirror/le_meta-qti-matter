# Copyright (c) 2022, 2025 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: BSD-3-Clause-Clear

import os
import re
import sys
import shutil
import subprocess

def platform_file_include_adjust(platform, device, filename):
    pat=re.compile("(#include.<.*/)(Linux)(/.*\.h.*>)")
    temp = open('temp', 'w')
    file = open(filename)
    for line in file.readlines():
        m = pat.match(line)
        if m:
            line = m.group(1) + platform + '/' + device + m.group(3) + '\n'
        temp.write(line)
    temp.close()
    file.close()
    subprocess.run(['mv', '-f', 'temp', filename], shell=False, check=False)

def soft_link_path_adjust(directory):
    for rootdir, subdirs, files in os.walk(directory):
        for name in subdirs + files:
            path = os.path.join(rootdir, name)
            if os.path.islink(path):
                target = os.readlink(path)
                os.unlink(path)
                os.symlink("../" + target, path)


if __name__ == '__main__':
    if len(sys.argv) != 4:
        print("wrong input parameters")
        exit(1)
    matter = sys.argv[1]
    patch = sys.argv[2]
    device = sys.argv[3]

    platform = "qcom"

    if device is None:
        device = "default"

    examples = [os.path.join(matter, "examples", "all-clusters-app"), os.path.join(matter, "examples", "platform")]
    for item in examples:
        if os.path.exists(os.path.join(item, platform, device)):
            subprocess.run(['rm', '-rf', os.path.join(item, platform, device)], shell=False, check=False)
        if os.path.exists(os.path.join(item, "linux")):
            subprocess.run(['mkdir', '-p', os.path.join(item, platform)], shell=False, check=False)
            subprocess.run(['cp', '-r', os.path.join(item, "linux"), os.path.join(item, platform, device)], shell=False, check=False)
            soft_link_path_adjust(os.path.join(item, platform, device))


    source = os.path.join(matter, "src", "platform")
    if os.path.exists(os.path.join(source, platform, device)):
        subprocess.run(['rm', '-rf', os.path.join(source, platform, device)], shell=False, check=False)
    if os.path.exists(os.path.join(source, "Linux")):
        shutil.copytree(os.path.join(source, "Linux"), os.path.join(source, platform, device), ignore=shutil.ignore_patterns("bluez"))
        soft_link_path_adjust(os.path.join(source, platform, device))

    if os.path.exists(os.path.join(source, platform, device, "fluoride")):
        subprocess.run(['rm', '-rf', os.path.join(source, platform, device, "fluoride")], shell=False, check=False)

    subprocess.run(['mkdir', '-p', os.path.join(source, platform, device, "fluoride")], shell=False, check=False)
    subprocess.run(['cp', os.path.join(source, "Linux", "bluez", "Types.h"), os.path.join(source, platform, device, "fluoride")], shell=False, check=False)

    directories = [source] + examples
    for item in directories:
        for rootdir, subdirs, files in os.walk(os.path.join(item, platform, device)):
            for file in files:
                filename = os.path.join(rootdir, file)
                platform_file_include_adjust(platform, device, filename)


    # apply patch
    os.chdir(os.path.join(matter, 'src', 'platform', platform, device))
    subprocess.run(['patch', '-p1', '-i', os.path.join(patch, 'matter-src-platform-linux.patch')], shell=False, check=False)



