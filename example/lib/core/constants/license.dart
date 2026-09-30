/// Demo FP1 licenses — same keys as native DocumentReader Android / iOS Apps.
library;

import 'dart:io' show Platform;

const androidApplicationId = 'com.identixia.documentreader';
const iosBundleId = 'com.identixia.documentreader.app';

const androidLicense =
    'pyyR2AECrdslO86QjttubhRKYJPJkY2yUVyxB8Sl3FZPQWIAAACcXtjl3U11LDEkp9Euqau6IQSeG5leKWraCqutz0r/HIv3Gxc6Y6bU7ICiG67b8344qASs5PnTyPDtG9/6L5QDkychtASXMh2qTpQeOC/P6fhJpf9mCCaSTYh1NWrlgGCyCmcAMGUCMQCyTmnLzoQfqLTNXYbJRyi3AoommNS5g2BFtjQQmWnDvKIHnCQEI+nEt1T7zxDBdNMCMHUPQ8jdH9Tx29zlkve8nWPUG2/8k/4hQmpMTBsHLp0NejIUv+nwyj+L0G3Y7dFd7A==';

const iosLicense =
    'pyyR2AECGxM88KoV67kjyUExX1uq3nOlD0x6wYmAdcdxHmEAAABH0F0Fpfsrb2kutZhsGTkFIsIlA5yVxSr7oDJ8PdaqJwG8RmkUXj/Iy7rZGrmB76Rk4/wTXtU8RYM8BB7Hfth4YcoiSugRW4gnu9BUvSuXurTLj1d5vrux8px4Zywydd+KZwAwZQIwJKDo8577/v8VeG/+tdQTAMSPt4W/PEIOvFJJSXKaCOwO4wMhoxrtvVmfrlfLwjI1AjEA3rRazlaPTM4Oi21gKYpw6B0ll5MxEyrKdKO7QbUmxwL/if8DfL8ZwBwJI85ByH8X';

String demoLicense() => Platform.isIOS ? iosLicense : androidLicense;
