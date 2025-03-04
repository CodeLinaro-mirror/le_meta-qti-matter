SUMMARY = "Matter (formerly Project CHIP) creates more connections between more objects, simplifying development for manufacturers and increasing compatibility for consumers, guided by the Connectivity Standards Alliance."
HOMEPAGE = "https://buildwithmatter.com/"
DESCRIPTION = "Integrating Matter 1.4 into Qualcomm Platform SDK."
SECTION = "base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

BRANCH = "v1.4-branch"

#SRC_URI = "gitsm://github.com/project-chip/connectedhomeip.git;branch=${BRANCH};protocol=https;platforms=linux"
#SRCREV = "43aa98c2d30ee547c6b587b9de7bbb794f175ece"

SRC_URI = "git://github.com/project-chip/connectedhomeip.git;branch=${BRANCH};protocol=https;name=connectedhomeip \
           git://github.com/nestlabs/nlassert.git;branch=master;protocol=https;name=nlassert;destsuffix=git/third_party/nlassert/repo \
           git://github.com/nestlabs/nlio.git;branch=master;protocol=https;name=nlio;destsuffix=git/third_party/nlio/repo \
           git://github.com/ARMmbed/mbedtls.git;branch=mbedtls-2.28;protocol=https;name=mbedtls;destsuffix=git/third_party/mbedtls/repo \
           git://github.com/nayuki/QR-Code-generator.git;branch=master;protocol=https;name=qrcode;destsuffix=git/examples/common/QRCode/repo \
           git://github.com/openthread/openthread.git;branch=main;protocol=https;name=openthread;destsuffix=git/third_party/openthread/repo \
           git://github.com/openthread/ot-br-posix.git;branch=main;protocol=https;name=ot-br-posix;destsuffix=git/third_party/ot-br-posix/repo \
           git://github.com/openweave/cirque.git;branch=master;protocol=https;name=cirque;destsuffix=git/third_party/cirque/repo \
           git://github.com/nanopb/nanopb.git;branch=master;protocol=https;name=nanopb;destsuffix=git/third_party/nanopb/repo \
           git://github.com/open-source-parsers/jsoncpp.git;branch=master;protocol=https;name=jsoncpp;destsuffix=git/third_party/jsoncpp/repo \
           git://github.com/troglobit/editline.git;branch=master;protocol=https;name=editline;destsuffix=git/third_party/editline/repo \
           git://github.com/google/boringssl.git;branch=master;protocol=https;name=boringssl;destsuffix=git/third_party/boringssl/repo/src \
           git://github.com/warmcat/libwebsockets.git;branch=main;protocol=https;name=libwebsockets;destsuffix=git/third_party/libwebsockets/repo \
           git://github.com/ocornut/imgui.git;branch=master;protocol=https;name=imgui;destsuffix=git/third_party/imgui/repo \
           git://github.com/google/perfetto.git;branch=releases/v35.x;protocol=https;name=perfetto;destsuffix=git/third_party/perfetto/repo \
           git://github.com/lwip-tcpip/lwip.git;branch=master;protocol=https;name=lwip;destsuffix=git/third_party/lwip/repo \
           git://github.com/abseil/abseil-cpp.git;branch=master;protocol=https;name=abseil-cpp;destsuffix=git/third_party/abseil-cpp/src \
           git://github.com/google/fuzztest.git;branch=main;protocol=https;name=fuzztest;destsuffix=git/third_party/fuzztest \
           git://github.com/google/googletest.git;branch=main;protocol=https;name=googletest;destsuffix=git/third_party/googletest \
           git://github.com/google/re2.git;branch=main;protocol=https;name=re2;destsuffix=git/third_party/re2/src \
           git://github.com/google/pigweed.git;branch=main;protocol=https;name=pigweed;destsuffix=git/third_party/pigweed/repo"


SRCREV_connectedhomeip = "43aa98c2d30ee547c6b587b9de7bbb794f175ece"
SRCREV_nlassert = "c5892c5ae43830f939ed660ff8ac5f1b91d336d3"
SRCREV_nlio = "0e725502c2b17bb0a0c22ddd4bcaee9090c8fb5c"
SRCREV_mbedtls = "5bc604f33ca4e4b818ff9584aef110b14e0ba35a"
SRCREV_qrcode = "8518684c0f33d004fa93971be2c6a8eca3167d1e"
SRCREV_pigweed = "ce0e3e2d1b7eec7cdf59fbb2ceed2b1cb3edd1ec"
SRCREV_openthread = "287dbfa25130ead5010877e934effbb0a2534265"
SRCREV_ot-br-posix = "bd15f713f8ae142526b5ef3b10388acc3c610555"
SRCREV_cirque = "16e86684b05a1d19d4ad71068fe6f977957ffb6e"
SRCREV_nanopb = "671672b4d7994a9b07a307ae654885c7202ae886"
SRCREV_jsoncpp = "69098a18b9af0c47549d9a271c054d13ca92b006"
SRCREV_editline = "425584840c09f83bb8fedbf76b599d3a917621ba"
SRCREV_boringssl = "9cac8a6b38c1cbd45c77aee108411d588da006fe"
SRCREV_libwebsockets = "c57c239368deb998420e663160a1ab2ffd5d7934"
SRCREV_imgui = "3c435c029788cc26c52e835e2feb262a3057addc"
SRCREV_perfetto = "b9aca8fb0a7d4130e6ad0b33ca3d14abbc276185"
SRCREV_lwip = "84fde1ebbfe35b3125fc2d89b8a456cbacf148e9"
SRCREV_abseil-cpp = "e9ca8d1ca225ef94fd20890b5440b22f490a410a"
SRCREV_fuzztest = "b7257a64c701fc30265eb91179bf9310549bf440"
SRCREV_googletest = "6dae7eb4a5c3a169f3e298392bff4680224aa94a"
SRCREV_re2 = "85dd7ad833a73095ecf3e3baea608ba051bbe2c7"

S = "${WORKDIR}/git"

#inherit pkgconfig

DEPENDS += "zap-native gn-native avahi ninja-native dbus-glib-native pkgconfig-native libbsd glib-2.0 openssl"
RDEPENDS:${PN} += " libavahi-client"

TARGET_CC = "${CC}"
TARGET_CXX = "${CXX}"
TARGET_CC_ARCH += "${LDFLAGS}"
TARGET_AR = "${AR}"

do_configure() {
    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"

    touch ${S}/build_overrides/pigweed_environment.gni

    cd ${S}/examples/all-clusters-app/linux

    bbnote "TARGET_ARCH=${TARGET_ARCH}"
    bbnote "TARGET_CC=${TARGET_CC}"
    bbnote "TARGET_CXX=${TARGET_CXX}"
    bbnote "TARGET_CC_ARCH=${TARGET_CC_ARCH}"
    bbnote "TARGET_AR=${TARGET_AR}"

    gn gen -v out/test --script-executable="python3" --args='is_debug=true treat_warnings_as_errors=false
        target_os="linux" 
        target_cpu="arm64" 
        arm_arch="armv8-a" 
        import("//build_overrides/build.gni") 
        target_cflags=[
            "-DCHIP_DEVICE_CONFIG_WIFI_STATION_IF_NAME=\"wlan0\"", 
            "-DCHIP_DEVICE_CONFIG_LINUX_DHCPC_CMD=\"dhcpcd -i %s \"", 
            "-O3"] 
        custom_toolchain="${build_root}/toolchain/custom" 
        target_cc="${TARGET_CC}" 
        target_cxx="${TARGET_CXX}" 
        target_ar="${TARGET_AR}"'

}

do_compile() {

    export ZAP_DEVELOPMENT_PATH="${STAGING_BINDIR_NATIVE}/zap"

    cd ${S}/examples/all-clusters-app/linux/out/test
    python3 ${THISDIR}/files/rspfile.py toolchain.ninja ${S}

    cd ${S}/examples/all-clusters-app/linux
    ninja -C out/test

}

do_install() {
    install -d -m 755 ${D}${sbindir}
    install ${S}/examples/all-clusters-app/linux/out/test/chip-all-clusters-app ${D}${sbindir}
    install -d ${D}${libdir}/matter
}


FILES:${PN} += "${sbindir}/*"
FILES:${PN} += "${libdir}/*"


#BBCLASSEXTEND = "native nativesdk"
