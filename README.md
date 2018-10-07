[![Build Status](https://travis-ci.org/lanl-ansi/micot-general-fragility.svg?branch=master)](https://travis-ci.org/lanl-ansi/micot-general-fragility)

[![codecov](https://codecov.io/gh/lanl-ansi/micot-general-fragility/branch/master/graph/badge.svg)](https://codecov.io/gh/lanl-ansi/micot-general-fragility)

# gfm-lpnorm

This is a legacy version of the micot-general-fragility model (GFM) to support the LPNORM project and is referred to as gfm-lpnorm. The current GFM is found in [repository](https://github.com/lanl-ansi/generalized-fragility-model). The support for this version of LPNORM will mingrate to the current GFM at a future date.

# Installation

## Compiled

A compiled JAR file is found in [target/gfm-lpnorm-0.1.jar](https://github.com/lanl-ansi/micot-general-fragility/blob/master/target/gfm-lpnorm-0.1.jar)

A compiled runnable jar file is found in [target/gfm-lpnorm.jar](https://github.com/lanl-ansi/micot-general-fragility/blob/master/target/gfm-lpnorm.jar)

## Maven Installation

gfm-lpnorm is distributed as Maven project. To install gfm-lpnorm as Maven project, follow these steps

1. Download [Apache Maven](https://maven.apache.org/download.cgi) and unzip the package into a location of your choice.
2. Update your PATH variable to point at the bin directory where the "mvn" executable is located
3. Download and install [Java 1.8 JDK (or later)](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html).
4. [Update](https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/) JAVA_HOME to point at directory of your Java installation.
5. Install a git tool
6. Download the repository using the following command ```git clone https://github.com/lanl-ansi/micot-general-fragility.git```
7. Build and package the code using the command ```mvn -Dmaven.test.skip=true package``` from the top level directory of the git repository

# Usage

The runnable jar file is executed with the command

```code
java -jar gfm-lpnorm.jar
```
You call this JAR from the command line with the following flags
 
```code

required arguments:
  -wf              path to the wind field Esri Ascii input file
  -r               path to the power system input file (RDT format)
  -o               path to the damage output file (RDT format)

optional arguments:
  -po              path to the poles output file
  -reo             path to the response estimator output file
  -feo             path to the fragility exposure output file
  -num             number of scenarios to generate in the damage output file (default is 1)
```
