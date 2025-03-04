SUMMARY = "ZAP stands for ZCL Advanced Platform. It is used to configure clusters, attributes and other entities for Matter and ZigbeePro applications."
HOMEPAGE = "https://www.silabs.com/"
DESCRIPTION = "Used to Build Matter v1.4 on Qualcomm Platform."
SECTION = "base"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"


BRANCH = "master"


# v2024.08.15 nightly
SRC_URI = "gitsm://github.com/project-chip/zap.git;branch=${BRANCH};protocol=https"
SRCREV = "33637fe41ab27b217a31ebd7d199825628c4695c"


#inherit pkgconfig

S = "${WORKDIR}/git"

DEPENDS += "nodejs-native"

do_configure() {

    cd ${S}
    npm_path=$(which npm)
    bbnote ${npm_path}

    npm config set cache ${WORKDIR}/.npm

    npm_config=$(npm config ls -l)
    bbnote "npm_config is ${npm_config}"

}


do_compile() {
    cd ${S}
    npm_path=$(which npm)
    bbnote "npm path is ${npm_path}"

    npm_root=$(npm root)
    bbnote "npm root is ${npm_root}"

    npm install
    npm run build
    #npm run tsc
    #npm ci
    mkdir -p ${bindir}/zap
    cp -r ${S}/* ${bindir}/zap/

}

do_install() {

    install -d -m 755 ${D}${bindir}
    cp -r ${bindir}/zap ${D}${bindir}

}


BBCLASSEXTEND = "native nativesdk"
