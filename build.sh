#!/bin/sh
set -x

echo "Building app..."
mvn clean package -DskipTests
