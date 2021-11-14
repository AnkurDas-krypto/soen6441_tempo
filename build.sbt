name := """Giterator"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"

libraryDependencies += guice

// https://mvnrepository.com/artifact/org.kohsuke/github-api
libraryDependencies += "org.kohsuke" % "github-api" % "1.122"