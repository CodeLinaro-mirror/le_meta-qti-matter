SUMMARY = "Matter (formerly Project CHIP) creates more connections between more objects, simplifying development for manufacturers and increasing compatibility for consumers, guided by the Connectivity Standards Alliance."
HOMEPAGE = "https://buildwithmatter.com/"
DESCRIPTION = "Integrating Matter 1.4 into Qualcomm Platform SDK."
SECTION = "base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

BRANCH = "v1.4-branch"

SRC_URI = "git://github.com/project-chip/connectedhomeip.git;branch=${BRANCH};protocol=https"
SRCREV = "43aa98c2d30ee547c6b587b9de7bbb794f175ece"

SRC_URI += "file://0001-add-config-patch-for-kalama.patch"

S = "${WORKDIR}/git"

#inherit pkgconfig
DEPENDS += "zap-native gn-native ninja-native dbus-glib-native pkgconfig-native libbsd glib-2.0 openssl"
DEPENDS += "libchrome fluoride bt-app btobex"
RDEPENDS:${PN} += " avahi"

TARGET_CC = "${CC}"
TARGET_CXX = "${CXX}"
TARGET_CC_ARCH += "${LDFLAGS}"
TARGET_AR = "${AR}"
TARGET_MACH = "${MACHINE}"

do_configure() {

    cd ${S}/
    python3 scripts/checkout_submodules.py --shallow --platform linux

    export PKG_CONFIG_PATH="${PKG_CONFIG_PATH}"
    export PKG_CONFIG="PKG_CONFIG_SYSROOT_DIR=\"${PKG_CONFIG_SYSROOT_DIR}\" pkg-config"

    touch ${S}/build_overrides/pigweed_environment.gni

    python3 ${THISDIR}/files/generate-for-platform.py ${S} ${THISDIR}/files/ ${TARGET_MACH}
    
    cp ${THISDIR}/files/qcom_sdk.gni ${S}/build_overrides/
    cp ${THISDIR}/files/qcom_sdk.gni ${S}/examples/build_overrides/

    cd ${S}/examples/all-clusters-app/qcom/${TARGET_MACH}

    bbnote "TARGET_ARCH=${TARGET_ARCH}"
    bbnote "TARGET_CC=${TARGET_CC}"
    bbnote "TARGET_CXX=${TARGET_CXX}"
    bbnote "TARGET_CC_ARCH=${TARGET_CC_ARCH}"
    bbnote "TARGET_AR=${TARGET_AR}"
    bbnote "TARGET_MACH=${TARGET_MACH}"

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
        chip_device_platform="qcom"
        platform_device="${TARGET_MACH}"
        platform_srcroot="${WORKSPACE}"
        platform_sysroot="${PKG_CONFIG_SYSROOT_DIR}"
        target_cc="${TARGET_CC}"
        target_cxx="${TARGET_CXX}"
        target_ar="${TARGET_AR}"'

}

do_compile() {

    export ZAP_DEVELOPMENT_PATH="${STAGING_BINDIR_NATIVE}/zap"

    cd ${S}/examples/all-clusters-app/qcom/${TARGET_MACH}/out/test
    python3 ${THISDIR}/files/rspfile.py toolchain.ninja ${S}

    cd ${S}/examples/all-clusters-app/qcom/${TARGET_MACH}/
    ninja -C out/test

}

do_install() {
    install -d -m 755 ${D}${sbindir}
    install ${S}/examples/all-clusters-app/qcom/${TARGET_MACH}/out/test/chip-all-clusters-app ${D}${sbindir}
    install -d ${D}${libdir}/matter
}


FILES:${PN} += "${sbindir}/*"
FILES:${PN} += "${libdir}/*"


#BBCLASSEXTEND = "native nativesdk"
